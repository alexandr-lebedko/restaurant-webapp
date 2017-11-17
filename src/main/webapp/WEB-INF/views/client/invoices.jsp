<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_INVOICES}">
    <div class="container main-content">
        <div class="row justify-content-end">
            <div class="col-lg-8">

                <h4 class="text-center mb-3"><fmt:message key="invoices"/></h4>

                <table class="table table-bordered table-text-center">

                    <thead class="bg-light">
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <th><fmt:message key="state"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><fmt:message key="total"/></th>
                    </tr>
                    </thead>

                    <tbody class="table-sm">
                    <c:forEach var="invoice" items="${requestScope.get(Attribute.INVOICES)}">

                        <c:url var="invoiceUrl" value="${URL.CLIENT_INVOICE}">
                            <c:param name="${Attribute.INVOICE_ID}" value="${invoice.id}"/>
                        </c:url>

                        <tr>
                            <td>
                                <a class="badge badge-info" href="${invoiceUrl}">${invoice.id}</a>
                            </td>
                            <td class="${invoice.state}"><fmt:message key="${invoice.state}"/></td>
                            <td>${invoice.createdOn.toLocalDate()}</td>
                            <td>${invoice.createdOn.toLocalTime()}</td>
                            <td>${invoice.amount.value}</td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</t:page>