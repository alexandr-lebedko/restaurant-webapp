<%@tag description="Header for unauthenticated user" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag import="net.lebedko.i18n.SupportedLocales" %>
<%@ tag import="net.lebedko.web.util.constant.URL" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:set var="contextUrl" value="${pageContext.request.contextPath}"/>

<c:set var="signIn" value="${contextUrl.concat(URL.SIGN_IN)}" scope="page"/>
<c:set var="signUp" value="${contextUrl.concat(URL.SIGN_UP)}" scope="page"/>


<%@attribute name="pageUrl" type="java.lang.String" required="true" %>

<c:set var="enPage"
       value="${pageUrl.concat('?').concat(SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME).concat('=').concat(SupportedLocales.EN_CODE)}"/>
<c:set var="ruPage"
       value="${pageUrl.concat('?').concat(SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME).concat('=').concat(SupportedLocales.RU_CODE)}"/>


<header class="bg-light">
    <div class="container">
        <nav class="navbar fixed-top navbar-light bg-light navbar-expand-md ">
            <a class="navbar-brand" href="#"><i class="fa fa-cutlery"></i>Restaurant</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${signIn}">
                            <i class="fa fa-sign-in" aria-hidden="true"></i>
                            <span><fmt:message key="page.signUp.signIn"/></span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${signUp}">
                            <i class="fa fa-user-plus" aria-hidden="true"></i>
                            <span><fmt:message key="page.signIn.signUp"/></span>
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
                </ul>
            </div>
        </nav>
    </div>
</header>