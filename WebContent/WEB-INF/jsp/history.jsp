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
		    	<c:forEach items="${transaction.items}" var="item">
		    		<div class="transaction-item">
		    			<a href="./game.jsp?id=${item.ID}">
		    				<img class="transaction-item-image" src="${item.boxArtPath}" />
						</a>
		    		</div>
		    	</c:forEach>
			</div>
		</c:forEach>
	</div>
</div>

<jsp:include page="footer.jsp" />
