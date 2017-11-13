<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.entity.order.State" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<style>

    input {
        border: none;
        margin: 0 auto !important;
        border-radius: 10%;
        text-align: center !important;
        /*padding-left: 1rem;*/
    }

</style>


<t:page pageUrl="${URL.ADMIN_ORDER_DETAILS.concat('?').concat(Attribute.ORDER_ID).concat('=').concat(param.get(Attribute.ORDER_ID))}">
    <div class="container main-content">
        <div class="row justify-content-center">
            <c:choose>
                <c:when test="${requestScope.get(Attribute.ORDER_DETAILS) ne null}">
                    <c:set var="order" value="${requestScope.get(Attribute.ORDER_DETAILS).left}"/>
                    <c:set var="orderContent" value="${requestScope.get(Attribute.ORDER_DETAILS).right}"/>


                    <div class="col-lg-3 text-center">

                        <h5 class="mb-3">Order details</h5>
                        <table class="table border">
                            <tbody>
                            <tr>
                                <th>Order</th>
                                <td>${order.id}</td>
                            </tr>
                            <tr>
                                <th>Invoice</th>
                                <td>${order.invoice.id}</td>
                            </tr>
                            <c:choose>
                                <c:when test="${order.state eq State.PROCESSED}">
                                    <tr class="bg-success">
                                        <th>State</th>
                                        <td>${order.state}</td>
                                    </tr>
                                </c:when>
                                <c:when test="${order.state eq State.REJECTED}">
                                    <tr class="bg-danger">
                                        <th>State</th>
                                        <td>${order.state}</td>
                                    </tr>
                                </c:when>

                                <c:when test="${order.state eq State.MODIFIED}">
                                    <tr class="bg-warning">
                                        <th>State</th>
                                        <td>${order.state}</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <th>State</th>
                                        <td>${order.state}</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                            <tr>
                                <th>Date</th>
                                <td>${order.createdOn.toLocalDate()}</td>
                            </tr>
                            <tr>
                                <th>Time</th>
                                <td>${order.createdOn.toLocalTime()}</td>
                            </tr>
                            </tbody>
                        </table>

                        <c:if test="${order.state eq State.NEW}">
                            <c:url var="processOrderUrl" value="${URL.ADMIN_PROCESS_ORDER}"/>
                            <c:url var="rejectOrderUrl" value="${URL.ADMIN_REJECT_ORDER}"/>

                            <form method="post" action="${processOrderUrl}">
                                <input type="hidden" name="${Attribute.ORDER_ID}" value="${order.id}"/>
                                <button class="btn btn-success btn-block rounded-0">Process</button>
                            </form>

                            <form method="post" action="${rejectOrderUrl}">
                                <input type="hidden" name="${Attribute.ORDER_ID}" value="${order.id}"/>
                                <button class="btn  float-right btn-block rounded-0 btn-danger">Reject</button>
                            </form>

                        </c:if>
                    </div>


                    <div class="col-lg-8 text-center">

                        <h4 class="mb-3">Order Content</h4>

                        <c:url var="modifyOrderUrl" value="${URL.ADMIN_MODIFY_ORDER}"/>

                        <form method="post" action="${modifyOrderUrl}" id="order-content-form">

                            <input type="hidden" name="${Attribute.ORDER_ID}" value="${order.id}"/>

                            <table style="user-select: none" class="table border centered-table">

                                <thead>

                                <tr>
                                    <th>ID</th>
                                    <th>Item</th>
                                    <th>Title</th>
                                    <th>Quantity</th>
                                    <th>Delete</th>
                                </tr>

                                </thead>

                                <tbody>
                                <c:forEach var="orderItem" items="${orderContent}" varStatus="status">
                                    <c:set var="rowId" value="${'row-id-'.concat(status.index)}"/>
                                    <c:set var="orderItemId" value="${orderItem.id}"/>
                                    <c:set var="itemTitle" value="${orderItem.item.title.value.get(lang)}"/>
                                    <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(orderItem.item.pictureId)}"/>
                                    <c:set var="itemId" value="${orderItem.item.id}"/>
                                    <c:set var="quantity" value="${orderItem.quantity}"/>

                                    <tr id="${rowId}">

                                        <td>
                                            <input readonly name="${Attribute.ORDER_ITEM_ID}" size="3"
                                                   value="${orderItemId}">
                                        </td>

                                        <td>
                                            <img src="${imageUrl}">
                                        </td>

                                        <td>
                                            <input type="hidden" name="${Attribute.ITEM_ID}"
                                                   value="${itemId}"/>${itemTitle}
                                        </td>

                                        <c:choose>

                                            <c:when test="${order.state eq State.NEW}">
                                                <td>
                                                    <input class="border pl-3" name="${Attribute.ORDER_ITEM_QUANTITY}"
                                                           type="number" max="${quantity}" min="1" value="${quantity}">
                                                </td>
                                                <td>
                                                    <i onclick="deleteOrderItemRow('${rowId}')"
                                                       class="fa fa-trash p-1"></i>
                                                </td>
                                            </c:when>

                                            <c:otherwise>
                                                <td>
                                                    <input readonly class="border pl-3" type="number" max="${quantity}"
                                                           min="1" name="${Attribute.ORDER_ITEM_QUANTITY}"
                                                           value="${quantity}">
                                                </td>

                                                <td>
                                                    <i class="fa fa-trash p-1"></i>
                                                </td>
                                            </c:otherwise>

                                        </c:choose>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>

                            <c:if test="${order.state eq State.NEW}">
                                <button disabled
                                        class="btn btn-lg float-right p-2 pl-5 pr-5 mr-3 rounded-0 btn-warning">
                                    Modify
                                </button>
                            </c:if>

                        </form>
                    </div>
                </c:when>
            </c:choose>


        </div>


        <script>
            $('#order-content-form')
                .each(function () {
                    $(this).data('initial-form-data', $(this).serialize())
                })
                .on('change input', function () {
                    $(this)
                        .find('input:submit, button:submit')
                        .prop('disabled', $(this).serialize() == $(this).data('initial-form-data'));
                })
                .find('input:submit, button:submit')
                .prop('disabled', true);

            function deleteOrderItemRow(rowId) {
                const selector = "#".concat(rowId);

                if ($(selector).siblings().length > 0) {

                    $(selector).remove();

                    $('#order-content-form')
                        .find('button:submit')
                        .prop('disabled', false);
                }
            }

        </script>


    </div>

    </div>
</t:page>