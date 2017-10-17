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

            <c:url var="postUrl" value="${URL.CLIENT_MENU_ITEMS}"/>

            <div class="col-lg-9">
                <div class="row">

                    <c:forEach var="item" items="${items}">

                        <div class="col-lg-3">
                            <div class="card" onclick="post('${postUrl}', {'itemId': '${item.id}'})">

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
                            </div>
                        </div>

                    </c:forEach>

                </div>
            </div>

        </div>
    </div>

    <script src="${pageContext.request.contextPath.concat("/js/script.js")}"></script>
</t:page>