<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <div id="container">
            <%@include file="parts/header.jsp" %>
            <%@include file="parts/navigation.jsp" %>

            <div id="content">
                <div id="blogContainer">				
                    <div class="content_title">
                        <c:out value="Recent posts"/>
                    </div>

                    <div class="paginationBlock">
                        <c:if test="${currentPageNumber > 1}">
                            <a href="<c:url value="/page${currentPageNumber-1}" />"><c:out value="<<Prev"/></a>
                        </c:if>

                        <c:if test="${currentPageNumber != 1}">
                            <a href="<c:url value="/page1" />">1</a>
                        </c:if>

                        <c:if test="${currentPageNumber > 4}">
                            <b>...</b>
                        </c:if>   

                        <c:if test="${currentPageNumber > 3}">
                            <a href="<c:url value="/page${currentPageNumber-2}" />">${currentPageNumber-2}</a>
                        </c:if>    

                        <c:if test="${currentPageNumber > 2}">
                            <a href="<c:url value="/page${currentPageNumber-1}" />">${currentPageNumber-1}</a>
                        </c:if>

                        <b><a>${currentPageNumber}</a></b>

                        <c:if test="${currentPageNumber < lastPageNumber-1}">
                            <a href="<c:url value="/page${currentPageNumber+1}" />">${currentPageNumber+1}</a>
                        </c:if>

                        <c:if test="${currentPageNumber < lastPageNumber-2}">
                            <a href="<c:url value="/page${currentPageNumber+2}" />">${currentPageNumber+2}</a>
                        </c:if>

                        <c:if test="${currentPageNumber < lastPageNumber-3}">
                            <b>...</b>
                        </c:if>

                        <c:if test="${currentPageNumber != lastPageNumber}">
                            <a href="<c:url value="/page${lastPageNumber}" />">${lastPageNumber}</a>
                        </c:if>

                        <c:if test="${currentPageNumber < lastPageNumber}">
                            <a href="<c:url value="/page${currentPageNumber+1}" />"><c:out value="Next>>"/></a>
                        </c:if>
                    </div>

                    <c:if test="${not empty posts}">	
                        <c:forEach var="post" items="${posts}">							
                            <div class="postPreview">								
                                <div class="title">
                                    <a href="<c:url value="/post${post.id}"/>"><c:out value="${post.title}"/></a>
                                </div>
                                <div class="content">
                                    <c:choose>
                                        <c:when test="${fn:length(post.content) > 200}">
                                            <c:set var="previewContent" value="${fn:substring(post.content,0, 200).concat('...')}"/>
                                            <c:out value="${previewContent}" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${post.content}" />
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                                <div class="meta">
                                    <label>Author: </label>
                                    <a href="<c:url value="profile=${post.author.username}" />"><c:out value="${post.author.username}"/></a>                                        
                                    <div class="date">
                                        <label><c:out value="${post.postDate}"/></label>
                                    </div>
                                </div>
                            </div>																										
                        </c:forEach>
                    </c:if>
                </div>

            </div>


            <%@include file="parts/footer.jsp" %>
        </div>

    </body>
</html>
