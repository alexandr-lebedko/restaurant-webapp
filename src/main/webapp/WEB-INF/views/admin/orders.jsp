<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.order.OrderState" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page>
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <c:url var="newOrders" value="${URL.ADMIN_ORDERS}">
                    <c:param name="${Attribute.ORDER_STATE}" value="${OrderState.NEW}"/>
                </c:url>
                <c:url var="processedOrders" value="${URL.ADMIN_ORDERS}">
                    <c:param name="${Attribute.ORDER_STATE}" value="${OrderState.PROCESSED}"/>
                </c:url>
                <c:url var="rejectedOrders" value="${URL.ADMIN_ORDERS}">
                    <c:param name="${Attribute.ORDER_STATE}" value="${OrderState.REJECTED}"/>
                </c:url>
                <c:url var="modifiedOrders" value="${URL.ADMIN_ORDERS}">
                    <c:param name="${Attribute.ORDER_STATE}" value="${OrderState.MODIFIED}"/>
                </c:url>
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
                <c:if test="${!empty requestScope.get(Attribute.PAGED_DATA).content}">
                    <h4 class="text-center mb-3"><fmt:message key="orders"/></h4>
                    <table class="table table-hover table-bordered" id="admin-orders">
                        <thead>
                        <tr>
                            <th><fmt:message key="order"/></th>
                            <th><fmt:message key="state"/></th>
                            <th><fmt:message key="invoice"/></th>
                            <th><fmt:message key="date"/></th>
                            <th><fmt:message key="time"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-sm">
                        <c:forEach var="order" items="${requestScope.get(Attribute.PAGED_DATA).content}">
                            <c:url var="orderInfo" value="${URL.ADMIN_ORDER}">
                                <c:param name="${Attribute.ORDER_ID}" value="${order.id}"/>
                            </c:url>
                            <c:url var="invoiceInfo" value="${URL.ADMIN_INVOICE}">
                                <c:param name="${Attribute.INVOICE_ID}" value="${order.invoice.id}"/>
                            </c:url>
                            <tr>
                                <td><a href="${orderInfo}">${order.id}</a></td>
                                <td class="${order.state}"><fmt:message key="${order.state}"/></td>
                                <td><a href="${invoiceInfo}">${order.invoice.id}</a></td>
                                <td>${order.createdOn.toLocalDate()}</td>
                                <td>${order.createdOn.toLocalTime()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <t:paging pagingUrl="${URL.ADMIN_ORDERS}"
                              params="${param}"
                              data="${requestScope.get(Attribute.PAGED_DATA)}"/>
                </c:if>
            </div>
        </div>
    </div>
</t:page>
