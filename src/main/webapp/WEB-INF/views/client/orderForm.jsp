<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_ORDER_FORM}">

    <div class="container main-content">

        <div class="row justify-content-end">

            <c:choose>
                <c:when test="${orderContent ne null}">
                    <div class="col-lg-8 unselectable">

                        <c:if test="${errors ne null}">
                            <div class="alert alert-warning text-center">
                                <c:forEach var="error" items="${errors.entrySet}">
                                    <t:error errorName="${error.key}"/>
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:url var="createOrderUrl" value="${URL.CLIENT_ORDER_FORM}"/>
                        <form method="post" action="${createOrderUrl}" id="order-form">

                            <table class="table border table-text-center">
                                <thead>
                                <tr class="bg-light" id="table-header">
                                    <th scope="col"><fmt:message key="id"/></th>
                                    <th scope="col"><fmt:message key="item"/></th>
                                    <th scope="col"><fmt:message key="title"/></th>
                                    <th scope="col"><fmt:message key="price"/></th>
                                    <th scope="col"><fmt:message key="quantity"/></th>
                                    <th scope="col"><fmt:message key="page.items.remove"/></th>
                                </tr>
                                </thead>

                                <tbody class="table-sm">

                                <c:forEach var="orderItem" items="${orderContent}" varStatus="status">
                                    <c:url var="imageUrl"
                                           value="${URL.IMAGE_PREFIX.concat(orderItem.key.pictureId)}"/>
                                    <c:set var="rowId" value="${'dish-row-'.concat(status.index)}"/>
                                    <c:set var="dishAmount" value="${'dish-number-'.concat(status.index)}"/>

                                    <tr id="${rowId}">
                                        <td>
                                            <input name="${Attribute.ITEM_ID}" value="${orderItem.key.id}" readonly
                                                   size="3">
                                        </td>
                                        <td>
                                            <img src="${imageUrl}"/>
                                        </td>
                                        <td>
                                                ${orderItem.key.title.value.get(lang)}
                                        </td>
                                        <td>${orderItem.key.price.value}</td>
                                        <td>
                                            <input class="border" name="${Attribute.ORDER_ITEM_QUANTITY}"
                                                   value="${orderItem.value}"
                                                   type="number" min="1"/>
                                        </td>
                                        <td>
                                            <i class="fa fa-trash p-2" onclick="deleteOrderItemRow('${rowId}')"
                                               aria-hidden="true"></i>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                            <button form="order-form" type="submit"
                                    class="btn btn-success rounded-0 btn-lg float-right">
                                <fmt:message key="submit"/>
                            </button>

                        </form>
                        <c:url var="deleteBucket" value="${URL.CLIENT_CLEAR_ORDER_BUCKET}"/>

                        <form method="post" action="${deleteBucket}">
                            <button class="btn btn-warning rounded-0 btn-lg float-right mr-3">
                                <fmt:message key="delete"/>
                            </button>
                        </form>


                    </div>
                </c:when>

                <c:otherwise>
                    <div class="col-lg-5">
                        <div class="alert alert-info" role="alert">
                            <h5 class="p-5">
                                <fmt:message key="page.items.emptyCard"/>
                            </h5>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>

    </div>
</t:page>