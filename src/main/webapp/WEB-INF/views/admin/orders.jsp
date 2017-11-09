<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<t:page pageUrl="${URL.ADMIN_NEW_ORDERS}">


    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <ul class="nav flex-column border" id="admin-orders-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">New</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Processed</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="#">Rejected</a>
                    </li>

                </ul>
            </div>

            <div class="col-lg-9">
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
                    <c:forEach var="order" items="${requestScope.get(Attribute.ADMIN_NEW_ORDERS)}">

                        <c:url var="orderDetails" value="${URL.ADMIN_ORDER_DETAILS}">
                            <c:param name="${Attribute.ORDER_ID}" value="${order.id}"/>
                        </c:url>

                        <tr>
                            <td>${order.id}</td>
                            <td>${order.state}</td>
                            <td>${order.invoice.id}</td>
                            <td>${order.createdOn.toLocalDate()}</td>
                            <td>${order.createdOn.toLocalTime()}</td>
                            <td>Process</td>
                            <td>Reject</td>
                            <td><a href="${orderDetails}">Details</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>


        </div>

    </div>

</t:page>