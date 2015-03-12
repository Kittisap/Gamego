<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Login" />
</jsp:include>

<div class="w-container search-page-container">
    <h2>Login</h2>
    <div class="searchMargin">
    	<c:if test="${not empty error}">
    		<div>${error}</div><br />
    	</c:if>
    	<form class="signup-container" id="email-form" method="post" action="./login">
	        <label for="Username">Username</label>
	        <input class="w-input" id="Username" type="text" placeholder="Please enter your username" name="username" required="required" value="${username}" />
	        <label for="Password">Password</label>
	        <input class="w-input" id="Password" type="password" placeholder="Please enter your password" name="password" required="required" />
	        <input class="pure-button pure-button-primary" type="submit" value="Login" />
        </form>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
