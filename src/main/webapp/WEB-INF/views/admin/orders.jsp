<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.order.OrderState" %>

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
                        <a class="nav-link" href="${newOrders}">New</a>
                    </li>
                    <li class="nav-item ${orderState eq OrderState.PROCESSED ? 'active' : ''}">
                        <a class="nav-link" href="${processedOrders}">Processed</a>
                    </li>
                    <li class="nav-item ${orderState eq OrderState.MODIFIED ? 'active' : ''}">
                        <a class="nav-link" href="${modifiedOrders}">Modified</a>
                    </li>
                    <li class="nav-item ${orderState eq OrderState.REJECTED ? 'active' : ''}">
                        <a class="nav-link" href="${rejectedOrders}">Rejected</a>
                    </li>
                </ul>
            </div>

            <div class="col-lg-8">
                <table class="table table-sm border" id="admin-orders">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>State</th>
                        <th>Invoice</th>
                        <th>Date</th>
                        <th>Time</th>
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
                            <td>${order.state}</td>
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
                                            Process
                                        </span>
                                    </td>
                                    <td>
                                        <span class="badge badge-danger span-btn" id="reject-button"
                                              onclick="post('${reject}',{'${Attribute.ORDER_ID}':'${order.id}'})">
                                            Reject
                                        </span>
                                    </td>
                                </c:when>

                                <c:otherwise>
                                    <td><span class="badge badge-light span-btn">Process</span></td>
                                    <td><span class="badge badge-light span-btn" >Reject</span></td>
                                </c:otherwise>

                            </c:choose>

                            <td>
                                <a class="badge badge-info" href="${details}">Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</t:page>
