<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.invoice.InvoiceState" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_INVOICE.concat('?').concat(Attribute.INVOICE_ID).concat('=').concat(param.get(Attribute.INVOICE_ID))}">

    <div class="container main-content">
        <div class="row justify-content-between">

            <div class="col-lg-3">

                <c:set var="invoice" value="${requestScope.get(Attribute.INVOICE)}"/>

                <h5 class="mb-3 mt-3 text-center">
                    <fmt:message key="invoice.details"/>
                </h5>

                <table class="table border table-bordered">
                    <tbody>
                    <tr>
                        <th class="text-center"><fmt:message key="id"/></th>
                        <td class="text-center ">${invoice.id}</td>
                    </tr>
                    <tr class="${invoice.state}">
                        <th class="text-center"><fmt:message key="state"/></th>
                        <td class="text-center"><fmt:message key="${invoice.state}"/></td>
                    </tr>
                    <tr>
                        <th class="text-center"><fmt:message key="date"/></th>
                        <td class="text-center">${invoice.createdOn.toLocalDate()}</td>
                    </tr>
                    <tr>
                        <th class="text-center"><fmt:message key="time"/></th>
                        <td class="text-center">${invoice.createdOn.toLocalTime()}</td>
                    </tr>
                    <c:if test="${invoice.amount.value ne 0}">
                        <tr>
                            <th class="text-center"><fmt:message key="price"/></th>
                            <td class="text-center">${invoice.amount.value}</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>


                <c:if test="${invoice.state eq InvoiceState.UNPAID}">
                    <c:url var="payInvoice" value="${URL.CLIENT_PAY_INVOICE}"/>
                    <form id="pay-invoice-form" method="post" action="${payInvoice}">
                        <input type="hidden" name="${Attribute.INVOICE_ID}" value="${invoice.id}">
                        <button class="btn btn-block btn-lg btn-warning rounded-0">
                            <fmt:message key="invoice.pay"/>
                        </button>
                    </form>
                </c:if>

            </div>

            <div class="col-lg-8">

                <c:if test="${!empty errors}">
                    <div class="alert alert-warning text-center">
                        <c:forEach var="error" items="${errors.entrySet}">
                            <t:error errorName="${error.key}"/>
                        </c:forEach>
                    </div>
                </c:if>


                <h4 class="mb-3 text-center">
                    <fmt:message key="invoice.content"/>
                </h4>
                <table class="table border table-text-center table-bordered">
                    <thead class="bg-light">
                    <tr>
                        <th><fmt:message key="order"/></th>
                        <th><fmt:message key="item"/></th>
                        <th><fmt:message key="title"/></th>
                        <th><fmt:message key="state"/></th>
                        <th><fmt:message key="price"/></th>
                        <th><fmt:message key="quantity"/></th>
                        <th><fmt:message key="total"/></th>
                    </tr>
                    </thead>

                    <tbody class="table-sm">

                    <c:set var="invoiceTotalPrice" value="0"/>
                    <c:set var="orderItems" value="${requestScope.get(Attribute.ORDER_ITEMS)}"/>

                    <c:forEach var="orderItem" items="${orderItems}">
                        <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(orderItem.item.pictureId)}"/>
                        <c:set var="invoiceTotalPrice" value="${invoiceTotalPrice+orderItem.price.value}"/>

                        <c:url var="orderUrl" value="${URL.CLIENT_ORDER}">
                            <c:param name="${Attribute.ORDER_ID}" value="${orderItem.order.id}"/>
                        </c:url>

                        <tr>
                            <td><a class="badge badge-info" href="${orderUrl}">${orderItem.order.id}</a></td>
                            <td><img src="${imageUrl}"></td>
                            <td>${orderItem.item.info.title.value.get(lang)}</td>
                            <td class="${orderItem.order.state}"><fmt:message key="${orderItem.order.state}"/></td>
                            <td>${orderItem.item.info.price.value}</td>
                            <td>${orderItem.quantity}</td>
                            <td>${orderItem.price.value}</td>
                        </tr>

                    </c:forEach>

                    </tbody>
                </table>

                <c:if test="${invoice.state eq InvoiceState.UNPAID}">
                    <button type="submit" form="pay-invoice-form"
                            class="btn btn-lg float-right btn-warning rounded-0 font-weight-bold p-3 ">
                        <fmt:message key="invoice.pay"/>
                    </button>
                </c:if>

            </div>
        </div>
    </div>
</t:page>
