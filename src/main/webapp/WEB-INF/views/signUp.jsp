<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="language"
       value="${not empty param.lang ? param.lang: not empty language ? language: pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization"/>

<c:set var="pageUri" value="app/signUp" scope="page"/>

<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>
    <title><fmt:message key="page.signUp.title"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
</head>

<body>
<header>
    <div class="container">
        <div class="row">
            <div class="col-md-2" id="header-title">
                <div>
                    <a class="navbar-brand" onclick="false"><fmt:message key="page.restaurant"/></a>
                </div>
            </div>
            <div class="col-md-3 col-sm-6" id="page-title">
                <div>
                    <h2><fmt:message key="page.signUp"/></h2>
                </div>
            </div>
            <div class=" col-md-2 col-md-offset-4" id="button-area">
                <div class="button">
                    <a href="app/signIn" class="btn" role="button"><fmt:message key="page.signUp.signIn"/></a>
                </div>
                <div class="dropdown">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown">
                        <c:choose>
                            <c:when test="${language == 'en'}">
                                <c:out value="EN"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="RU"/>
                            </c:otherwise>
                        </c:choose>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="${pageUri}?lang=en">English</a></li>
                        <li><a href="${pageUri}?lang=ru">Русский</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>


<section class="form-section">
    <div class="container">
        <div class="row">

            <div class="col-md-4" id="picture-area">
                <img src="img/restaurant_pickup1600.png">
            </div>

            <div class="col-md-offset-3 col-md-4" id="sign-in-form">
                <form method="post">
                    <div class="form-group">
                        <label for="firstName"><fmt:message key="page.signUp.form.firstName.label"/></label>

                        <input name="firstName"
                               type="text"
                               class="form-control"
                               id="firstName"
                               placeholder="<fmt:message key="page.signUp.form.firstName.placeholder"/>"
                               value="${user.fullName.firstName}">

                        <span><t:error errorName="firstName"/></span>
                    </div>

                    <div class="form-group">
                        <label for="lastName"><fmt:message key="page.signUp.form.lastName.label"/></label>
                        <input name="lastName"
                               type="text"
                               class="form-control"
                               id="lastName"
                               placeholder="<fmt:message key="page.signUp.form.lastName.placeholder"/>"
                               value="${user.fullName.lastName}">

                        <span><t:error errorName="lastName"/></span>
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key="page.signUp.form.email.label"/></label>
                        <input name="email"
                               type="email"
                               class="form-control"
                               id="email"
                               placeholder="<fmt:message key="page.signUp.form.email.placeholder"/>"
                               value="${user.email}">

                        <span>
                            <t:error errorName="email"/>
                            <t:error errorName="user exists"/>
                        </span>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="page.signUp.form.password.label"/></label>
                        <input name="password"
                               type="password"
                               class="form-control"
                               placeholder="<fmt:message key="page.signUp.form.password.placeholder"/>"
                               value="${user.password.passwordString}"/>

                        <span><t:error errorName="password"/></span>
                    </div>
                    <button type="submit" class="btn btn-group-sm">
                        <fmt:message key="page.signUp.form.submit"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <p>
                    alexandr.lebedko
                </p>
            </div>
            <div class="col-md-3 col-md-offset-6">
                <p>Copyright &copy; 2017, All Rights Reserved</p>
            </div>

        </div>
    </div>
</footer>

<script src="js/jquery-1.12.4.min.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>
