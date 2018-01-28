<%@tag description="General page template" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <script src='<c:url value="/js/jquery-3.2.1.slim.min.js"/>'></script>
    <script src='<c:url value="/js/popper.min.js"/>'></script>
    <link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>
    <script src='<c:url value="/js/bootstrap.min.js"/>'></script>
    <link href='<c:url value="/css/eho2.css"/>' rel="stylesheet"/>
    <link href='<c:url value="/css/font-awesome.min.css"/>' rel="stylesheet">
    <script src='<c:url value="/js/script.js"/>'></script>
    <link href='<c:url value="/css/style.css"/>' rel="stylesheet"/>
</head>

<body>
<t:header/>
<jsp:doBody/>
<t:footer/>
</body>
</html>