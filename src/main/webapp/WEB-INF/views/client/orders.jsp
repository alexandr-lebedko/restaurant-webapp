<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_ORDERS}">
    <div class="container main-content">
        <div class="row justify-content-end">

            <div class="col-lg-8 unselectable">

                <h4 class="text-center mb-3 "><fmt:message key="orders"/></h4>
                <table class="table border table-bordered  table-hover table-text-center">
                    <thead class="bg-light">
                    <tr>
                        <th><fmt:message key="order"/></th>
                        <th><fmt:message key="invoice"/></th>
                        <th><fmt:message key="state"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-sm">
                    <c:forEach var="order" items="${requestScope.get(Attribute.ORDERS)}">
                        <c:url var="orderUrl" value="${URL.CLIENT_ORDER}">
                            <c:param name="${Attribute.ORDER_ID}" value="${order.id}"/>
                        </c:url>
                        <c:url var="invoiceUrl" value="${URL.CLIENT_INVOICE}">
                            <c:param name="${Attribute.INVOICE_ID}" value="${order.invoice.id}"/>
                        </c:url>
                        <tr>
                            <td><a class="badge badge-info" href="${orderUrl}">${order.id}</a></td>
                            <td><a class="badge badge-info" href="${invoiceUrl}">${order.invoice.id}</a></td>
                            <td class="${order.state}"><fmt:message key="${order.state}"/></td>
                            <td>${order.createdOn.toLocalDate()}</td>
                            <td>${order.createdOn.toLocalTime()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</t:page>