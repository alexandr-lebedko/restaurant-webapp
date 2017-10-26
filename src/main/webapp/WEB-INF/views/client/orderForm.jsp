<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_ORDER_FORM}">

    <div class="container main-content">

        <div class="row justify-content-center">

            <c:choose>
                <c:when test="${orderContent ne null}">
                    <div class="col-lg-11">

                        <table class="table border " style="user-select: none">
                            <thead>
                            <tr class="bg-light" id="table-header">
                                <th scope="col">ID</th>
                                <th scope="col"><fmt:message key="page.items.item"/></th>
                                <th scope="col"><fmt:message key="page.items.title"/></th>
                                <th scope="col"><fmt:message key="page.items.table.price"/></th>
                                <th scope="col"><fmt:message key="page.items.quantity"/></th>
                                <th scope="col"><fmt:message key="page.items.remove"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:set var="rowCounter" value="0"/>

                            <c:forEach var="entry" items="${orderContent}">
                                <c:set var="itemId" value="${entry.key.id}"/>
                                <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(entry.key.pictureId)}"/>
                                <c:set var="title" value="${entry.key.title.value.get(lang)}"/>
                                <c:set var="price" value="${entry.key.price.value}"/>
                                <c:set var="amount" value="${entry.value}"/>

                                <c:set var="dishRowSelector" value="${'dish-row-'.concat(rowCounter)}"/>
                                <c:set var="dishAmountSelector" value="${'dish-number-'.concat(rowCounter)}"/>


                                <tr id="${dishRowSelector}">
                                    <td class="item-id">${itemId}</td>
                                    <td><img src="${imageUrl}"/></td>
                                    <td>${title}</td>
                                    <td>${price}</td>
                                    <td class="set-amount-cell">
                                        <div class="set-amount-wrapper">
                                            <div class="minus-button set-amount-button"
                                                 onclick="minusItemBasket('${dishAmountSelector}')">
                                                <i class="fa fa-minus" aria-hidden="true"></i>
                                            </div>

                                            <div id="${dishAmountSelector}" class="item-amount">
                                                    ${amount}
                                            </div>

                                            <div class="plus-button set-amount-button"
                                                 onclick="plusItemBasket('${dishAmountSelector}')">
                                                <i class="fa fa-plus" aria-hidden="true"></i>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="trash-button" onclick="deleteItemRow('${dishRowSelector}')">
                                            <i class="fa fa-trash" aria-hidden="true"></i>
                                        </div>
                                    </td>

                                </tr>

                                <c:set var="rowCounter" value="${rowCounter+1}"/>
                            </c:forEach>
                            </tbody>

                        </table>

                        <form method="post" onsubmit="return false">
                            <button class="btn btn-light float-right btn-lg " type="submit" onclick="submitOrder()">
                                <fmt:message key="page.items.order.submit"/>
                            </button>
                        </form>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="col-lg-5 text-center ">
                        <h5 class="bg-light" style="padding: 2rem"><fmt:message key="page.items.emptyCard"/> <i class="ml-3 fa fa-frown-o" aria-hidden="true"></i></h5>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>

    </div>
</t:page>