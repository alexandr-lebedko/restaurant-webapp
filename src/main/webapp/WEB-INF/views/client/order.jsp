<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.order.OrderState" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>


<t:page>
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <h5 class="mb-3 mt-3 text-center"><fmt:message key="order.details"/></h5>
                <table class="table table-lg border">
                    <tbody>
                    <tr>
                        <th><fmt:message key="order"/></th>
                        <td>${order.id}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="invoice"/></th>
                        <td>${order.invoice.id}</td>
                    </tr>
                    <tr class="${order.state}">
                        <th><fmt:message key="state"/></th>
                        <td><fmt:message key="${order.state}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="date"/></th>
                        <td>${order.createdOn.toLocalDate()}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="time"/></th>
                        <td>${order.createdOn.toLocalTime()}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-lg-8 unselectable">
                <h4 class="mb-3 text-center"><fmt:message key="order.content"/></h4>
                <table class="table table-bordered table-text-center">
                    <thead class="bg-light">
                    <tr>
                        <th scope="col"><fmt:message key="item"/></th>
                        <th scope="col"><fmt:message key="title"/></th>
                        <th scope="col"><fmt:message key="price"/></th>
                        <th scope="col"><fmt:message key="quantity"/></th>
                        <th scope="col"><fmt:message key="total"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-sm">
                    <c:set var="total" value="0"/>
                    <c:forEach var="orderItem" items="${orderItems}">
                        <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(orderItem.item.imageId)}"/>
                        <c:set var="total" value="${total+orderItem.price.value}"/>
                        <tr>
                            <td><img src="${imageUrl}"/></td>
                            <td>${orderItem.item.title.value.get(lang)}</td>
                            <td>${orderItem.item.price.value}</td>
                            <td>${orderItem.quantity}</td>
                            <td>${orderItem.price.value}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <th class="pt-3 pb-3 bg-light">${total}</th>
                    </tr>
                    </tbody>
                </table>
                <div class="row justify-content-end">
                    <c:url var="submitOrder" value="${URL.CLIENT_SUBMIT_MODIFIED_ORDER}"/>
                    <form method="post" action="${submitOrder}">
                        <input name="${Attribute.ORDER_ID}" value="${order.id}" type="hidden">
                        <button ${order.state eq OrderState.MODIFIED? "":"disabled"}
                                class="btn btn-success btn-lg rounded-0 mr-3">
                            <fmt:message key="submit"/>
                        </button>
                    </form>

                    <c:url var="modifyOrder" value="${URL.CLIENT_MODIFY_ORDER}"/>
                    <form method="post" action="${modifyOrder}">
                        <input name="${Attribute.ORDER_ID}" value="${order.id}" type="hidden">
                        <button ${(order.state eq OrderState.NEW || order.state eq OrderState.MODIFIED)?"":"disabled"}
                                class="btn btn-warning btn-lg mr-3 rounded-0">
                            <fmt:message key="modify"/>
                        </button>
                    </form>

                    <c:url var="rejectOrder" value="${URL.CLIENT_REJECT_ORDER}"/>
                    <form method="post" action="${rejectOrder}">
                        <input name="${Attribute.ORDER_ID}" value="${order.id}" type="hidden">
                        <button ${(order.state eq OrderState.NEW || order.state eq OrderState.MODIFIED)?"":"disabled"}
                                class="btn btn-danger btn-lg mr-3 rounded-0">
                            <fmt:message key="reject"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</t:page>