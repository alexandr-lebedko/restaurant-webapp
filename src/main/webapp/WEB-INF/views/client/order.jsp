<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>



<t:page pageUrl="${URL.CLIENT_ORDER_DETAILS.concat('?id=').concat(param.id)}">
    <div class="container main-content">

        <c:if test="${orderItems ne null}">

            <div class="row border align-items-center" id="order">

                <div class="col-lg-4" id="order-details">
                    <c:set var="orderId" value="${orderItems.get(0).order.id}"/>
                    <c:set var="invoiceId" value="${orderItems.get(0).order.invoice.id}"/>
                    <c:set var="creation" value="${orderItems.get(0).order.createdOn.toLocalDate()}"/>
                    <c:set var="invoiceGet" value="${URL.CLIENT_INVOICE_DETAILS}"/>

                    <h5 class="ml-2 mb-3"><fmt:message key="page.order.details"/></h5>

                    <table class="table table-lg border text-center">
                        <tbody>
                        <tr>
                            <th>
                                <fmt:message key="page.order.id"/>
                            </th>
                            <td>${orderId}</td>
                        </tr>
                        <tr class="invoice-id" onclick="get('${invoiceGet}',{'id': ${order.invoice.id}})">
                            <th><fmt:message key="page.invoice.id"/></th>
                            <td>${invoiceId}</td>
                        </tr>
                        <tr>
                            <th><fmt:message key="page.order.created"/></th>
                            <td>${creation}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="col-lg-8 text-center" id="order-content">
                    <h4 class="mb-3 mt-3"><fmt:message key="page.order.content"/></h4>

                    <table class="table table-bordered order-content">
                        <thead class="bg-light">
                        <tr>
                            <th scope="col"><fmt:message key="page.items.item"/></th>
                            <th scope="col"><fmt:message key="page.items.title"/></th>
                            <th scope="col"><fmt:message key="page.items.table.price"/></th>
                            <th scope="col"><fmt:message key="page.items.quantity"/></th>
                            <th scope="col">Total</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:set var="overallAmount" value="0"/>
                        <c:forEach var="orderItem" items="${orderItems}">
                            <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(orderItem.item.pictureId)}"/>
                            <c:set var="title" value="${orderItem.item.title.value.get(lang)}"/>
                            <c:set var="price" value="${orderItem.item.price.value}"/>
                            <c:set var="amount" value="${orderItem.quantity}"/>
                            <c:set var="total" value="${amount*price}"/>
                            <c:set var="overallAmount" value="${total + overallAmount}"/>
                            <tr>
                                <td><img src="${imageUrl}"/></td>
                                <td>${title}</td>
                                <td>${price}</td>
                                <td>${amount}</td>
                                <td>${total}</td>
                            </tr>
                        </c:forEach>
                        <tr class="">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <th class="pt-3 pb-3 bg-light">${overallAmount}</th>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>

        </c:if>

        <c:if test="${orders ne null}">

            <div class="row justify-content-center border" id="client-orders">

                <div class="col-lg-11 text-center">

                    <h4 class="mb-3 mt-3"><fmt:message key="page.order.orders"/> </h4>

                    <table class="table border table-lg">

                        <thead class="bg-light">

                        <tr>
                            <th scope="col"><fmt:message key="page.order.id"/></th>
                            <th scope="col"><fmt:message key="page.invoice.id"/></th>
                            <th scope="col"><fmt:message key="page.order.state"/></th>
                            <th scope="col"><fmt:message key="page.order.created"/></th>
                        </tr>

                        </thead>

                        <tbody>

                        <c:forEach var="order" items="${orders}">
                            <c:url var="orderGet" value="${URL.CLIENT_ORDER_DETAILS}"/>
                            <c:url var="invoiceGet" value="${URL.CLIENT_INVOICE_DETAILS}"/>

                            <tr>
                                <td class="order-id" onclick="get('${orderGet}',{'id': ${order.id}})">
                                        ${order.id}
                                </td>
                                <td class="invoice-id" onclick="get('${invoiceGet}',{'id': ${order.invoice.id}})">
                                        ${order.invoice.id}
                                </td>
                                <c:choose>
                                    <c:when test="${order.state == 'NEW'}">
                                        <td>
                                            NEW
                                            <i class="fa fa-spinner  float-right mr-3" aria-hidden="true"></i>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            PROCESSED
                                            <i class="fa fa-check-square-o  float-right mr-3" aria-hidden="true"></i>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${order.createdOn.toLocalDate()}</td>
                            </tr>
                        </c:forEach>

                        </tbody>

                    </table>

                </div>

            </div>

        </c:if>
    </div>


</t:page>