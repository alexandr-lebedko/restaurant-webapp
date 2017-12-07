<%@tag description="Tag which contains paging logic" %>
<%@ tag import="net.lebedko.web.util.constant.Attribute" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="pagingUrl" type="java.lang.String" required="true" %>
<%@attribute name="params" type="java.util.Map" required="true" %>
<%@attribute name="data" type="net.lebedko.dao.paging.Page" required="true" %>

<nav class="float-right mt-2">
    <c:set var="totalPageNum" value="${data.numberOfPages}"/>
    <c:set var="currPageNum" value="${data.currentPage}"/>
    <c:set var="nextPageNum" value="${currPageNum+1}"/>

    <c:url var="prevPageUrl" value="${pagingUrl}">
        <c:forEach var="paramEntry" items="${params}">
            <c:if test="${paramEntry.key ne Attribute.PAGE_NUM}">
                <c:param name="${paramEntry.key}" value="${paramEntry.value}"/>
            </c:if>
        </c:forEach>
        <c:param name="${Attribute.PAGE_NUM}" value="${currPageNum-1}"/>
    </c:url>

    <c:url var="nextPageUrl" value="${URL.CLIENT_ORDERS}">
        <c:forEach var="paramEntry" items="${params}">
            <c:if test="${paramEntry.key ne Attribute.PAGE_NUM}">
                <c:param name="${paramEntry.key}" value="${paramEntry.value}"/>
            </c:if>
        </c:forEach>
        <c:param name="${Attribute.PAGE_NUM}" value="${currPageNum+1}"/>
    </c:url>

    <ul class="pagination pagination-sm justify-content-end">
        <li class="page-item ${currPageNum == 1 ? 'disabled':''}">
            <a class="page-link" href="${prevPageUrl}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${totalPageNum}" varStatus="loop">
            <li class="page-item ">
                <c:url var="pageUrl" value="${pagingUrl}">
                    <c:forEach var="paramEntry" items="${params}">
                        <c:if test="${paramEntry.key ne Attribute.PAGE_NUM}">
                            <c:param name="${paramEntry.key}" value="${paramEntry.value}"/>
                        </c:if>
                    </c:forEach>
                    <c:param name="${Attribute.PAGE_NUM}" value="${loop.index}"/>
                </c:url>
                <a class="page-link ${currPageNum == loop.index ? 'bg-light':''}"
                   href="${pageUrl}">${loop.index}</a>
            </li>
        </c:forEach>
        <li class="page-item ${totalPageNum == currPageNum ? 'disabled':''}">
            <a class="page-link" href="${nextPageUrl}">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

