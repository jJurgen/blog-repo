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
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal.username" var="username"/>
                            <sec:authentication property="principal.about" var="about"/>
                            <div class="field">
                                <c:out value="${username}, change some and press 'Save'" />
                                <form:form action="editProfile" modelAttribute="editProfileFormBean" method="POST" >
                                    <div class="field">
                                        <label>About:</label>
                                    </div>
                                    <textarea class="aboutArea" name="about" maxlength="500" placeholder="Some Text"><c:out value="${about}"/></textarea>
                                    <button type="submit">Save</button>
                                </form:form>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <div class="field">
                                <label>You didn't sign in</label>
                            </div>		
                        </sec:authorize>
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
