<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<c:set var="contextUrl" value="${pageContext.request.contextPath}"/>
<c:set var="itemsUrl" value="${contextUrl.concat(URL.CLIENT_MENU_ITEMS).concat('?category=')}"/>
<c:set var="imagesUrl" value="${contextUrl.concat(URL.IMAGE_PREFIX)}"/>

<t:page pageUrl="${URL.CLIENT_CATEGORIES}">

    <div class="container main-content" id="categories">
        <div class="col-xl-10 col-lg-9  col-md-11 col-sm-11 ml-auto">
            <div class="row">

                <c:forEach var="category" items="${categories}">
                    <div class="col-lg-4 col-md-4 col-sm-6 col-6">
                        <div class="card text-center">
                            <a href="${itemsUrl.concat(category.id)}">
                                <c:url var ="imageUrl" value="${URL.IMAGE_PREFIX.concat(category.imageId)}"/>
                                <img class="card-img-top" src="${imageUrl}"/>

                                <div class="card-body">
                                    <h5 class="card-title">${category.name}</h5>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</t:page>

