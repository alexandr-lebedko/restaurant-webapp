<%@tag description="Header for unauthenticated user" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag import="net.lebedko.util.SupportedLocales" %>
<%@ tag import="net.lebedko.web.util.constant.URL" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<%@attribute name="pageUrl" type="java.lang.String" required="true" %>

<c:url var="enPage" value="${pageUrl}">
    <c:param name="${SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME}" value="${SupportedLocales.EN_CODE}"/>
</c:url>

<c:url var="ruPage" value="${pageUrl}">
    <c:param name="${SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME}" value="${SupportedLocales.RU_CODE}"/>
</c:url>

<c:url var="signIn" value="${URL.SIGN_IN}" scope="page"/>
<c:url var="signUp" value="${URL.SIGN_UP}" scope="page"/>

<header class="bg-light">
    <div class="container">
        <nav class="navbar navbar-light bg-light navbar-expand-md ">
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
                            <span><fmt:message key="signIn"/></span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${signUp}">
                            <i class="fa fa-user-plus" aria-hidden="true"></i>
                            <span><fmt:message key="signUp"/></span>
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