<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>
<%@ page import="net.lebedko.web.util.constant.Attribute" %>
<%@ page import="net.lebedko.i18n.SupportedLocales" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.ADMIN_CATEGORIES}">
    <div class="container main-content">
        <div class="row justify-content-end table-hover">
            <div class="col-lg-8 unselectable">
                <button data-toggle="modal" data-target="#newCategoryModal"
                        class="btn float-right btn-outline-info rounded-0 pl-4 pr-4 mb-3 mr-3">
                    <fmt:message key="create"/>
                </button>
                <table class="table table-text-center table-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="id"/></th>
                        <th>Укр</th>
                        <th>Eng</th>
                        <th>Руc</th>
                    </tr>
                    </thead>
                    <tbody class="table-sm ">
                    <c:forEach var="category" items="${requestScope.get(Attribute.CATEGORIES)}">
                        <tr data-toggle="modal"
                            data-target="#categoryModal"
                            data-id="${category.id}"
                            data-ua="${category.title.get(SupportedLocales.UA_CODE)}"
                            data-en="${category.title.get(SupportedLocales.EN_CODE)}"
                            data-ru="${category.title.get(SupportedLocales.RU_CODE)}">
                            <td>${category.id}</td>
                            <td>${category.title.get(SupportedLocales.UA_CODE)}</td>
                            <td>${category.title.get(SupportedLocales.EN_CODE)}</td>
                            <td>${category.title.get(SupportedLocales.RU_CODE)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="modal category-modal" id="categoryModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-light">
                        <h5 class="modal-title  text text-warning ">
                            <fmt:message key="modify.category"/>
                        </h5>
                        <i class="fa fa-times" role="button" aria-hidden="true" data-dismiss="modal"
                           aria-label="Close"></i>
                    </div>

                    <div class="modal-body">
                        <c:if test="${requestScope.get(Attribute.MODIFIED_CATEGORY) ne null}">
                            <div class="alert alert-warning" id="category-alert">
                                <c:forEach var="error" items="${errors.entrySet}">
                                    <t:error errorName="${error.key}"/>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:set var="category" value="${requestScope.get(Attribute.MODIFIED_CATEGORY)}"/>
                        <c:url value="${URL.ADMIN_MODIFY_CATEGORY}" var="modifyCategory"/>
                        <form method="post" action="${modifyCategory}" id="modifyCategory">
                            <input type="hidden"
                                   id="categoryId"
                                   name="${Attribute.CATEGORY_ID}"
                                   value="${category.id}">
                            <div class="form-group">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">УКР</span>
                                </label>
                                <input type="text"
                                       class="form-control"
                                       id="uaTitle"
                                       name="${Attribute.TITLE_UA}"
                                       value="${category.title.get(SupportedLocales.UA_CODE)}">
                            </div>
                            <div class="form-group">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">ENG</span>
                                </label>
                                <input type="text"
                                       class="form-control"
                                       id="enTitle"
                                       name="${Attribute.TITLE_EN}"
                                       value="${category.title.get(SupportedLocales.EN_CODE)}">
                            </div>
                            <div class="form-group">
                                <label class="col-form-label">
                                    <span class="text-muted font-weight-bold">РУС</span>
                                </label>
                                <input type="text"
                                       id="ruTitle"
                                       class="form-control"
                                       name="${Attribute.TITLE_RU}"
                                       value="${category.title.get(SupportedLocales.RU_CODE)}">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer justify-content-between">
                        <c:url var="deleteUrl" value="${URL.ADMIN_DELETE_CATEGORY}"/>
                        <form action="${deleteUrl}" method="post">
                            <input type="hidden" id="deleteId" name="${Attribute.CATEGORY_ID}"/>
                            <button class="btn btn-danger rounded-0 float-left">
                                <fmt:message key="delete"/>
                            </button>
                        </form>
                        <button type="submit" class="btn  btn-warning rounded-0" form="modifyCategory">
                            <fmt:message key="modify"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal category-modal" id="newCategoryModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-light">
                        <h5 class="modal-title  text text-info">
                            <fmt:message key="category.new"/>
                        </h5>
                        <i class="fa fa-times" role="button" aria-hidden="true" data-dismiss="modal"></i>
                    </div>
                    <div class="modal-body">
                        <c:if test="${requestScope.get(Attribute.NEW_CATEGORY) ne null}">
                            <c:set var="category" value="${requestScope.get(Attribute.NEW_CATEGORY)}"/>
                            <div class="alert alert-warning" id="category-alert">
                                <c:forEach var="error" items="${errors.entrySet}">
                                    <t:error errorName="${error.key}"/>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:url var="newCategoryUrl" value="${URL.ADMIN_CREATE_CATEGORY}"/>
                        <form action="${newCategoryUrl}" method="post" id="newCategotyForm">
                            <div class="form-group">
                                <label for="uaTitle" class="col-form-label">
                                    <span class="text-muted font-weight-bold">УКР</span>
                                </label>
                                <input type="text"
                                       class="form-control uaTitle"
                                       name="${Attribute.TITLE_UA}"
                                       value="${category.title.get(SupportedLocales.UA_CODE)}">
                            </div>
                            <div class="form-group">
                                <label for="enTitle" class="col-form-label">
                                    <span class="text-muted font-weight-bold">ENG</span>
                                </label>
                                <input type="text"
                                       class="form-control enTitle"
                                       name="${Attribute.TITLE_EN}"
                                       value="${category.title.get(SupportedLocales.EN_CODE)}">
                            </div>
                            <div class="form-group">
                                <label for="ruTitle" class="col-form-label">
                                    <span class="text-muted font-weight-bold">РУС</span>
                                </label>
                                <input type="text"
                                       class="form-control ruTitle"
                                       name="${Attribute.TITLE_RU}"
                                       value="${category.title.get(SupportedLocales.RU_CODE)}">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" form="newCategotyForm" class="btn btn-info rounded-0">
                            <fmt:message key="submit"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${requestScope.get(Attribute.MODIFIED_CATEGORY) ne null}">
        <script>
            $('#categoryModal').modal('show');
        </script>
    </c:if>
    <c:if test="${requestScope.get(Attribute.NEW_CATEGORY) ne null}">
        <script>
            $('#newCategoryModal').modal('show');
        </script>
    </c:if>
</t:page>