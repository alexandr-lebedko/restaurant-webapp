<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.util.SupportedLocales" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<style>

</style>

<t:page pageUrl="${URL.ADMIN_CREATE_ITEM}">
    <div class="container main-content">
        <div class="row">
            <div class="col-lg-2 align-items-center">
                <img id="image-preview" class="rounded">
            </div>

            <div class="col-lg-10">
                <c:if test="${errors ne null}">
                    <div class="alert alert-warning text-center">
                        <c:forEach var="error" items="${errors.entrySet}">
                            <t:error errorName="${error.key}"/><br/>
                        </c:forEach>
                    </div>
                </c:if>
                <form method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <input type="file" class="form-control-sm form-control-file" id="image"
                                       name="${Attribute.IMAGE}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label for="uaTitle" class="font-weight-bold">Назва</label>
                                <input class="form-control form-control-sm" id="uaTitle"
                                       name="${Attribute.TITLE_UA}"
                                       aria-describedby="emailHelp" placeholder="Введiть назву"
                                       value="${item.title.value.get(SupportedLocales.UA_CODE)}">
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label for="enTitle" class="font-weight-bold">Title</label>
                                <input class="form-control form-control-sm" id="enTitle"
                                       name="${Attribute.TITLE_EN}"
                                       aria-describedby="emailHelp" placeholder="Enter title"
                                       value="${item.title.value.get(SupportedLocales.EN_CODE)}">
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label for="ruTitle" class="font-weight-bold">Название</label>
                                <input class="form-control form-control-sm" id="ruTitle"
                                       name="${Attribute.TITLE_RU}"
                                       aria-describedby="emailHelp" placeholder="Введите название"
                                       value="${item.title.value.get(SupportedLocales.RU_CODE)}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="font-weight-bold" for="uaDescription">Опис</label>
                                <textarea class="form-control form-control-sm" id="uaDescription" rows="5"
                                          placeholder="Введiть опис"
                                          name="${Attribute.DESCRIPTION_UA}">${item.description.value.get(SupportedLocales.UA_CODE)}</textarea>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="font-weight-bold" for="enDescription">Description</label>
                                <textarea class="form-control form-control-sm" id="enDescription" rows="5"
                                          placeholder="Enter description"
                                          name="${Attribute.DESCRIPTION_EN}">${item.description.value.get(SupportedLocales.EN_CODE)}</textarea>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="font-weight-bold" for="ruDescription">Описание</label>
                                <textarea class="form-control form-control-sm" id="ruDescription" rows="5"
                                          placeholder="Введите описание"
                                          name="${Attribute.DESCRIPTION_RU}">${item.description.value.get(SupportedLocales.RU_CODE)}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="font-weight-bold" for="price"><fmt:message key="price"/></label>
                                <input type="number" class="form-control form-control-sm" name="${Attribute.PRICE}"
                                       id="price"
                                       value="${item.price.value}"/>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="font-weight-bold" for="category"><fmt:message key="category"/></label>
                                <select class="form-control form-control-sm" id="category"
                                        name="${Attribute.CATEGORY_ID}">
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
                    <button class="btn float-right  btn btn-success mr-5 rounded-0" type="submit">
                        <fmt:message key="submit"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</t:page>