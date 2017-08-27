<%--
  alexandr.lebedko : 29.06.2017
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="a" uri="localhost:8080/restaurant/customTagLib" %>
<c:set var="lang"
       value="${not empty param.lang ? param.lang: lang ne null? lang: pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:set var="pageUri" value="app/admin/menu" scope="page"/>
<c:set var="imagesUri" value="${pageContext.request.contextPath}/images/"/>

<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>
    <title><fmt:message key="page.admin.menu.title"/></title>


    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>

    <style>
        #admin-middle-section {
            min-height: 750px !important;
        }
    </style>

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
                    <h2><fmt:message key="page.admin.menu"/></h2>
                </div>
            </div>
            <div class=" col-md-2 col-md-offset-4" id="button-area">

                <div class="button">
                    <a href="app/admin/menu/category/new" class="btn"><fmt:message
                            key="page.admin.label.newCategory"/></a>
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
                        <li><a href="${pageUri}?lang=ru_RU">Русский</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>

<section class="middle-section">
    <div class="container-fluid" id="admin-middle-section">
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
                        <a href="app/admin/menu" class="side-bar-item">
                            <i class="glyphicon glyphicon-th-list"></i>
                            <fmt:message key="page.admin.label.menu"/>
                        </a>
                    </li>

                    <li role="presentation">
                        <a href="#" class="side-bar-item">
                            <i class="glyphicon glyphicon-off"></i>
                            <fmt:message key="page.admin.label.logout"/>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="col-md-10 page-content" id="admin-menu">

                <c:set value="${fn:length(categories)}" var="numberOfCategories"/>
                <c:set value="3" var="elementsPerRow"/>
                <c:set value="${a:defineNumberOfRows(numberOfCategories, elementsPerRow)}" var="numberOfRows"/>
                <c:set value="0" var="currentElement"/>

                <%--<c:out value="Number of categories: ${numberOfCategories}"/><br/>--%>
                <%--<c:out value="Elements per row: ${elementsPerRow}"/><br/>--%>
                <%--<c:out value="Number of rows: ${numberOfRows}"/><br/>--%>

                <c:forEach var="currentRow" begin="1" end="${numberOfRows}" step="${currentRow = currentRow+1}">
                    <div class="row">


                        <c:set value="${a:defineRowLimit(numberOfCategories, elementsPerRow,currentRow ) - 1}"
                               var="endIndex"/>


                        <c:forEach var="category"
                                   begin="${currentElement}"
                                   end="${endIndex}"
                                   items="${categories}">

                            <div class="col-md-4">
                                <div class="panel panel-default category">
                                    <div class="panel-heading">
                                        <a href="#" class="thumbnail">
                                            <img src="images/${category.imageId}">
                                        </a>
                                    </div>
                                    <div class="panel-body ">
                                        <a class="category-name">${category.name}</a>
                                        <a href=""><span class="glyphicon glyphicon-wrench"></span></a>
                                    </div>
                                </div>


                            </div>

                            <c:set var="currentElement" value="${currentElement = currentElement+1}"/>
                        </c:forEach>

                    </div>
                </c:forEach>

            </div>
        </div>

    </div>
</section>

<footer style="background-color: rgba(19, 25, 53, 0.95); height: 15px">
</footer>


<%--<footer style="background-color: rebeccapurple">--%>
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
<script src="js/script.js"></script>

</body>
</html>
