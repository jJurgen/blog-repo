<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <link href="resources/css/blogStyles.css" rel="stylesheet"
              type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <c:url value="/login" var="loginUrl" />
        <form id="signInForm" name="loginForm" action="${loginUrl}" method="POST">

            <c:if test="${not empty param.error}">
                <c:if test="${param.error eq 'invalidData'}" >
                    <label class="errorMessage"><c:out value="Invalid username or password"/></label>
                </c:if>                
            </c:if>

            <div class="field">
                <label>Nickname:</label>
                <input type="text" name="username" maxlength="35">
            </div>
            <div class="field">
                <label>Password:</label>
                <input type="password"	name="password" maxlength="35">
            </div>
            <div class="submit">
                <button class="signInButton" type="submit">Sign in</button>
                <label class="remember"><input name="rememberMe" type="checkbox">Remember</label>
            </div>
        </form>

    </body>
</html>
