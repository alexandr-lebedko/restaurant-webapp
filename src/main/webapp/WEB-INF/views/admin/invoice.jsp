<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.entity.invoice.InvoiceState" %>


<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.ADMIN_INVOICE.concat('?').concat(Attribute.INVOICE_ID).concat('=').concat(param.get(Attribute.INVOICE_ID))}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <c:set var="invoice" value="${requestScope.get(Attribute.INVOICE)}"/>
                <table class="table border">
                    <tbody>
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <td>${invoice.id}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="user"/></th>
                        <td>${invoice.user.fullName}</td>
                    </tr>
                    <tr class="${invoice.state}">
                        <th><fmt:message key="state"/></th>
                        <td><fmt:message key="${invoice.state}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="date"/></th>
                        <td>${invoice.createdOn.toLocalDate()}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="time"/></th>
                        <td>${invoice.createdOn.toLocalTime()}</td>
                    </tr>
                    <tr>
                        <th><fmt:message key="total"/></th>
                        <td>${invoice.amount.value}</td>
                    </tr>
                    </tbody>
                </table>

                <c:if test="${invoice.state eq State.CLOSED}">
                    <form method="post" id="invoice-submit">
                        <input type="hidden" name="${Attribute.INVOICE_ID}" value="${invoice.id}">
                        <button class="btn btn-block btn-success rounded-0"><fmt:message key="invoice.submit"/></button>
                    </form>
                </c:if>
            </div>

            <div class="col-lg-8">

                <table class="table table-text-center table-bordered table-sm">
                    <thead>
                    <tr>
                        <th><fmt:message key="order"/></th>
                        <th><fmt:message key="state"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><i class="fa fa-trash pr-3"></i></th>
                        <th><i class="fa fa-info"></i></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="entry" items="${requestScope.get(Attribute.ITEMS_TO_ORDER)}">
                        <c:set var="order" value="${entry.key}"/>

                        <tr>
                            <td>${order.id}</td>
                            <td class="${order.state} "><fmt:message key="${order.state}"/></td>
                            <td>${order.createdOn.toLocalDate()}</td>
                            <td>${order.createdOn.toLocalTime()}</td>

                            <td>
                                <span class="badge badge-danger span-btn">
                                    <fmt:message key="delete"/>
                                </span>
                            </td>
                            <td>
                                <span class="info-btn badge-light badge span-btn">
                                    <fmt:message key="info"/>
                                </span>
                            </td>
                        </tr>

                        <tr class="order-content">
                            <td colspan="6">
                                <h6 class="mt-3 mb-3"><fmt:message key="order.content"/></h6>

                                <table class="table table-bordered inner-table mb-4">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="item"/></th>
                                        <th><fmt:message key="quantity"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="orderItem" items="${entry.value}">
                                        <tr>
                                            <td>${orderItem.item.title.value.get(lang)}</td>
                                            <td>${orderItem.quantity}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <style>

            </style>
        </div>
    </div>
</t:page>