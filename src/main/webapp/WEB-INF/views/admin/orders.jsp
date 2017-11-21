<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.order.OrderState" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:set var="orderState" value="${requestScope.get(Attribute.ORDER_STATE)}"/>
<c:choose>
    <c:when test="${orderState eq OrderState.MODIFIED}">
        <c:set var="pageUrl" value="${URL.ADMIN_MODIFIED_ORDERS}"/>
    </c:when>
    <c:when test="${orderState eq OrderState.PROCESSED}">
        <c:set var="pageUrl" value="${URL.ADMIN_PROCESSED_ORDERS}"/>
    </c:when>
    <c:when test="${orderState eq OrderState.REJECTED}">
        <c:set var="pageUrl" value="${URL.ADMIN_PROCESSED_ORDERS}"/>
    </c:when>
    <c:when test="${orderState eq OrderState.NEW}">
        <c:set var="pageUrl" value="${URL.ADMIN_NEW_ORDERS}"/>
    </c:when>
</c:choose>

<t:page pageUrl="${pageUrl}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <c:url var="newOrders" value="${URL.ADMIN_NEW_ORDERS}"/>
                <c:url var="processedOrders" value="${URL.ADMIN_PROCESSED_ORDERS}"/>
                <c:url var="rejectedOrders" value="${URL.ADMIN_REJECTED_ORDERS}"/>
                <c:url var="modifiedOrders" value="${URL.ADMIN_MODIFIED_ORDERS}"/>


                <ul class="nav flex-column border" id="admin-orders-nav">
                    <li class="nav-item ${orderState eq OrderState.NEW ? 'active' : ''}">
                        <a class="nav-link" href="${newOrders}">
                            <fmt:message key="new"/>
                        </a>
                    </li>
                    <li class="nav-item ${orderState eq OrderState.PROCESSED ? 'active' : ''}">
                        <a class="nav-link" href="${processedOrders}">
                        <fmt:message key="processed"/>
                        </a>
                    </li>
                    <li class="nav-item ${orderState eq OrderState.MODIFIED ? 'active' : ''}">
                        <a class="nav-link" href="${modifiedOrders}">
                            <fmt:message key="modified"/>
                        </a>
                    </li>
                    <li class="nav-item ${orderState eq OrderState.REJECTED ? 'active' : ''}">
                        <a class="nav-link" href="${rejectedOrders}">
                            <fmt:message key="rejected"/>
                        </a>
                    </li>
                </ul>
            </div>


            <div class="col-lg-8">
                <c:if test="${!empty orders}">
                <table class="table table-sm border" id="admin-orders">
                    <thead>
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <th><fmt:message key="state"/></th>
                        <th><fmt:message key="invoice"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><i class="fa fa-check"></i></th>
                        <th><i class="fa fa-ban"></i></th>
                        <th><i class="fa fa-info"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <c:url var="details" value="${URL.ADMIN_ORDER_DETAILS}">
                            <c:param name="${Attribute.ORDER_ID}" value="${order.id}"/>
                        </c:url>
                        <tr>
                            <td>${order.id}</td>
                            <td class="${order.state}"><fmt:message key="${order.state}"/></td>
                            <td>${order.invoice.id}</td>
                            <td>${order.createdOn.toLocalDate()}</td>
                            <td>${order.createdOn.toLocalTime()}</td>

                            <c:choose>
                                <c:when test="${orderState eq OrderState.NEW}">
                                    <c:url var="process" value="${URL.ADMIN_PROCESS_ORDER}"/>
                                    <c:url var="reject" value="${URL.ADMIN_REJECT_ORDER}"/>
                                    <td>
                                        <span class="badge badge-success span-btn" id="process-button"
                                              onclick="post('${process}',{'${Attribute.ORDER_ID}':'${order.id}'})">
                                            <fmt:message key="process"/>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge badge-danger span-btn" id="reject-button"
                                              onclick="post('${reject}',{'${Attribute.ORDER_ID}':'${order.id}'})">
                                            <fmt:message key="reject"/>
                                        </span>
                                    </td>
                                </c:when>

                                <c:otherwise>
                                    <td><span class="badge badge-light span-btn">Process</span></td>
                                    <td><span class="badge badge-light span-btn">Reject</span></td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <a class="badge badge-info" href="${details}">Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    </c:if>
                </table>
            </div>
        </div>
    </div>
</t:page>
