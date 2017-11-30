<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="">
    <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
    <div class="container main-content">
        <div class="row bg-light p-5 ${code == 404 ? 'text-warning':'text-danger'} align-items-center">
            <div class="col">
                <i class="fa fa-exclamation-triangle fa-5x align-bottom pl-5"></i>
            </div>
            <div class="col">
                <h1 class="fa-4x">${code}</h1>
                <h4><fmt:message key="${code}"/></h4>
            </div>
        </div>
</t:page>