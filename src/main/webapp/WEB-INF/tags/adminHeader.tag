<%@tag description="Header for unauthenticated user" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag import="net.lebedko.web.util.constant.URL" %>
<%@ tag import="net.lebedko.i18n.SupportedLocales" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<%@attribute name="pageUrl" required="true" %>


<c:url var="main" value="${URL.ADMIN_MAIN}"/>
<c:url var="orders" value="${URL.ADMIN_ORDERS}"/>
<c:url var="invoices" value="${URL.ADMIN_INVOICES}"/>
<c:url var="menu" value="${URL.ADMIN_MENU}"/>
<c:url var="signOut" value="${URL.SIGN_OUT}"/>

<c:url var="enPage" value="${pageUrl}">
    <c:param name="${SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME}" value="${SupportedLocales.EN_CODE}"/>
</c:url>

<c:url var="ruPage" value="${pageUrl}">
    <c:param name="${SupportedLocales.LOCALE_REQUEST_ATTRIBUTE_NAME}" value="${SupportedLocales.RU_CODE}"/>
</c:url>

<header class="bg-light">

    <div class="container">

        <nav class="navbar navbar-light bg-light navbar-expand-md">

            <a class="navbar-brand" href="#"><i class="fa fa-cutlery"></i>Administration</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">


                <ul class="navbar-nav">

                    <li class="nav-item">
                        <a class="nav-link" href="${main}">
                            <i class="fa fa-home" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.main"/> </span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link badged"  href="${invoices}">
                            <i class="fa fa-credit-card" aria-hidden="true"></i>
                            <span class="badge">${unprocessedInvoiceNum}</span>
                            <span><fmt:message key="page.header.invoices"/> </span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link badged" href="${orders}">
                            <i class="fa fa-list-ul" aria-hidden="true"></i>
                            <span class="badge">${unprocessedOrderNum}</span>
                            <span><fmt:message key="page.header.orders"/> </span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${menu}">
                            <i class="fa fa-th" aria-hidden="true"></i>
                            <span><fmt:message key="page.header.menu"/> </span>
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