<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Your History" />
</jsp:include>

  <div class="w-container history-container">
    <h2>Your History</h2>
    <div class="history-transaction">
	    <c:forEach items="${transactions}" var="transaction">
	    	<div class="history-item">
	    		<a href="./game.jsp?id=${transaction.game.ID}">
	    			<img class="history-image" src="${transaction.game.boxArtPath}" />
	    		</a>
	    		<ul class="history-details">
	    			<li><a href="./game.jsp?id=${transaction.game.ID}">${transaction.game.title}</a></li>
	    			<li>Price: ${transaction.game.formattedPrice}</li>
	    		</ul>
	    	</div>
	    </c:forEach>
	</div>
  </div>

<jsp:include page="footer.jsp" />
