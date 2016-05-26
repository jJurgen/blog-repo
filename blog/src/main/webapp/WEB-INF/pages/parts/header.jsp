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