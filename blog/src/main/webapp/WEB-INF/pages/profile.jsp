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
            <div id="header">
                <div id="headerNavigationPanel">
                    <sec:authorize access="isAuthenticated()">
                        <sec:authentication property="principal.username" var="username"/>
                        <label>
                            <c:out value="Hello, ${username}"/>
                            <a href="<c:url value="/logout"/>">(sign out)</a>
                        </label>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <label>
                            <c:out value="Hello! You aren't authorized." />
                        </label>
                        <label>
                            <c:out value="Would you like to" />
                            <a href="<c:url value="/signIn"/>"><c:out value="sign in"/></a>
                            <c:out value="?" />
                        </label>
                        <label>
                            <c:out value="If you haven't got an account - " />
                            <a href="<c:url value="/signUp"/>"><c:out value="sign up"/></a>
                            <c:out value="!" />
                        </label>
                    </sec:authorize>    
                </div>
                <a href="<c:url value="/home"/>">
                    <div id="headerBannerPanel">
                    </div>
                </a>
            </div>

            <div id="navigation">
                <form action="<c:url value="/home"/>">
                    <button type="submit">Home</button>
                </form>
                <form action="<c:url value="/writepost"/>">
                    <button type="submit">Write Post</button>
                </form>
                <form action="<c:url value="/profile"/>">
                    <button type="submit">Profile</button>
                </form>
                <form action="<c:url value="/search"/>">
                    <button type="submit">Search</button>
                </form>
                <form action="signUp">
                    <button type="submit">Sign Up</button>
                </form>
            </div>

            <div id="content">
                <div id="blogContainer">				
                    <div id="userInfo">
                        <c:choose>
                            <c:when test="${not empty user}">						
                                <div class="field">
                                    <label>nickname: <c:out value="${user.username}"/></label>
                                </div>
                                <div class="field">
                                    <label>registration date: <c:out value="${user.regDate}"/></label>
                                </div>

                                <div class="field">
                                    <label>account type:</label>
                                    <c:forEach var="role" items="${user.roles}" >
                                        <c:set var="strRole" value="${role.role}" />
                                        <c:out value="${fn:substringAfter(role.role, 'ROLE_')}, " />                                        
                                    </c:forEach>
                                </div>
                                <div class="field">
                                    <label>something about: <c:out value="${user.about}"/></label>
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
                                <c:if test="${not empty user.posts}">
                                    <div id="resultsContainer">
                                        <label class="header">User's posts:</label>
                                        <table>
                                            <tr>
                                                <th class="thTitle" align="center">Title</th>											
                                                    <c:choose>
                                                        <c:when test="${isCurrentUser}">
                                                        <th class="thAuthor" align="center">Edit</th>	
                                                        </c:when>
                                                        <c:otherwise>
                                                        <th class="thAuthor" align="center">Author</th>
                                                        </c:otherwise>
                                                    </c:choose>					
                                                <th class="thDate" align="center">Date</th>
                                            </tr>
                                            <c:forEach var="post" items="${user.posts}">
                                                <tr>
                                                    <td class="title">
                                                        <div class="tdContent">
                                                            <a href="<c:url value="/post${post.id}"/>"><c:out value="${post.title}"/></a>
                                                        </div>
                                                    </td>									
                                                    <c:choose>
                                                        <c:when test="${isCurrentUser}">
                                                            <td class="author">
                                                                <form action="editPost" method="POST">
                                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                                    <button type="submit">Edit</button>
                                                                </form>
                                                                <form action="removePost" method="POST">
                                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                                    <button type="submit">Remove</button>
                                                                </form>
                                                            </td>		
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td class="author">
                                                                <div class="tdContent">
                                                                    <c:out value="${post.author.username}" />
                                                                </div>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose>									
                                                    <td class="date">
                                                        <div class="tdContent">
                                                            <c:out value="${post.postDate}" />
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>	    
                                </c:if>
                            </c:when>
                            <c:otherwise>                                
                                <div class="field">
                                    <label><c:out value="Unknown user..."/></label>
                                </div>                               
                            </c:otherwise>
                        </c:choose>                        
                    </div>
                </div>
            </div>

            <div id="clear"></div>
            <div id="footer">
                <div class="title">
                    <label>Jurgen</label>
                </div>
            </div>
        </div>
    </body>
</html>
