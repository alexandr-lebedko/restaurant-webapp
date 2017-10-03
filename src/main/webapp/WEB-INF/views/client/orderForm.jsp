<%--
  alexandr.lebedko : 20.09.2017
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="lang"
       value="${not empty param.lang ? param.lang: not empty lang ? lang: pageContext.request.locale}"
       scope="session"/>


<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>

<c:set var="contextUri" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <title>MainPage</title>
</head>
<body>
<div class="container">

    <div class="row" style="width: 100%">
        <div class="col" style="width: 100%">
            <nav class="navbar navbar-dark justify-content-center" style="background-color: #e3f2fd;">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Active</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#">Disabled</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<div class="row justify-content-center">
    <c:if test="${orderContent ne null}">
        <div class="col-4">
            <form method="post">
                <c:forEach items="${orderContent}" var="entry">
                    <input type="hidden" name="itemId" value="${entry.key.id}">
                    <div class="form-group row">
                        <label class="col-8 col-form-label">${entry.key.title.value.get(lang)}</label>
                        <div class="col-4">
                            <input type="number" name="quantity" class="form-control-plaintext" value="${entry.value}">
                        </div>
                    </div>
                </c:forEach>
                <button type="submit" class="btn">Make Order</button>
            </form>
        </div>
    </c:if>
</div>


</div>


</form>

</div>

</body>
</html>
