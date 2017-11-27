<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.ADMIN_INVOICE.concat('?').concat(Attribute.INVOICE_ID).concat('=').concat(param.get(Attribute.INVOICE_ID))}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <c:set var="invoice" value="${requestScope.get(Attribute.INVOICE)}"/>
                <h5 class="text-center mb-3"><fmt:message key="invoice.details"/></h5>
                <table class="table border">
                    <tbody>
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <td>${invoice.id}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="user"/></th>
                        <td>${invoice.user.fullName}</td>
                    </tr>
                    <tr class="${invoice.state}">
                        <th><fmt:message key="state"/></th>
                        <td><fmt:message key="${invoice.state}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="date"/></th>
                        <td>${invoice.createdOn.toLocalDate()}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="time"/></th>
                        <td>${invoice.createdOn.toLocalTime()}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="total"/></th>
                        <td>${invoice.amount.value}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-lg-7 unselectable">
                <h4 class="text-center mb-3"><fmt:message key="invoice.content"/></h4>
                <table class="table table-text-center table-bordered table-hover">
                    <thead>
                    <tr>
                        <th><fmt:message key="order"/></th>
                        <th><fmt:message key="state"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-sm">
                    <c:forEach var="entry" items="${requestScope.get(Attribute.ITEMS_TO_ORDER)}">
                        <c:set var="order" value="${entry.key}"/>
                        <c:url var="orderUrl" value="${URL.ADMIN_ORDER}">
                            <c:param name="${Attribute.ORDER_ID}" value="${order.id}"/>
                        </c:url>
                        <tr class="row-link" data-url="${orderUrl}">
                            <td>${order.id}</td>
                            <td class="${order.state} "><fmt:message key="${order.state}"/></td>
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