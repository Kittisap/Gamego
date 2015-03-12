<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" >
	<jsp:param name="title" value="Your History" />
</jsp:include>

  <div class="w-container history-container">
    <h2>Your History</h2>
	<table>
	    <c:forEach items="${transactions}" var="transaction">
	        <tr>
	            <td>${transaction.game.boxartpath}</td>
	            <td>${transaction.game.formattedPrice}</td>
	            <td>${transaction.transactionDate}</td>
	        </tr>
	    </c:forEach>
	</table>
  </div>

<jsp:include page="footer.jsp" />
