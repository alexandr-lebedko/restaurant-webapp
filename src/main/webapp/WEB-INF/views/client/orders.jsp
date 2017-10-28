<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_ORDERS}">
    <div class="container main-content" id="client-orders">
        <div class="row justify-content-center">
            <div class="col-lg-11 ">
                <table class="table border">
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
    </div>
</t:page>