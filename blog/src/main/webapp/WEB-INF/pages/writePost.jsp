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
            <%@include file="parts/header.jsp" %>
            <%@include file="parts/navigation.jsp" %>

            <div id="content">
                <sec:authorize access="isAuthenticated()">
                    <form:form id="writePostForm" action="writePost" modelAttribute="writePostFormBean" method="POST">
                        <div class="title">Write something!</div>
                        <label for="title" class="inputTitle">Title:</label>
                        <textarea class="titleTextArea" name="title" ></textarea>
                        <form:errors cssClass="errorMessage" path="title"/>
                        <label class="inputTitle">Text:</label>
                        <textarea class="contentTextArea" name="content"></textarea>
                        <form:errors cssClass="errorMessage" path="content"/>
                        <button type="submit">Send</button>
                    </form:form>

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
</html>

