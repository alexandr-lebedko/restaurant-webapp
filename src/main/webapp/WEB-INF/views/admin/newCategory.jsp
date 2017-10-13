<%--
  alexandr.lebedko : 03.08.2017
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

<c:set var="pageUri" value="app/admin/menu/category/new"/>
<c:set var="contextUri" value="${pageContext.request.contextPath}"/>
<c:set var="enTitle" value="${category.value.get('en')}"/>
<c:set var="ukrTitle" value="${category.value.get('uk_UA')}"/>
<c:set var="ruTitle" value="${category.value.get('ru_RU')}"/>

<c:set var="isCategoryCreated" value="${(category ne null) && errors == null}"/>


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
                    <h2><i class="glyphicon glyphicon-th-list"></i>
                        <fmt:message key="page.admin.menu"/></h2>
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
                        <li><a href="${pageUri}?lang=ru_RU">Русский</a></li>
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

                    <li role="navigation">
                        <a href="${contextUri}/app/admin/main" class="side-bar-item">
                            <i class="glyphicon glyphicon-tower"></i>
                            <fmt:message key="page.admin.label.main"/>
                        </a></li>

                    <li role="navigation">
                        <a href="" class="side-bar-item">
                            <i class="glyphicon glyphicon-cutlery"></i>
                            <fmt:message key="page.admin.label.orders"/>
                            <span class="badge">2</span>
                        </a>
                    </li>

                    <li role="navigation">
                        <a href="#" class="side-bar-item">
                            <i class="glyphicon glyphicon-shopping-cart"></i>
                            <fmt:message key="page.admin.label.invoices"/>
                            <span class="badge">1</span>
                        </a>
                    </li>

                    <li role="navigation">
                        <a href="${contextUri}/app/admin/menu" class="side-bar-item active-pill">
                            <i class="glyphicon glyphicon-th-list"></i>
                            <fmt:message key="page.admin.label.menu"/>
                        </a>
                    </li>

                    <li role="navigation">
                        <a href="#" class="side-bar-item">
                            <i class="glyphicon glyphicon-off"></i>
                            <fmt:message key="page.header.signOut"/>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="col-md-10 page-content">

                <div class="row page-content-header">
                    <div class="col-md-12">
                        <h2><fmt:message key="page.admin.newCategory"/></h2>
                    </div>
                </div>


                <div class="row" id="new-category-form">
                    <div class="col-md-5 col-md-offset-3">

                        <form id="category-form" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <span><t:error errorName="invalid-category"/></span>
                                <span><t:error errorName="category-exists"/></span>
                            </div>


                            <div class="form-group has-feedback">
                                <label>Назва</label>
                                <input class="form-control ukr-input"
                                       name="ukr-title"
                                       value="${ukrTitle}"
                                       placeholder="Введiть Назву">
                            </div>

                            <div class="form-group">
                                <label>Title</label>
                                <input class="form-control us-input"
                                       name="en-title"
                                       value="${enTitle}"
                                       placeholder="Enter Title">
                            </div>

                            <div class="form-group">
                                <label>Название</label>
                                <input class="ru-input form-control"
                                       name="ru-title"
                                       value="${ruTitle}"
                                       placeholder="Введите Название">
                            </div>

                            <div class="form-group">
                                <img id="img" height="200px" width="250px">
                            </div>

                            <div class="form-group">
                                <label><fmt:message key="page.image"/></label>
                                <input type="file"
                                       onchange="previewImage(event, 'img')"
                                       name="image"
                                >
                            </div>

                            <button class="btn btn-default"><fmt:message key="page.button.submit"/></button>
                        </form>


                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
<%--rgba(19, 25, 53, 0.95)--%>
<footer style="background-color: rgba(19, 25, 53, 0.95); height: 15px">

</footer>


<script src="js/jquery-1.12.4.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/script.js"></script>

</body>
</html>
