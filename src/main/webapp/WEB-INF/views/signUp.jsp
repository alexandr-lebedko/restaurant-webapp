<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="net.lebedko.web.util.constant.URL" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>


<t:page pageUrl="${URL.SIGN_UP}">
    <div class="container main-content" id="sign-up-page">
        <div class="row justify-content-around">

            <div id="image-area" class="col-sm-12 col-md-6 col-lg-4">
                <img class="" src="/restaurant/img/restaurant_pickup1600.png">
            </div>

            <div class="col-sm-12 col-md-6 col-lg-4" id="sign-up-form">

                <form method="post" class="bg-light">

                    <div class="form-group">

                        <label for="firstName"><fmt:message key="page.signUp.form.firstName.label"/></label>
                        <input name="firstName"
                               type="text"
                               class="form-control"
                               id="firstName"
                               placeholder="<fmt:message key="page.signUp.form.firstName.placeholder"/>"
                               value="${user.fullName.firstName}">
                        <small class="form-text text-danger">
                            <t:error errorName="firstName"/>
                        </small>

                    </div>

                    <div class="form-group">

                        <label for="lastName"><fmt:message key="page.signUp.form.lastName.label"/></label>

                        <input name="lastName" type="text"
                               class="form-control"
                               id="lastName"
                               placeholder="<fmt:message key="page.signUp.form.lastName.placeholder"/>"
                               value="${user.fullName.lastName}">

                        <small class="form-text text-danger">
                            <t:error errorName="lastName"/>
                        </small>

                    </div>

                    <div class="form-group">

                        <label for="email"><fmt:message key="page.signUp.form.email.label"/></label>
                        <input name="email"
                               type="email"
                               class="form-control"
                               id="email"
                               placeholder="<fmt:message key="page.signUp.form.email.placeholder"/>"
                               value="${user.email}">

                        <small class="form-text text-danger">
                            <t:error errorName="email"/>
                            <t:error errorName="user exists"/>
                        </small>

                    </div>

                    <div class="form-group">

                        <label><fmt:message key="page.signUp.form.password.label"/></label>
                        <input name="password"
                               type="password"
                               class="form-control"
                               placeholder="<fmt:message key="page.signUp.form.password.placeholder"/>"
                               value="${user.password.passwordString}"/>

                        <small class="form-text text-danger">
                            <t:error errorName="password"/>
                        </small>
                    </div>

                    <button type="submit" class="btn btn-primary btn-block">
                        <fmt:message key="page.signUp.form.submit"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</t:page>