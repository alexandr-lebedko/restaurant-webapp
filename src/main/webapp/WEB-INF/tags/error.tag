<%@tag description="Error tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="errorName" required="true" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization"/>

<c:if test="${not empty errors.get(errorName)}">
    <fmt:message key="${errors.get(errorName)}" />
</c:if>



