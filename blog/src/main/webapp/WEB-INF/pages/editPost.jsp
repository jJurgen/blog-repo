<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit</title>
</head>
<body>
    <div id="container">
        <%@include file="parts/header.jsp" %>
        <%@include file="parts/navigation.jsp" %>

        <div id="content">
            <sec:authorize access="isAuthenticated()">
                <c:choose>
                    <c:when test="${not empty post}">
                        <form:form id="writePostForm" action="updatePost" modelAttribute="updatePostFormBean" method="POST">
                            <label class="title">Change something</label>
                            <label class="inputTitle">Title:</label>
                            <textarea class="titleTextArea" name="title" maxlength="200"><c:out value="${post.title}"/></textarea>
                            <form:errors cssClass="errorMessage" path="title"/>
                            <label class="inputTitle">Text:</label>
                            <textarea class="contentTextArea" name="content" maxlength="3000"><c:out value="${post.content}"/></textarea>
                            <form:errors cssClass="errorMessage" path="content"/>
                            <input type="hidden" name="postId" value="${post.id}" />
                            <button type="submit">Save</button>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <sec:authentication property="principal.posts" var="posts"/>
                        <c:if test="${not empty posts}">
                            <div id="resultsContainer">
                                <label class="header">Your posts:</label>
                                <table>
                                    <tr>
                                        <th class="thTitle" align="center">Title</th>										
                                        <th class="thAuthor" align="center">Edit</th>	
                                        <th class="thDate" align="center">Date</th>
                                    </tr>
                                    <c:forEach items="${posts}" var="currPost">
                                        <tr>
                                            <td class="title">
                                                <div class="tdContent">
                                                    <a href="<c:url value="/post${currPost.id}"/>"><c:out value="${currPost.title}"/></a>
                                                </div>
                                            </td>								
                                            <td class="author">
                                                <form action="editPost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${currPost.id}"/>"/>
                                                    <input type="submit" value="Edit">
                                                </form>
                                                <form action="removePost" method="POST">
                                                    <input type="hidden" name="postId" value="<c:out value="${currPost.id}"/>"/>
                                                    <input type="submit" value="Remove">										
                                                </form>
                                            </td>		                                                       							
                                            <td class="date">
                                                <div class="tdContent">
                                                    <c:out value="${currPost.postDate}" />
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:if>
                    </c:otherwise>
                </c:choose>                        
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <c:out value="You are not logged in" />
            </sec:authorize>
        </div>

        <%@include file="parts/footer.jsp" %>
    </div>

    <script>
        var area = document.getElementsByClassName("contentTextArea")[0];
        var tabWhiteSpaces = "      ";

        area.onkeydown = function (e) {
            e = e || event;

            if (e.keyCode == 9) {
                var startPosition = area.selectionStart;
                var endPosition = area.selectionEnd;

                area.value = area.value.substring(0, startPosition) + tabWhiteSpaces + area.value.substring(endPosition, area.value.length);
                area.selectionStart = area.selectionEnd = startPosition + tabWhiteSpaces.length;

                return false;
            }
        }

    </script>

</body>

