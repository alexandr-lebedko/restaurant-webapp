<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:choose>
    <c:when test="${param.get(Attribute.CATEGORY_ID) ne null}">
        <c:set var="pageUrl"
               value="${URL.CLIENT_MENU.concat('?').concat(Attribute.CATEGORY_ID).concat('=').concat(param.get(Attribute.CATEGORY_ID))}"/>
    </c:when>
    <c:otherwise>
        <c:set var="pageUrl" value="${URL.CLIENT_MENU}"/>
    </c:otherwise>
</c:choose>

<c:set var="categories" value="${requestScope.get(Attribute.CATEGORIES)}"/>
<c:set var="items" value="${requestScope.get(Attribute.ITEMS)}"/>
<c:if test="${!empty items}">
    <c:set var="requestedCategory" value="${items.get(0).category}"/>
</c:if>

<t:page pageUrl="${pageUrl}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div class="col-lg-3">
                <ul class="nav nav-pills flex-column bg-light">
                    <c:forEach var="category" items="${requestScope.get(Attribute.CATEGORIES)}">
                        <li class="nav-item">
                            <c:url var="categoryUrl" value="${URL.CLIENT_MENU}">
                                <c:param name="${Attribute.CATEGORY_ID}" value="${category.id}"/>
                            </c:url>
                            <a class="nav-link" id="${requestedCategory.equals(category) ? 'current-category':''}"
                               href="${categoryUrl}">
                                    ${category.title.get(lang)}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-lg-9" id="items">
                <div class="row">
                    <c:forEach var="item" items="${requestScope.get(Attribute.ITEMS)}">
                        <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(item.imageId)}"/>
                        <c:url var="addToBucketUrl" value="${URL.CLIENT_ITEM_ADD}"/>
                        <div class="col-lg-3">
                            <div class="card item-card" data-url="${addToBucketUrl}"
                                 data-attr="${Attribute.ITEM_ID}" data-value="${item.id}">
                                <img class="card-img-top" src="${imageUrl}">
                                <div class="order-text-block text-center">
                                    <fmt:message key="page.items.order"/>
                                </div>
                                <div class="card-body">
                                    <h6 class="card-title">${item.title.value.get(lang)}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">
                                        <fmt:message key="price"/>${item.price.value}
                                    </h6>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
</t:page>