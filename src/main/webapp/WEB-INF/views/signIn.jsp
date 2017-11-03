<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="net.lebedko.web.util.constant.URL" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>


<t:page pageUrl="${URL.SIGN_IN}">

    <div class="container main-content" >

    <div class="row justify-content-between">

        <div id="image-area" class="col-md-6 col-lg-4">
            <img src="/restaurant/img/restaurant1600.png"/>
        </div>

        <div id="form" class="col-md-6 col-lg-4 col-sm-12">

            <form class="bg-light" method="post">

                <div class="form-group">
                    <label for="emailInput"><fmt:message key="page.signIn.form.email.label"/></label>
                    <input type="email"
                           class="form-control form-control-sm"
                           id="emailInput"
                           name="email"
                           aria-describedby="emailHelp"
                           placeholder="<fmt:message key="page.signIn.form.email.placeholder"/>"
                           value="${userView.emailAddress}">
                    <small class="form-text text-danger">
                        <t:error errorName="user not exists"/>
                    </small>
                </div>

                <div class="form-group">
                    <label for="inputPassword"><fmt:message key="page.signIn.form.password.label"/></label>
                    <input type="password"
                           class="form-control form-control-sm"
                           name="password"
                           id="inputPassword"
                           value="${userView.password.passwordString}"
                           placeholder="<fmt:message key="page.signIn.form.password.placeholder"/>">
                    <small class="form-text text-danger">
                        <t:error errorName="wrong password"/>
                    </small>
                </div>

                <button type="submit" class="btn btn-primary btn-block">
                    <fmt:message key="page.signIn.form.submit"/>
                </button>
            </form>
        </div>
    </div>

</t:page>
`