<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.invoice.InvoiceState" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:set var="state" value="${requestScope.get(Attribute.INVOICE_STATE)}"/>
<c:set var="pageUrl" value="${URL.ADMIN_INVOICES.concat('?').concat(Attribute.INVOICE_STATE).concat('=').concat(state)}"/>

<t:page pageUrl="${pageUrl}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <c:url var="unpaidInvoices" value="${URL.ADMIN_INVOICES}">
                    <c:param name="${Attribute.INVOICE_STATE}" value="${InvoiceState.UNPAID}"/>
                </c:url>
                <c:url var="paidInvoices" value="${URL.ADMIN_INVOICES}">
                    <c:param name="${Attribute.INVOICE_STATE}" value="${InvoiceState.PAID}"/>
                </c:url>
                <ul class="nav flex-column border" id="admin-orders-nav">
                    <li class="nav-item ${state eq InvoiceState.UNPAID ? 'active' : ''}">
                        <a class="nav-link" href="${unpaidInvoices}">
                            <fmt:message key="UNPAID"/>
                        </a>
                    </li>
                    <li class="nav-item ${state eq InvoiceState.PAID ? 'active' : ''}">
                        <a class="nav-link" href="${paidInvoices}">
                            <fmt:message key="PAID"/>
                        </a>
                    </li>
                </ul>
            </div>

            <c:if test="${not empty requestScope.get(Attribute.PAGED_DATA).content}">
                <div class="col-lg-8 unselectable">
                    <h4 class="text-center mb-3">
                        <fmt:message key="invoices"/>
                    </h4>
                    <table class="table border table-bordered table-text-center table-hover">
                        <thead>
                        <tr>
                            <th><fmt:message key="id"/></th>
                            <th><fmt:message key="user"/></th>
                            <th><fmt:message key="state"/></th>
                            <th><fmt:message key="date"/></th>
                            <th><fmt:message key="time"/></th>
                            <th><fmt:message key="total"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-sm">
                        <c:forEach var="invoice" items="${requestScope.get(Attribute.PAGED_DATA).content}">
                            <c:url var="orderInfo" value="${URL.ADMIN_INVOICE}">
                                <c:param name="${Attribute.INVOICE_ID}" value="${invoice.id}"/>
                            </c:url>
                            <tr data-url="${orderInfo}" class="row-link">
                                <td>${invoice.id}</td>
                                <td>${invoice.user.fullName}</td>
                                <td><fmt:message key="${invoice.state}"/></td>
                                <td>${invoice.createdOn.toLocalDate()}</td>
                                <td>${invoice.createdOn.toLocalTime()}</td>
                                <td>${invoice.amount.value}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <t:paging pagingUrl="${URL.ADMIN_INVOICES}"
                              params="${params}"
                              data="${requestScope.get(Attribute.PAGED_DATA)}"/>
                </div>
            </c:if>
        </div>
    </div>
</t:page>