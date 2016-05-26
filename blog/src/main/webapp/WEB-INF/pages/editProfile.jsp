<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
    <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <div id="container">
            <%@include file="parts/header.jsp" %>
            <%@include file="parts/navigation.jsp" %>

            <div id="content">
                <div id="userInfo">
                    <sec:authorize access="isAuthenticated()">

                        <sec:authentication property="principal.username" var="username"/>
                        <sec:authentication property="principal.about" var="about"/>

                        <div class="centerField">
                            <c:out value="${username}, change something and press 'Save'" />
                        </div>

                        <form:form action="editProfile" modelAttribute="editProfileFormBean" method="POST" >

                            <div class="field">
                                <p>About:</p>
                            </div>

                            <textarea class="aboutArea" name="about" maxlength="500" placeholder="Some Text"><c:out value="${about}"/></textarea>
                            <button type="submit">Save</button>
                        </form:form>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <div class="field">
                            <label>You didn't sign in</label>
                        </div>		
                    </sec:authorize>
                </div>
            </div>

            <%@include file="parts/footer.jsp" %>
        </div>
       
    </body>
</html>
