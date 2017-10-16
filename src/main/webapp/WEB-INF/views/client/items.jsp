<%--&lt;%&ndash;--%>
<%--alexandr.lebedko : 14.09.2017--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<%--<c:set var="lang"--%>
<%--value="${not empty param.lang ? param.lang: not empty lang ? lang: pageContext.request.locale}"--%>
<%--scope="session"/>--%>


<%--<fmt:setLocale value="${lang}"/>--%>
<%--<fmt:setBundle basename="localization"/>--%>

<%--<c:set var="contextUri" value="${pageContext.request.contextPath}"/>--%>
<%--<c:set var="imagesUri" value="${contextUri.concat('/images/')}"/>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--<style>--%>

<%--#categories .card img {--%>
<%--height: 12rem;--%>
<%--}--%>

<%--#categories .card {--%>
<%--margin-bottom: 30px;--%>
<%--}--%>

<%--#categories a:hover, #categories a:focus {--%>
<%--text-decoration: none;--%>
<%--border: solid 1px #5e5e5e;--%>
<%--}--%>

<%--</style>--%>
<%--<base href="${pageContext.request.contextPath}/"/>--%>
<%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"--%>
<%--integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">--%>
<%--<title>MainPage</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>

<%--<div class="row" style="width: 100%">--%>
<%--<div class="col" style="width: 100%">--%>
<%--<nav class="navbar navbar-dark justify-content-center" style="background-color: #e3f2fd;">--%>
<%--<ul class="nav">--%>
<%--<li class="nav-item">--%>
<%--<a class="nav-link active" href="#">Active</a>--%>
<%--</li>--%>
<%--<li class="nav-item">--%>
<%--<a class="nav-link" href="#">Link</a>--%>
<%--</li>--%>
<%--<li class="nav-item">--%>
<%--<a class="nav-link" href="#">Link</a>--%>
<%--</li>--%>
<%--<li class="nav-item">--%>
<%--<a class="nav-link disabled" href="#">Disabled</a>--%>
<%--</li>--%>
<%--</ul>--%>
<%--</nav>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="row justify-content-center" id="categories" style="margin-top:50px">--%>
<%--<div class="col">--%>
<%--<c:forEach var="item" items="${items}">--%>
<%--<ul class="list-group-item">--%>
<%--&lt;%&ndash;<img class="card-img-top" src="${imagesUri.concat(item.pictureId)}"/>&ndash;%&gt;--%>
<%--<h6 class="card-title">${item.title}</h6>--%>
<%--<form method="post">--%>
<%--<input type="hidden" name="itemId" value="${item.id}">--%>
<%--<button type="submit">Add to Bucket</button>--%>
<%--</form>--%>
<%--</ul>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</div>--%>
<%--<c:if test="${orderContent ne null}">--%>
<%--<div class="row">--%>
<%--<div class="col">--%>
<%--<table class="table table-striped table-inverse">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th>#</th>--%>
<%--<th>Title</th>--%>
<%--<th>Quantity</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--<c:forEach items="${orderContent}" var="entry">--%>
<%--<tr>--%>
<%--<th scope="row">${entry.key.id}</th>--%>
<%--<th>${entry.key.info.title.value.get(lang)}</th>--%>
<%--<th>${entry.value}</th>--%>
<%--</tr>--%>
<%--</c:forEach>--%>


<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="net.lebedko.web.util.constant.URL" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.CLIENT_MENU_ITEMS.concat('?category=').concat(param.category)}">

    <div class="container main-content" id="items">

        <div class="row">

            <div class="col-lg-3">
                <ul class="nav nav-pills flex-column bg-light">

                    <c:forEach var="category" items="${categories}">
                        <li class="nav-item">
                            <c:url var="itemsByCategoryUrl" value="${URL.CLIENT_MENU_ITEMS}">
                                <c:param name="category" value="${category.id}"/>
                            </c:url>

                            <c:choose>
                                <c:when test="${param.category eq category.id}">
                                    <a class="nav-link" id="current-category"
                                       href="${itemsByCategoryUrl}">${category.name}</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="nav-link" href="${itemsByCategoryUrl}">${category.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>

                </ul>
            </div>

            <div class="col-lg-9">
                <div class="row">

                    <c:forEach var="item" items="${items}">

                        <div class="col-lg-3">
                            <div class="card">
                                <a href="#">
                                    <c:url var="imageUrl"
                                           value="${URL.IMAGE_PREFIX.concat(item.pictureId)}"/>

                                    <img class="card-img-top" src="${imageUrl}">

                                    <div class="order-text-block text-center">
                                        <fmt:message key="page.items.order"/>
                                    </div>

                                    <div class="card-body">
                                        <h6 class="card-title">${item.title}</h6>
                                        <h6 class="card-subtitle mb-2 text-muted">
                                            <fmt:message key="page.items.price"/>${item.price}
                                        </h6>
                                    </div>
                                </a>
                            </div>
                        </div>

                    </c:forEach>

                </div>
            </div>

        </div>
    </div>

</t:page>