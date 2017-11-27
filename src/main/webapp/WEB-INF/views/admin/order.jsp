<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.entity.order.OrderState" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>


<t:page pageUrl="${URL.ADMIN_ORDER.concat('?').concat(Attribute.ORDER_ID).concat('=').concat(param.get(Attribute.ORDER_ID))}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <c:set var="order" value="${requestScope.get(Attribute.ORDER)}"/>
            <div class="col-lg-3 text-center">
                <h5 class="mb-3">
                    <fmt:message key="order.details"/>
                </h5>
                <table class="table border">
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
                <c:url var="processOrderUrl" value="${URL.ADMIN_PROCESS_ORDER}"/>
                <form method="post" action="${processOrderUrl}">
                    <input type="hidden" name="${Attribute.ORDER_ID}" value="${order.id}"/>
                    <button ${order.state ne OrderState.NEW? "disabled":""}
                            class="btn btn-success btn-block rounded-0 mb-2" id="submit-order"><fmt:message
                            key="submit"/></button>
                </form>
                <c:url var="rejectOrderUrl" value="${URL.ADMIN_REJECT_ORDER}"/>
                <form method="post" action="${rejectOrderUrl}">
                    <input type="hidden" name="${Attribute.ORDER_ID}" value="${order.id}"/>
                    <button ${order.state ne OrderState.NEW? "disabled":""}
                            class="btn float-right btn-block rounded-0 btn-danger">
                        <fmt:message key="reject"/>
                    </button>
                </form>
            </div>

            <div class="col-lg-7 text-center unselectable">

                <h4 class="mb-3"><fmt:message key="order.content"/></h4>
                <c:url var="modifyOrder" value="${URL.ADMIN_MODIFY_ORDER}"/>
                <form method="post" action="${modifyOrder}" id="order-content-form">
                    <input type="hidden" name="${Attribute.ORDER_ID}" value="${order.id}"/>
                    <table class="table border centered-table">
                        <thead>
                        <tr>
                            <th><fmt:message key="id"/></th>
                            <th><fmt:message key="item"/></th>
                            <th><fmt:message key="title"/></th>
                            <th><fmt:message key="quantity"/></th>
                            <th><fmt:message key="delete"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="orderItem" items="${requestScope.get(Attribute.ORDER_ITEMS)}">
                            <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(orderItem.item.imageId)}"/>
                            <tr>
                                <td><input readonly name="${Attribute.ORDER_ITEM_ID}" size="3" value="${orderItem.id}"></td>
                                <td><img src="${imageUrl}"></td>
                                <td>${orderItem.item.title.value.get(lang)}
                                    <input type="hidden" name="${Attribute.ITEM_ID}" value="${orderItem.item.id}"/>
                                </td>
                                <td><input class="border pl-3 ${order.state}"
                                    ${order.state ne OrderState.NEW? "readonly":""}
                                           name="${Attribute.ORDER_ITEM_QUANTITY}" type="number"
                                           max="${orderItem.quantity}" min="1" value="${orderItem.quantity}">
                                </td>
                                <td><i class="fa fa-trash p-1 delete-row-button ${order.state}"></i></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <button ${order.state ne OrderState.NEW? "disabled":""}
                            class="modify-button btn float-right p-2 pl-5 pr-5 mr-3 rounded-0 btn-warning">
                        <fmt:message key="modify"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</t:page>