<%@tag description="Main header tag, to be used in every page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag import="net.lebedko.entity.user.User.UserRole" %>

<%@attribute name="pageUrl" type="java.lang.String" required="true" %>

<c:choose>

    <c:when test="${UserRole.ADMIN.equals(role)}">


    </c:when>

    <c:when test="${UserRole.CLIENT.equals(role)}">

        <t:clientHeader pageUrl="${pageUrl}"/>

    </c:when>

    <c:otherwise>

        <t:guestHeader pageUrl="${pageUrl}"/>

    </c:otherwise>
</c:choose>