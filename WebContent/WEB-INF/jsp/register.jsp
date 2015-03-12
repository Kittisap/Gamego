<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Register" />
</jsp:include>

<div class="w-container search-page-container">
	<h2>Register</h2>
    <div class="searchMargin">
    	<c:if test="${not empty error}">
    		<div>${error}</div><br />
    	</c:if>
    	<form class="signup-container" id="email-form" method="post" action="./register">
	        <label for="username">Username</label>
	        <input class="w-input" id="username" type="text" placeholder="Please enter your username" name="username" required="required" autofocus="autofocus" value="${username}" />
	        <label for="email">Email</label>
	        <input class="w-input" id="email" type="email" placeholder="Please enter your email" name="email" required="required" value="${email}" />
	        <label for="password">Password</label>
	        <input class="w-input" id="Password" type="password" placeholder="Please enter your password" name="password" required="required" />
	        <label for="passwordConfirm">Password Confirmation</label>
	        <input class="w-input" id="passwordConfirm" type="password" placeholder="Please enter your password again" name="passwordConfirm" required="required" />
	        <input class="pure-button pure-button-primary" type="submit" value="Register" />
        </form>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
