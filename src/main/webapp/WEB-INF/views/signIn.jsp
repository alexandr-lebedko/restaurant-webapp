<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="net.lebedko.web.util.constant.URL" %>
<%@page import="net.lebedko.web.util.constant.Attribute" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.SIGN_IN}">
    <div class="container main-content">
        <div class="row justify-content-between">
            <div id="image-area" class="col-lg-4">
                <img src="<c:url value="${URL.SIGN_IN_IMG}"/>"/>
            </div>
            <div id="form" class="col-lg-4">
                <form class="bg-light" method="post">
                    <div class="form-group">
                        <label for="emailInput">
                            <fmt:message key="email"/>
                        </label>
                        <input name="${Attribute.EMAIL}"
                               type="email"
                               class="form-control form-control-sm"
                               id="emailInput"
                               aria-describedby="emailHelp"
                               placeholder="<fmt:message key="email.placeholder"/>"
                               value="${email}">
                        <small class="form-text text-danger">
                            <t:error errorName="user not exists"/>
                        </small>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">
                            <fmt:message key="password"/>
                        </label>
                        <input name="${Attribute.PASSWORD}"
                               type="password"
                               class="form-control form-control-sm"
                               id="inputPassword"
                               value="${password.passwordString}"
                               placeholder="<fmt:message key="password.placeholder"/>">
                        <small class="form-text text-danger">
                            <t:error errorName="wrong password"/>
                        </small>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">
                        <fmt:message key="signIn"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</t:page>
