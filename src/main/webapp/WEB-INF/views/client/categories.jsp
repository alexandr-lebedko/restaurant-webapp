<%--
  alexandr.lebedko : 14.09.2017
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

<c:set var="contextUri" value="${pageContext.request.contextPath}"/>
<c:set var="imagesUri" value="${contextUri.concat('/images/')}"/>
<c:set var="dishesUrl" value="${contextUri.concat('/app/client/items?category=')}"/>


<!DOCTYPE html>
<html>
<head>
    <style>

        #categories .card img {
            height: 12rem;
        }

        #categories .card {
            margin-bottom: 30px;
        }

        #categories a:hover, #categories a:focus {
            text-decoration: none;
            border: solid 1px #5e5e5e;
        }

    </style>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <title>MainPage</title>
</head>
<body>
<div class="container">

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

    <div class="row justify-content-center" id="categories" style="margin-top:50px">
        <div class="card-group">
            <c:forEach var="category" items="${categories}">
                <div class="col col-lg-3 col-md-4 col-sm-6 col-xs-6">
                    <div class="card">
                        <a href="${dishesUrl.concat(category.id)}">
                            <img class="card-img-top" src="${imagesUri.concat(category.imageId)}"/>
                            <div class="card-body text-center">
                                <h6 class="card-title">${category.name}</h6>
                            </div>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</div>
</div>


</body>
</html>
