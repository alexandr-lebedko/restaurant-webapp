<%--
  alexandr.lebedko : 27.08.2017
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

<%@page import="net.lebedko.i18n.SupportedLocales" %>
<c:set var="enTitle" value="${item.title.value.get(SupportedLocales.EN_CODE)}"/>
<c:set var="uaTitle" value="${item.title.value.get(SupportedLocales.UA_CODE)}"/>
<c:set var="ruTitle" value="${item.title.value.get(SupportedLocales.RU_CODE)}"/>
<c:set var="enDescription" value="${item.description.value.get(SupportedLocales.EN_CODE)}"/>
<c:set var="uaDescription" value="${item.description.value.get(SupportedLocales.UA_CODE)}"/>
<c:set var="ruDescription" value="${item.description.value.get(SupportedLocales.RU_CODE)}"/>
<c:set var="price" value="${item.price.value}"/>

<c:set var="contextUri" value="${pageContext.request.contextPath}"/>
<c:set var="pageUri" value="app/admin/menu/item/new"/>


<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>
    <title><fmt:message key="page.admin.newItem.title"/></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">

    <style>
        .form-row {
            margin-bottom: 20px;
        }
    </style>
</head>


<body>
<div class="container-fluid">
    <div class="row">
        <div class="col" style="width: 100%">
            <nav class="navbar navbar-dark justify-content-center" style="background-color: #e3f2fd;">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Active</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#">Disabled</a>
                    </li>
                </ul>

            </nav>

        </div>
    </div>


    <div class="row justify-content-center" style="margin-top:50px">

        <c:choose>
            <c:when test="${newItem == null}">


                <c:if test="${errors ne null}">
                    <div class="col-lg-9 ">
                        <c:forEach var="orderItem" items="${errors.entrySet}">
                            <span> <t:error errorName="${orderItem.key}"/></span>
                            <br/>
                        </c:forEach>
                        <br/>
                    </div>
                </c:if>
                <div class="col-lg-9">

                    <form method="post" enctype="multipart/form-data">
                        <div class="form-row ">
                            <div class="col">
                                <input type="text" class="form-control" name="title_en" placeholder="Title"
                                       value="${enTitle}">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" name="title_ru" placeholder="Название"
                                       value="${ruTitle}">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" name="title_ua" placeholder="Назва"
                                       value="${uaTitle}">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="col">
                                <textarea class="form-control" name="description_en"
                                          placeholder="Description">${enDescription}</textarea>
                            </div>

                            <div class="col">
                                <textarea class="form-control" name="description_ru" placeholder="Описание">${ruDescription}</textarea>
                            </div>

                            <div class="col">
                                <textarea class="form-control" name="description_ua" placeholder="Опис">${uaDescription}</textarea>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col">
                                <select class="form-control " required name="category" autocomplete="off">
                                    <option disabled selected value>Category</option>
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col">
                                <input type="number" class="form-control" name="price" placeholder="Price" step="0.01" value="${price}">
                            </div>
                        </div>

                        <div class="form-row">

                            <div class="col">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-info active">
                                        <input type="radio" name="state" value="ACTIVE" autocomplete="off" checked>
                                        Active
                                    </label>
                                    <label class="btn btn-outline-info">
                                        <input type="radio" name="state" value="INACTIVE" autocomplete="off"> Inactive
                                    </label>
                                </div>
                            </div>

                            <div class="col">
                                <div class="form-group">
                                    <label>Item image</label>
                                    <input type="file" class="form-control-file" name="itemImage">
                                </div>
                            </div>
                        </div>
                        <div class="form-row ">
                            <div class="col justify-content-center">
                                <button type="submit" class="btn btn-block btn-outline-info">Create Item</button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <h1>NEW MENU ITEM CREATED</h1>
            </c:otherwise>
        </c:choose>


    </div>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
        integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
        crossorigin="anonymous"></script>

</body>
</html>
