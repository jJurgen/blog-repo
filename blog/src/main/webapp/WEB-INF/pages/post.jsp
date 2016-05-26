<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
    <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Post</title>
</head>
<body>
    <div id="container">

        <%@include file="parts/header.jsp" %>
        <%@include file="parts/navigation.jsp" %>

        <div id="content">
            <div id="postContainer">
                <c:if test="${not empty post}">
                    <div class="authorField">
                        <c:out value="Author:"/>						
                        <a class="nickname" href="<c:url value="profile=${post.author.username}" />"><c:out value="${post.author.username}"/></a>
                    </div>
                    <div class="postDate">
                        <c:out value="${post.postDate}" />
                    </div>
                    <div class="postTitle">
                        <c:out value="${post.title}" />
                    </div>
                    <div class="postContent">
                        <pre><c:out value="${post.content}"/></pre>
                    </div>
                    <sec:authorize access="hasRole('ROLE_MODER')">
                        <form class="removeForm" action="removePost" method="POST">
                            <input type="hidden" name="postId" value="<c:out value="${post.id}"/>">
                            <button type="submit">remove</button>
                        </form>
                    </sec:authorize>

                    <sec:authorize access="isAnonymous()">
                        <div class="afterPostInfo">
                            <label>* if you want to write a comment - please sign in</label>
                        </div>
                    </sec:authorize>

                </c:if>
            </div>


            <div id="commentContainer">

                <sec:authorize access="isAuthenticated()">
                    <form:form cssClass="addCommentForm" action="addComment" modelAttribute="addCommentFormBean" method="POST">
                        <div class="avatarBlock">
                            <img src="resources/img/defaultAvatar.png" >
                        </div>
                        <textarea name="comment" maxlength="500" placeholder="Write comment..."></textarea>
                        <input type="hidden" name="postId" value="<c:out value="${post.id}"/>">
                        <button type="submit">Write</button>
                    </form:form>
                </sec:authorize>
                <div class="title">
                    <label class="commentsTitle">Comments:</label>
                </div>

                <c:forEach var="comment" items="${post.comments}">
                    <div class="commentArea">

                        <div class="avatarBlock">
                            <img src="resources/img/defaultAvatar.png" >
                        </div>

                        <div class="commentBlock">
                            <div class="meta">
                                <a href="<c:url value="profile=${comment.author.username}"/>"><c:out value="${comment.author.username}"/></a>
                                <c:out value=", ${comment.commentDate}" />
                            </div>
                            <div class="content">
                                <c:out value="${comment.content}"/>
                            </div>                                
                        </div>

                        <sec:authorize access="hasRole('ROLE_MODER')">
                            <form class="removeForm" action="removeComment" method="POST">
                                <input type="hidden" name="commentId" value="<c:out value="${comment.id}"/>">
                                <button type="submit">remove</button>
                            </form>
                        </sec:authorize>

                    </div>
                </c:forEach>                    

            </div>

        </div>

        <%@include file="parts/footer.jsp" %>
    </div>
</body>
</html>
