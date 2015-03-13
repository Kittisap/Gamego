<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="${searchQuery}" />
</jsp:include>

<div class="w-container search-page-container">
	<h2>Search</h2>
	<form id="email-form" method="get" action="./search">
		<input class="w-input game-search" id="search-Game" type="text" 
			placeholder="Search for a game..." name="q" 
        	value="${searchQuery}" />
		<div class="searchMargin">
			<label for="genreID">Genre</label>
			<select id="genreID" name="genreID">
	        	<option value="0">All</option>
		        <c:forEach items="${genres}" var="genre">
		        	<option value="${genre.ID}"<c:if test="${genreID == genre.ID}"> selected</c:if>>${genre.name}</option>
		        </c:forEach>
			</select>
        </div><br />
        <input class="searchMargin pure-button pure-button-primary" type="submit" value="Search" />
	</form>
</div>

<c:if test="${not empty searchQuery}">
	<div class="w-container search-page-container">
		<h2>Results</h2>
		<c:choose>
			<c:when test="${fn:length(searchResults.results) gt 0}">
				<div class="searchMargin">Displaying page ${searchResults.currentPage} of ${searchResults.totalPages}.</div>
				<div class="searchMargin">Displaying results ${searchResults.resultStart} to ${searchResults.resultEnd} of ${searchResults.totalResults}.</div><br />
				<div class="searchResults">
					<ul>
					    <c:forEach items="${searchResults.results}" var="searchResult">
					    	<li><a href="./product?id=${searchResult.ID}">${searchResult.shortTitle}</a></li>
					    </c:forEach>
					</ul>
				</div><br />
				<div class="searchMargin">
					<c:choose>
						<c:when test="${searchResults.currentPage == 1}">
							<a class="pure-button pure-button-disabled" href="#">First</a>
							<a class="pure-button pure-button-disabled" href="#">Previous</a>
						</c:when>
						<c:otherwise>
							<a class="pure-button pure-button-primary" href="./search?q=${searchQuery}&genreID=${searchResults.genreID}&page=1">First</a>
							<a class="pure-button pure-button-primary" href="./search?q=${searchQuery}&genreID=${searchResults.genreID}&page=${searchResults.currentPage - 1}">Previous</a>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${searchResults.currentPage == searchResults.totalPages}">
							<a class="pure-button pure-button-disabled" href="#">Next</a>
							<a class="pure-button pure-button-disabled" href="#">Last</a>
						</c:when>
						<c:otherwise>
							<a class="pure-button pure-button-primary" href="./search?q=${searchQuery}&genreID=${searchResults.genreID}&page=${searchResults.currentPage + 1}">Next</a>
							<a class="pure-button pure-button-primary" href="./search?q=${searchQuery}&genreID=${searchResults.genreID}&page=${searchResults.totalPages}">Last</a>
						</c:otherwise>
					</c:choose>
				</div>
			</c:when>
			<c:otherwise>
				<div class="searchMargin">No results found for "${searchQuery}".</div>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
