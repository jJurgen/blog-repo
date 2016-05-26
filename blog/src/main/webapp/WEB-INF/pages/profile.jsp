<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
    <head>
        <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <div id="container">
            <%@include file="parts/header.jsp" %>
            <%@include file="parts/navigation.jsp" %>

            <div id="content">

                <c:choose>
                    <c:when test="${not empty user}">						
                        <div id="userInfo">

                            <img src="resources/img/defaultAvatar.png">

                            <div class="field">
                                <p>nickname: <c:out value="${user.username}"/></p>
                            </div>

                            <div class="field">
                                <p>registration date: <c:out value="${user.regDate}"/></p>
                            </div>

                            <div class="field">
                                <p>account type:
                                    <c:forEach var="role" items="${user.roles}" >
                                        <c:set var="strRole" value="${role.role}" />
                                        <c:out value="${fn:substringAfter(role.role, 'ROLE_')} " />                                        
                                    </c:forEach>
                                </p>
                            </div>

                            <div class="field">
                                <p>comments:<c:out value=" ${user.comments.size()}"/></p>
                            </div>

                            <div class="field">
                                <p>posts:<c:out value=" ${user.posts.size()}"/></p>
                            </div>

                            <div class="field">
                                <p>something about: <c:out value="${user.about}"/></p>
                            </div>

                            <c:if test="${isCurrentUser}">
                                <button onclick="location.href = 'editProfile'">Edit profile</button>				
                            </c:if>

                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <form action="removeProfile" method="POST">
                                    <input type="hidden" name = "userId" value="<c:out value="${user.id}"/>">
                                    <button type="submit">Remove user</button>
                                </form>	
                            </sec:authorize>
                        </div>  

                        <c:if test="${not empty user.posts}" >
                            <div id="blogContainer">
                                <c:forEach var="post" items="${user.posts}">							
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
                                            <c:if test="${isCurrentUser}">
                                                <form action="editPost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                    <button type="submit">edit</button>
                                                </form>

                                                <form action="removePost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                    <button type="submit">remove</button>
                                                </form> 
                                            </c:if>

                                            <sec:authorize access="hasRole('ROLE_MODER')">
                                                <form action="removePost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                    <button type="submit">remove</button>
                                                </form> 
                                            </sec:authorize>

                                            <div class="date">
                                                <label><c:out value="${post.postDate}"/></label>
                                            </div>
                                        </div>
                                    </div>																										
                                </c:forEach>
                            </div>                            
                        </c:if>    
                    </c:when>
                    <c:otherwise>        
                        <div id="userInfo">
                            <div class="field">
                                <p><c:out value="Unknown user..."/></p>
                            </div>          
                        </div>
                    </c:otherwise>
                </c:choose>                        

            </div>

            <%@include file="parts/footer.jsp" %>
        </div>
    </body>
</html>
