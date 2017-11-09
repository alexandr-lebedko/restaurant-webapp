<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.entity.invoice.State" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_INVOICES}">

    <div class="container main-content">

        <c:if test="${currentInvoice ne null}">

            <div class="row">

                <div class="col-lg-3">
                    <c:set var="invoice" value="${currentInvoice.key}"/>
                    <c:set var="invoiceId" value="${invoice.id}"/>
                    <c:set var="invoiceState" value="${invoice.state}"/>
                    <c:set var="creationDate" value="${invoice.createdOn.toLocalDate()}"/>
                    <c:set var="creationTime" value="${invoice.createdOn.toLocalTime()}"/>


                    <h5 class="mb-3 ml-3"><fmt:message key="page.invoice.details"/></h5>
                    <table class="table border table-bordered">
                        <tbody>
                        <tr>
                            <th class="text-center bg-light">ID</th>
                            <td class="text-center ">${invoiceId}</td>
                        </tr>
                        <tr>
                            <th class="text-center bg-light"><fmt:message key="page.label.state"/> </th>
                            <td class="text-center">${invoiceState}</td>
                        </tr>
                        <tr>
                            <th class="text-center bg-light"><fmt:message key="page.label.date"/></th>
                            <td class="text-center">${creationDate}</td>
                        </tr>
                        <tr>
                            <th class="text-center bg-light"><fmt:message key="page.label.time"/></th>
                            <td class="text-center">${creationTime}</td>
                        </tr>
                        </tbody>
                    </table>

                    <c:choose>
                        <c:when test="${invoice.state eq State.ACTIVE}">
                            <c:url var="closeInvoiceUrl" value="${URL.CLIENT_CLOSE_INVOICE}"/>

                            <form method="post" action="${closeInvoiceUrl}">
                                <input type="hidden" name="id" value="${invoice.id}">
                                <button class="btn btn-block btn-warning btn-sm rounded-0 font-weight-bold">
                                    <fmt:message key="page.invoice.button.closeInvoice"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post">
                                <input type="hidden" name="id" value="${invoice.id}">
                                <button class="btn btn-block btn-warning btn-sm rounded-0">PAY INVOICE</button>
                            </form>
                        </c:otherwise>

                    </c:choose>
                </div>

                <div class="col-lg-9 text-center" id="client-invoices">
                    <c:if test="${errors ne null}">
                        <div class="alert alert-warning">
                            <c:forEach var="error" items="${errors.entrySet}">
                                <t:error errorName="${error.key}"/>
                            </c:forEach>
                        </div>
                    </c:if>

                    <c:set var="invoiceContent" value="${currentInvoice.value}"/>


                    <h4 class="mb-3"><fmt:message key="page.invoice.content"/></h4>

                    <table class="table border">
                        <thead class="bg-light">
                        <tr>
                            <th class="text-center"><fmt:message key="page.items.item"/></th>
                            <th class="text-center"><fmt:message key="page.items.title"/></th>
                            <th class="text-center"><fmt:message key="page.order.state"/></th>
                            <th class="text-center"><fmt:message key="page.items.table.price"/></th>
                            <th class="text-center"><fmt:message key="page.items.quantity"/></th>
                            <th class="text-center"><fmt:message key="page.items.total"/></th>
                        </tr>
                        </thead>

                        <tbody>

                        <c:set var="invoiceTotalPrice" value="0"/>

                        <c:forEach var="orderItem" items="${invoiceContent}">

                            <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(orderItem.item.pictureId)}"/>
                            <c:set var="itemTitle" value="${orderItem.item.info.title.value.get(lang)}"/>
                            <c:set var="itemPrice" value="${orderItem.item.info.price.value}"/>
                            <c:set var="itemStatus" value="${orderItem.order.state}"/>
                            <c:set var="itemAmount" value="${orderItem.quantity}"/>
                            <c:set var="orderItemPrice" value="${itemAmount*itemPrice}"/>
                            <c:set var="invoiceTotalPrice" value="${invoiceTotalPrice+orderItemPrice}"/>

                            <tr>
                                <td><img src="${imageUrl}"></td>
                                <td>${itemTitle}</td>
                                <td>${itemStatus}</td>
                                <td>${itemPrice}</td>
                                <td>${itemAmount}</td>
                                <td>${orderItemPrice}</td>
                            </tr>

                        </c:forEach>
                        <tr class="bg-light">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td class="bg-warning  p-3">${invoiceTotalPrice}</td>
                        </tr>
                        </tbody>

                    </table>

                    <c:choose>
                        <c:when test="${invoice.state eq State.ACTIVE}">
                            <form method="post" action="${closeInvoiceUrl}">
                                <input type="hidden" name="id" value="${invoice.id}">
                                <button class="btn btn-lg float-left btn-warning rounded-0 pl-5 pr-5 p-1">
                                    <fmt:message key="page.invoice.button.closeInvoice"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post">
                                <input type="hidden" name="id" value="${invoice.id}">
                                <button class="btn btn-lg float-left btn-warning rounded-0 font-weight-bold p-3 ">PAY
                                    INVOICE
                                </button>
                            </form>
                        </c:otherwise>

                    </c:choose>

                </div>
            </div>
        </c:if>
    </div>
</t:page>