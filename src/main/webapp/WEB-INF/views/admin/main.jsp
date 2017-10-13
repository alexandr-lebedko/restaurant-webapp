<%--
  alexandr.lebedko : 29.06.2017
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="lang"
       value="${not empty param.lang ? param.lang: not empty lang ? lang: pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<c:set var="pageUri" value="app/admin/main" scope="page"/>


<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>
    <title><fmt:message key="page.administration.main.title"/></title>


    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>


</head>


<body>
<header>
    <div class="container">
        <div class="row">
            <div class="col-md-2" id="header-title">
                <div>
                    <a class="navbar-brand" onclick="false"><fmt:message key="page.administration"/></a>
                </div>
            </div>
            <div class="col-md-3 col-sm-6" id="page-title">
                <div>
                    <h2><fmt:message key="page.administration.main"/></h2>
                </div>
            </div>
            <div class=" col-md-2 col-md-offset-4" id="button-area">
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

<section class="middle-section">
    <div class="container" id="admin-middle-section">
        <div class="row">
            <div class="col-md-2" id="side-bar">
                <ul class="nav nav-pills nav-stacked">
                    <li role="presentation" id="sidebar-header"></li>

                    <li role="presentation">
                        <a href="" class="side-bar-item">
                            <i class="glyphicon glyphicon-tower"></i>
                            <fmt:message key="page.admin.label.main"/>
                        </a></li>

                    <li role="presentation">
                        <a href="" class="side-bar-item">
                            <i class="glyphicon glyphicon-cutlery"></i>
                            <fmt:message key="page.admin.label.orders"/>
                            <span class="badge">2</span>
                        </a>
                    </li>

                    <li role="presentation">
                        <a href="#" class="side-bar-item">
                            <i class="glyphicon glyphicon-shopping-cart"></i>
                            <fmt:message key="page.admin.label.invoices"/>
                            <span class="badge">1</span>
                        </a>
                    </li>

                    <li role="presentation">
                        <a href="#" class="side-bar-item">
                            <i class="glyphicon glyphicon-th-list"></i>
                            <fmt:message key="page.admin.label.menu"/>
                        </a>
                    </li>

                    <li role="presentation">
                        <a href="#" class="side-bar-item">
                            <i class="glyphicon glyphicon-off"></i>
                            <fmt:message key="page.header.signOut"/>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="col-md-10" id="page-content">

            </div>
        </div>
    </div>
</section>

<%--<footer>--%>
<%--<div class="container">--%>
<%--<div class="row">--%>
<%--<div class="col-md-3">--%>
<%--<p>--%>
<%--alexandr.lebedko--%>
<%--</p>--%>
<%--</div>--%>
<%--<div class="col-md-3 col-md-offset-6">--%>
<%--<p>Copyright &copy; 2017, All Rights Reserved</p>--%>
<%--</div>--%>

<%--</div>--%>
<%--</div>--%>
<%--</footer>--%>


<script src="js/jquery-1.12.4.min.js"></script>
<script src="js/bootstrap.js"></script>

</body>
</html>
