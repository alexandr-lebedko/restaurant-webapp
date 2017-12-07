<%@tag description="Main header tag, to be used in every page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/header" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag import="net.lebedko.entity.user.UserRole" %>
<%@tag import="net.lebedko.web.util.constant.Attribute" %>

<%@attribute name="pageUrl" type="java.lang.String" required="true" %>
<c:set var="role" value="${sessionScope.get(Attribute.USER).role}"/>
<c:choose>
    <c:when test="${UserRole.ADMIN eq role}">
        <t:adminHeader pageUrl="${pageUrl}"/>
    </c:when>
    <c:when test="${UserRole.CLIENT eq role}">
        <t:clientHeader pageUrl="${pageUrl}"/>
    </c:when>
    <c:when test="${role eq null}">
        <t:guestHeader pageUrl="${pageUrl}"/>
    </c:when>
</c:choose>