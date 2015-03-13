<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Your History" />
</jsp:include>

<div class="w-container history-container">
	<h2>Your History</h2>
	<div class="transaction-history">
		<c:forEach items="${transactions}" var="transaction">
			<div class="transaction">
				<h3>Transaction for ${transaction.transactionDate} (${transaction.formatTransactionID(transaction.transactionID)})</h3>
	    		<ul class="transaction-details">
	    			<li>Items: ${transaction.itemCount}</li>
	    			<li>Order Total: ${transaction.formattedOrderTotal}</li>
	    		</ul>
		    	<c:forEach items="${transaction.items}" var="transactionItem">
		    		<div class="transaction-item">
		    			<a href="./product?id=${transactionItem.item.ID}">
		    				<img class="transaction-item-image" src="${transactionItem.item.boxArtPath}" />
						</a>
						<ul class="transaction-item-details">
							<li><a href="./product?id=${transactionItem.item.ID}">${transactionItem.item.title}</a></li>
							<li>Price: ${transactionItem.item.formattedPrice}</li>
							<li>
								<form method="post" action="./rate">
									<input type="hidden" name="gameID" value="${transactionItem.item.ID}" />
										<select class="transaction-item-rating" name="rating">
											<option value="0"<c:if test="${transactionItem.rating == 0}"> selected</c:if>>Select a rating...</option>
											<option value="1"<c:if test="${transactionItem.rating == 1}"> selected</c:if>>1</option>
											<option value="2"<c:if test="${transactionItem.rating == 2}"> selected</c:if>>2</option>
											<option value="3"<c:if test="${transactionItem.rating == 3}"> selected</c:if>>3</option>
											<option value="4"<c:if test="${transactionItem.rating == 4}"> selected</c:if>>4</option>
											<option value="5"<c:if test="${transactionItem.rating == 5}"> selected</c:if>>5</option>
										</select>
										<button class="pure-button pure-button-primary" type="submit">Rate!</button>
								</form>
							</li>
						</ul>
		    		</div>
		    	</c:forEach>
			</div>
		</c:forEach>
		<c:if test="${fn:length(transactions) == 0}">
			<div class="cheekyMessage">You haven't purchased any games! Why not buy yourself something nice?</div>
		</c:if>
	</div>
</div>

<jsp:include page="footer.jsp" />
