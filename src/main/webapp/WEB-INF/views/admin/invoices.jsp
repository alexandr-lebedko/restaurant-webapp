<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.invoice.State" %>

<c:set var="invoiceState" value="${requestScope.get(Attribute.INVOICE_STATE)}"/>
<c:choose>
    <c:when test="${invoiceState eq State.ACTIVE}">
        <c:set var="pageUrl" value="${URL.ADMIN_ACTIVE_INVOICES}"/>
    </c:when>
    <c:when test="${invoiceState eq State.CLOSED}">
        <c:set var="pageUrl" value="${URL.ADMIN_CLOSED_INVOICES}"/>
    </c:when>
    <c:when test="${invoiceState eq State.UNPAID}">
        <c:set var="pageUrl" value="${URL.ADMIN_UNPAID_INVOICES}"/>
    </c:when>
    <c:when test="${invoiceState eq State.PAID}">
        <c:set var="pageUrl" value="${URL.ADMIN_PAID_INVOICES}"/>
    </c:when>
</c:choose>

<t:page pageUrl="${pageUrl}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <c:url var="activeInvoices" value="${URL.ADMIN_ACTIVE_INVOICES}"/>
                <c:url var="closedInvoices" value="${URL.ADMIN_CLOSED_INVOICES}"/>
                <c:url var="unpaidInvoices" value="${URL.ADMIN_UNPAID_INVOICES}"/>
                <c:url var="paidInvoices" value="${URL.ADMIN_PAID_INVOICES}"/>

                <ul class="nav flex-column border" id="admin-orders-nav">
                    <li class="nav-item ${invoiceState eq State.CLOSED? 'active' : ''}">
                        <a class="nav-link" href="${closedInvoices}">Closed</a>
                    </li>
                    <li class="nav-item ${invoiceState eq State.ACTIVE ? 'active' : ''}">
                        <a class="nav-link" href="${activeInvoices}">Active</a>
                    </li>
                    <li class="nav-item ${invoiceState eq State.UNPAID ? 'active' : ''}">
                        <a class="nav-link" href="${unpaidInvoices}">Unpaid</a>
                    </li>
                    <li class="nav-item ${invoiceState eq State.PAID ? 'active' : ''}">
                        <a class="nav-link" href="${paidInvoices}">Paid</a>
                    </li>
                </ul>
            </div>

            <c:if test="${!empty requestScope.get(Attribute.INVOICES)}">


                <div class="col-lg-8">
                    <table class="table table-sm border table-text-center">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>User</th>
                            <th>State</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Amount</th>
                            <th><i class="fa fa-check"></i></th>
                            <th><i class="fa fa-info"></i></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="invoice" items="${requestScope.get(Attribute.INVOICES)}">

                            <c:url var="details" value="${URL.ADMIN_INVOICE}">
                                <c:param name="${Attribute.INVOICE_ID}" value="${invoice.id}"/>
                            </c:url>

                            <tr>
                                <td>${invoice.id}</td>
                                <td>${invoice.user.fullName}</td>
                                <td>${invoice.state}</td>
                                <td>${invoice.createdOn.toLocalDate()}</td>
                                <td>${invoice.createdOn.toLocalTime()}</td>
                                <td>${invoice.amount.value}</td>
                                <td><span class="badge badge-secondary span-btn">Process</span></td>
                                <td><a class="badge badge-info" href="${details}">Details</a></td>
                            </tr>
                        </c:forEach>


                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</t:page>