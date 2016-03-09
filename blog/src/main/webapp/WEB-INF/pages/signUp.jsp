<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <link href="resources/css/blogStyles.css" rel="stylesheet" type="text/css"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form:form id="signInForm" action="signUp" modelAttribute="signUpForm" method="POST">
            <div class="field">         
                <label>Username:</label>
                <form:input path="username" maxlength="35"/>                
            </div>
            <div class="errorMessage">
                <form:errors path="username"/>
            </div>
            <c:if test="${not empty usernameUniqueness}">
                <div class="errorMessage">
                    <c:out value="${usernameUniqueness}" />
                </div>
            </c:if>
            <div class="field">
                <label>Email:</label>
                <form:input path="email" maxlength="35"/>
            </div>
            <div class="errorMessage">
                <form:errors path="email"/>
            </div>
            <c:if test="${not empty emailUniqueness}">
                <div class="errorMessage">
                    <c:out value="${emailUniqueness}" />
                </div>
            </c:if>
            <div class="field">
                <label>Password:</label>
                <form:password path="password" maxlength="35"></form:password>
                </div>
                <div class="errorMessage">
                <form:errors path="password"/>
            </div>    
            <div class="field">
                <label>Confirm password:</label>
                <form:password path="confirmPassword" maxlength="35"></form:password>
                </div>
                <div class="errorMessage">
                <form:errors path="passValid"/> 
            </div>    
            <div class="submit">
                <button class ="regButton" type="submit">Sign up</button>			
            </div>
        </form:form>

    </body>
</html>
