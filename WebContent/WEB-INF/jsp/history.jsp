<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Your History" />
</jsp:include>

<div class="w-container history-container">
	<h2>Your History</h2>
	<div class="transaction-history">
		<c:forEach items="${transactions}" var="transaction">
			<div class="transaction">
				<h3>Transaction for ${transaction.transactionDate}</h3>
	    		<ul class="transaction-details">
	    			<li>Items: ${transaction.itemCount}</li>
	    			<li>Order Total: ${transaction.formattedOrderTotal}</li>
	    		</ul>
		    	<c:forEach items="${transaction.items}" var="transactionItem">
		    		<div class="transaction-item">
		    			<a href="./game.jsp?id=${transactionItem.item.ID}">
		    				<img class="transaction-item-image" src="${transactionItem.item.boxArtPath}" />
						</a>
						<ul class="transaction-item-details">
							<li>${transactionItem.item.title}</li>
							<li>Price: ${transactionItem.item.formattedPrice}</li>
							<li>
								<c:choose>
									<c:when test="${transactionItem.rating == 0}">
										<form method="post" action="./rate">
											<input type="hidden" name="gameID" value="${transactionItem.item.ID}" />
											<select class="transaction-item-rating" name="rating">
												<option value="0">Select a rating...</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
											</select>
											<button class="pure-button pure-button-primary" type="submit">Rate!</button>
										</form>
									</c:when>
									<c:otherwise>
										Your Rating: ${transactionItem.rating}
									</c:otherwise>
								</c:choose>
							</li>
						</ul>
		    		</div>
		    	</c:forEach>
			</div>
		</c:forEach>
	</div>
</div>

<jsp:include page="footer.jsp" />
