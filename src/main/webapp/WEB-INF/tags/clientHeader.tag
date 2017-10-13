<%@tag description="Header for unauthenticated user" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag import="net.lebedko.web.util.constant.WebConstant.URL" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<%@attribute name="pageUrl" required="true" %>

<c:set var="enPage"
       value="${pageUrl.concat('?').concat(SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME).concat('=').concat(SupportedLocales.EN_CODE)}"/>
<c:set var="ruPage"
       value="${pageUrl.concat('?').concat(SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME).concat('=').concat(SupportedLocales.RU_CODE)}"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="signOut" value="${contextPath.concat(URL.SIGN_OUT)}"/>
<c:set var="orderCart" value="${contextPath.concat(URL.CLIENT_ORDER_FORM)}"/>
<c:set var="orders" value="${contextPath.concat(URL.CLIENT_ORDERS)}"/>
<c:set var="invoices" value="${contextPath.concat(URL.CLIENT_INVOICES)}"/>
<c:set var="menu" value="${contextPath.concat(URL.CLIENT_CATEGORIES)}"/>


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
                            <span><fmt:message key="page.header.invoices"/></span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${menu}">
                            <i class="fa fa-th" aria-hidden="true"></i>
                            <span><fmt:message key="page.signUp.form.firstName.label"/></span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${orders}">
                            <i class="fa fa-list-ul" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.orders"/></span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${orderCart}">
                            <i class="fa fa-shopping-cart" aria-hidden="true"></i>
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