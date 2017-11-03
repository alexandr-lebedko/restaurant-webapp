<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="net.lebedko.web.util.constant.URL" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<t:page pageUrl="${URL.ADMIN_MAIN}">
    <div class="container main-content">
        <h2>ADMIN MAIN PAGE</h2>


    </div>
</t:page>