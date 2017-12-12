<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.util.SupportedLocales" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:set var="requestedCategory" value="${requestScope.get(Attribute.CATEGORY)}"/>

<t:page pageUrl="${URL.ADMIN_ITEMS.concat('?').concat(Attribute.CATEGORY_ID).concat('=').concat(requestedCategory.id)}">
    <div class="container main-content">
        <div class="row justify-content-between">

            <div class="col-lg-3">
                <ul class="nav nav-pills flex-column bg-light">
                    <c:forEach var="category" items="${requestScope.get(Attribute.CATEGORIES)}">
                        <li class="nav-item">
                            <c:url var="categoryUrl" value="${URL.ADMIN_ITEMS}">
                                <c:param name="${Attribute.CATEGORY_ID}" value="${category.id}"/>
                            </c:url>
                            <a class="nav-link" id="${requestedCategory.equals(category) ? 'current-category':''}"
                               href="${categoryUrl}">${category.title.get(lang)}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <div class="col-lg-8 unselectable">
                <c:url var="newItem" value="${URL.ADMIN_CREATE_ITEM}"/>
                <a class="float-right mb-3 btn pl-4 pr-4 rounded-0 btn-outline-success" href="${newItem}"><fmt:message
                        key="create"/></a>
                <table class="table border" id="admin-items-table">
                    <tbody>
                    <c:forEach var="item" items="${requestScope.get(Attribute.ITEMS)}">
                        <c:set var="imageId" value="${item.imageId}"/>
                        <c:url var="imageUrl" value="${URL.IMAGE_PREFIX.concat(imageId)}"/>
                        <c:set var="id" value="${item.id}"/>
                        <c:set var="categoryId" value="${item.category.id}"/>
                        <c:set var="ruTitle" value="${item.title.value.get(SupportedLocales.RU_CODE)}"/>
                        <c:set var="uaTitle" value="${item.title.value.get(SupportedLocales.UA_CODE)}"/>
                        <c:set var="enTitle" value="${item.title.value.get(SupportedLocales.EN_CODE)}"/>
                        <c:set var="ruDescription" value="${item.description.value.get(SupportedLocales.RU_CODE)}"/>
                        <c:set var="enDescription" value="${item.description.value.get(SupportedLocales.EN_CODE)}"/>
                        <c:set var="uaDescription" value="${item.description.value.get(SupportedLocales.UA_CODE)}"/>
                        <c:set var="price" value="${item.price.value}"/>
                        <tr>
                            <td><img class="item-image" data-toggle="modal" data-target="#image-modal" data-id="${id}"
                                     src="${imageUrl}"></td>
                            <td colspan="4" data-toggle="modal" data-target="#item-modal" data-id="${id}"
                                data-title-ua="${uaTitle}" data-title-en="${enTitle}" data-title-ru="${ruTitle}"
                                data-description-ua="${uaDescription}" data-description-en="${enDescription}"
                                data-description-ru="${ruDescription}" data-category="${categoryId}"
                                data-price="${price}" data-image="${imageId}">

                                <table class="table border-bottom-0">
                                    <tbody>
                                    <tr>
                                        <th><fmt:message key="title"/></th>
                                        <td>${uaTitle}</td>
                                        <td>${enTitle}</td>
                                        <td>${ruTitle}</td>
                                    </tr>
                                    <tr>
                                        <th><fmt:message key="description"/></th>
                                        <td>${uaDescription}</td>
                                        <td>${enDescription}</td>
                                        <td>${ruDescription}</td>
                                    </tr>
                                    <tr>
                                        <th><fmt:message key="price"/></th>
                                        <td colspan="3">${price}</td>
                                    </tr>
                                    </tbody>
                                </table>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal" tabindex="-1" id="image-modal" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title text-warning"><fmt:message key="image.modify"/></h5>
                    <i class="fa fa-times" role="button" aria-hidden="true" data-dismiss="modal"></i>
                </div>
                <div class="modal-body">
                    <div class="row justify-content-center mb-3">
                        <img id="image-preview"/>
                    </div>
                    <div class="row justify-content-center">
                        <c:url var="updateImage" value="${URL.ADMIN_MODIFY_ITEM_IMAGE}"/>
                        <form method="post" action="${updateImage}" id="updateImage" enctype="multipart/form-data">
                            <input name="${Attribute.ITEM_ID}" type="hidden" id="updateImageItemId"/>
                            <input name="${Attribute.IMAGE_ID}" type="file" id="image" class="form-control-file"
                                   required>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-warning rounded-0" form="updateImage"><fmt:message
                            key="submit"/></button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal" id="item-modal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title  text text-warning">
                        <fmt:message key="item.modify"/>
                    </h5>
                    <i class="fa fa-times" role="button" aria-hidden="true" data-dismiss="modal"></i>
                </div>
                <div class="modal-body">
                    <c:if test="${requestScope.get(Attribute.MODIFIED_ITEM) ne null}">
                        <div class="alert alert-warning" id="item-alert">
                            <c:forEach var="error" items="${errors.entrySet}">
                                <t:error errorName="${error.key}"/>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:set var="item" value="${requestScope.get(Attribute.MODIFIED_ITEM)}"/>
                    <c:url var="updateItem" value="${URL.ADMIN_MODIFY_ITEM}"/>
                    <form method="post" action="${updateItem}" id="updateForm">
                        <input type="hidden" id="itemId" name="${Attribute.ITEM_ID}" value="${item.id}">
                        <input type="hidden" id="imageId" name="${Attribute.IMAGE_ID}" value="${item.imageId}">
                        <div class="row">
                            <div class="col">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">Назва</span>
                                </label>
                                <input name="${Attribute.TITLE_UA}" class="form-control uaTitle"
                                       value="${item.title.value.get(SupportedLocales.RU_CODE)}"/>
                            </div>
                            <div class="col">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">Title</span>
                                </label>
                                <input name="${Attribute.TITLE_EN}" class="form-control enTitle"
                                       value="${item.title.value.get(SupportedLocales.EN_CODE)}"/>
                            </div>
                            <div class="col">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">Название</span>
                                </label>
                                <input name="${Attribute.TITLE_RU}" class="form-control ruTitle"
                                       value="${item.title.value.get(SupportedLocales.RU_CODE)}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">Опис</span>
                                </label>
                                <textarea name="${Attribute.DESCRIPTION_UA}" rows="6"
                                          class="form-control uaDescription">${item.description.value.get(SupportedLocales.UA_CODE)}</textarea>
                            </div>
                            <div class="col">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">Description</span>
                                </label>
                                <textarea name="${Attribute.DESCRIPTION_EN}" rows="6"
                                          class="form-control enDescription">${item.description.value.get(SupportedLocales.EN_CODE)}</textarea>
                            </div>
                            <div class="col">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">Описание</span>
                                </label>
                                <textarea name="${Attribute.DESCRIPTION_RU}" rows="6"
                                          class="form-control ruDescription">${item.description.value.get(SupportedLocales.RU_CODE)}</textarea>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label class="col-form-label">
                                        <span class="text-muted font-weight-bold"><fmt:message key="price"/></span>
                                    </label>
                                    <input name="${Attribute.PRICE}" id="price" type="number" min="0"
                                           class="form-control" value="${item.price.value}">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label class="col-form-label">
                                            <span class="text-muted font-weight-bold">
                                                <fmt:message key="category"/>
                                            </span>
                                    </label>
                                    <select class="form-control" name="${Attribute.CATEGORY_ID}">
                                        <c:forEach var="category"
                                                   items="${requestScope.get(Attribute.CATEGORIES)}">
                                            <option id="${category.id}" value="${category.id}"
                                                ${categoryId eq category.id ? 'selected':''}>${category.title.get(lang)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer justify-content-between">
                    <form>
                        <button class="btn btn-danger rounded-0 float-left">
                            <fmt:message key="delete"/>
                        </button>
                    </form>
                    <button type="submit" class="btn btn-warning rounded-0" form="updateForm">
                        <fmt:message key="submit"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${requestScope.get(Attribute.MODIFIED_ITEM) ne null}">
        <script>
            $('#item-modal').modal('show');
        </script>
    </c:if>
</t:page>