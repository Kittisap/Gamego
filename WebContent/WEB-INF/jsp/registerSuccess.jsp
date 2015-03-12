<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Registration Successful" />
</jsp:include>

<div class="w-container search-page-container">
	<h2>Registration Successful</h2>
    <div class="searchMargin">Welcome to GameGo, ${username}! You may login <a href="./login">here</a>.</div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
