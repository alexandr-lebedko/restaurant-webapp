<%@tag description="Header for unauthenticated user" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag import="net.lebedko.web.util.constant.URL" %>
<%@ tag import="net.lebedko.i18n.SupportedLocales" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:url var="signOut" value="${URL.SIGN_OUT}"/>
<c:url var="orderCart" value="${URL.CLIENT_ORDER_FORM}"/>
<c:url var="orders" value="${URL.CLIENT_ORDERS}"/>
<c:url var="invoices" value="${URL.CLIENT_INVOICES}"/>
<c:url var="menu" value="${URL.CLIENT_CATEGORIES}"/>


<%@attribute name="pageUrl" required="true" %>

<c:url var="enPage" value="${pageUrl}">
    <c:param name="${SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME}" value="${SupportedLocales.EN_CODE}"/>
</c:url>

<c:url var="ruPage" value="${pageUrl}">
    <c:param name="${SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME}" value="${SupportedLocales.RU_CODE}"/>
</c:url>

<header class="bg-light">

    <div class="container">

        <nav class="navbar navbar-light bg-light navbar-expand-md">

            <a class="navbar-brand" href="#"><i class="fa fa-cutlery"></i>Restaurant</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">

                <ul class="navbar-nav">

                    <li class="nav-item">
                        <a class="nav-link" href="${invoices}">
                            <i class="fa fa-credit-card" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.invoice"/></span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${menu}">
                            <i class="fa fa-th" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.categories"/></span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${orders}">
                            <i class="fa fa-list-ul" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.orders"/></span>
                        </a>
                    </li>

                    <li class="nav-item" id="order-bucket-nav">
                        <a class="nav-link" href="${orderCart}">
                            <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                            <span id="bucket-amount" class="badge">${orderAmount}</span>
                            <span><fmt:message key="page.header.orderCart"/></span>
                        </a>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true">
                            <i class="fa fa-language"></i>
                            <c:choose>
                                <c:when test="${SupportedLocales.getByCode(SupportedLocales.EN_CODE).equals(lang)}">
                                    English
                                </c:when>
                                <c:when test="${SupportedLocales.getByCode(SupportedLocales.RU_CODE).equals(lang)}">
                                    Русский
                                </c:when>
                            </c:choose>
                        </a>

                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="${enPage}">English</a>
                            <a class="dropdown-item" href="${ruPage}">Русский</a>
                        </div>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${signOut}">
                            <i class="fa fa-sign-out" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.signOut"/> </span>
                        </a>
                    </li>

                </ul>

            </div>

        </nav>

    </div>

</header>