<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="resources/css/signInStyle.css" rel="stylesheet"
              type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign in</title>
    </head>
    <body>

        <c:url value="/login" var="loginUrl" />
        <form id="signInForm" name="loginForm" action="${loginUrl}" method="POST">

            <div class="field">
                <label for="username">Username:</label>
                <input type="text" name="username" maxlength="35">
            </div>
            <div class="field">
                <label for="password">Password:</label>
                <input type="password"	name="password" maxlength="35">
            </div>

            <c:if test="${not empty param.error}">
                <c:if test="${param.error eq 'invalidData'}" >
                    <div class="errorMessage">
                        <p><c:out value="Invalid username or password" /></p>
                    </div>
                </c:if>                
            </c:if>

            <div class="lower">
                <a href="signUp">Create an account</a>
                <button type="submit">Sign in</button>
            </div>

        </form>

    </body>
</html>
