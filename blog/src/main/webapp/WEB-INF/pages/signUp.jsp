<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <link href="resources/css/signUpStyle.css" rel="stylesheet" type="text/css"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form:form id="signUpForm" action="signUp" modelAttribute="signUpForm" method="POST">

            <div class="field">         
                <label for="username">Username:</label>
                <form:input path="username" maxlength="35"/>                
            </div>

            <div class="errorMessage">
                <p><form:errors path="username"/></p>
            </div>

            <c:if test="${not empty usernameUniqueness}">
                <div class="errorMessage">
                    <p><c:out value="${usernameUniqueness}" /></p>
                </div>
            </c:if>

            <div class="field">
                <label for="email">Email:</label>
                <form:input path="email" maxlength="35"/>
            </div>

            <div class="errorMessage">
                <p><form:errors path="email"/></p>
            </div>

            <c:if test="${not empty emailUniqueness}">
                <div class="errorMessage">
                    <p><c:out value="${emailUniqueness}" /></p>
                </div>
            </c:if>

            <div class="field">
                <label for="password">Password:</label>
                <form:password path="password" maxlength="35"></form:password>
            </div>
            
            <div class="errorMessage">
                    <p><form:errors path="password"/></p>
            </div>    
            
            <div class="field">
                <label for="confirmPassword">Confirm password:</label>
                <form:password path="confirmPassword" maxlength="35"></form:password>
             </div>
            
            <div class="errorMessage">
                    <p><form:errors path="passValid"/></p>
            </div>  
            
            <div class="lower">
                <button type="submit">Sign up</button>			
            </div>
        </form:form>

    </body>
</html>
