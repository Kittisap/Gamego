<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="${product.title}" />
</jsp:include>

  <div class="w-container game-container">
  	<c:choose>
  	<c:when test="${not empty product}">
    <div class="w-row game-row-1">
      <div class="w-col w-col-8 game-info-col-1">
        <h2>${product.title}</h2><br />
        <div class="game-image-container">
        	<img class="game-image" src="${product.boxArtPath}" />
        </div>
        <h2>Description</h2>
        <div class="game-description">${product.description}</div>
      </div>
      <div class="w-col w-col-4 game-info-col-2">
        <div class="game-info">
			<strong>Genres:</strong>
			<c:choose>
				<c:when test="${fn:length(product.genres) gt 0}">
					<c:forEach items="${product.genres}" var="genre">
						<span>[${genre.name}]</span>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<span>N/A</span>
				</c:otherwise>
			</c:choose><br />
			<strong>Release Date</strong>: ${product.releaseDate}<br />
			<strong>Developer:</strong> ${product.developer.name}<br />
			<strong>Publisher:</strong> ${product.publisher.name}<br />
			<strong>ESRB Rating:</strong> ${product.ESRBRating.name}<br />
			<strong>Average User Rating:</strong> ${product.formattedUserRating}<br /><br />
			<strong>Price:</strong> ${product.formattedPrice}
			
			<c:if test="${product.stock == 0}">
				<br /><br /><strong>OUT OF STOCK</strong>
			</c:if>
		</div>
		
		<c:if test="${product.stock gt 0}">
			<div class="w-form">
				<form method="post" action="./cart.jsp">
					<input class="pure-button pure-button-primary add-to-cart" type="submit" value="Add to Cart" />
					<input type="hidden" name="gameId" value="${product.ID}" />
				</form>
			</div>
		</c:if>
			
	</div>
</div>
  </c:when>
  <c:otherwise>
  	<h2>Game Not Found</h2>
  	<div class="searchMargin">No game found with that ID number.</div>
  </c:otherwise>
  </c:choose>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />
