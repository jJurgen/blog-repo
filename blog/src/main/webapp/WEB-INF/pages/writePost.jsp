<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
    <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Write Post</title>
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
                    <sec:authorize access="isAuthenticated()">
                        <form:form id="writePostForm" action="writePost" modelAttribute="writePostFormBean" method="POST">
                            <label class="title">Would you like to write a new post?</label>
                            <label class="inputTitle">Title:</label>
                            <textarea class="titleTextArea" name="title" ></textarea>
                            <form:errors cssClass="errorMessage" path="title"/>
                            <label class="inputTitle">Text:</label>
                            <textarea class="contentTextArea" name="content"></textarea>
                            <form:errors cssClass="errorMessage" path="content"/>
                            <button type="submit">Add Post</button>
                        </form:form>

                        <div id="resultsContainer">
                            <label class="header">Your posts:</label>
                            <c:if test="${not empty posts}">
                                <table>
                                    <tr>
                                        <th class="thTitle" align="center">Title</th>											
                                        <th class="thAuthor" align="center">Edit</th>
                                        <th class="thDate" align="center">Date</th>
                                    </tr>
                                    <c:forEach var="post" items="${posts}">
                                        <tr>
                                            <td class="title">
                                                <div class="tdContent">
                                                    <a href="<c:url value="/post${post.id}"/>"><c:out value="${post.title}"/></a>
                                                </div>
                                            </td>
                                            <td class="author">                                                
                                                <form  action="editPost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                    <button type="submit">Edit</button>
                                                </form>
                                                <form  action="removePost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${post.id}"/>"/>
                                                    <button type="submit">Remove</button>
                                                </form>
                                            </td>		
                                            <td class="date">
                                                <div class="tdContent">
                                                    <c:out value="${post.postDate}" />
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </div>	 
                    </sec:authorize>
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
