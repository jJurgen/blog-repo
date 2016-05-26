<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <div id="container">
            <%@include file="parts/header.jsp" %>
            <%@include file="parts/navigation.jsp" %>

            <div id="content">

                <form id="searchForm" action="doSearch" method="GET">
                    <div class="title">
                        <label>Search by:</label>
                    </div>
                    <div class="settings">
                        <div class="setting">
                            <label><input type="checkbox" name="byTitle" checked="checked">by title</label>
                        </div>
                        <div class="setting">
                            <label><input type="checkbox" name="byContent" checked="checked">by content</label>
                        </div>
                        <div class="setting">
                            <label><input type="checkbox" name="byAuthor">by author</label>
                        </div>
                        <div class="setting">
                            <label><input type="checkbox" name="byComments">by comments</label>
                        </div>
                    </div>
                    <div class="submit">
                        <input  type="text" name="searchReq" maxlength="150">
                        <button type="submit">Search</button>
                    </div>
                </form>

                <c:if test="${not empty result}" >
                    <div id="blogContainer">
                        <c:forEach var="post" items="${result}">							
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
                    </div>                            
                </c:if>    
            </div>

            <%@include file="parts/footer.jsp" %>
        </div>
    </body>
</html>
