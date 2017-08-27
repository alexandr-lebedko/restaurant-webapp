<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="lang"
       value="${not empty param.lang ? param.lang: not empty lang ? lang: pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<c:set var="pageUri" value="app/signIn" scope="page"/>


<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>
    <title><fmt:message key="page.signIn.title"/></title>


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
                    <h2><fmt:message key="page.signIn"/></h2>
                </div>
            </div>
            <div class=" col-md-2 col-md-offset-4" id="button-area">
                <div class="button">
                    <a href="app/signUp" class="btn" role="button"><fmt:message key="page.signIn.signUp"/></a>
                </div>
                <div class="dropdown">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown">
                        <c:choose>
                            <c:when test="${lang == 'en'}">
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
                <img src="img/restaurant1600.png">
            </div>
            <div class="col-md-offset-3 col-md-4" id="sign-in-form">
                <form method="post">
                    <div class="form-group">
                        <label for="email"><fmt:message key="page.signIn.form.email.label"/></label>
                        <input type="email"
                               class="form-control"
                               id="email"
                               name="email"
                               placeholder="<fmt:message key="page.signIn.form.email.placeholder"/>"
                               value="${user.emailAddress}"/>

                        <span><t:error errorName="user not exists"/></span>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="page.signIn.form.password.label"/> </label>
                        <input type="password"
                               class="form-control"
                               name="password"
                               placeholder="<fmt:message key="page.signIn.form.password.placeholder"/>"
                               value="${user.password.passwordString}"/>

                        <span><t:error errorName="wrong password"/></span>
                    </div>
                    <button type="submit" class="btn btn-group-sm"><fmt:message key="page.signIn.form.submit"/></button>
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
