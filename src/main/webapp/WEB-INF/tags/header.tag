<%@tag description="Main header tag, to be used in every page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag import="net.lebedko.entity.user.UserRole"%>

<%@attribute name="pageUrl" type="java.lang.String" required="true" %>
<c:set var ="role" value="${user.role}"/>
<c:choose>
    <c:when test="${UserRole.ADMIN eq role}">


    </c:when>

    <c:when test="${UserRole.CLIENT eq role}">

        <t:clientHeader pageUrl="${pageUrl}"/>

    </c:when>

    <c:when test="${role eq null}">

        <t:guestHeader pageUrl="${pageUrl}"/>

    </c:when>
</c:choose>