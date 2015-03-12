<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Thank you!" />
</jsp:include>

  <div class="w-container checkout-container">
    <h2>Thanks for your purchase!</h2>
    <div class="order-info">Enjoy your awesome games! And thanks for supporting us broke college students!<br /><br />
    	For reference, your order number is: ${transactionID}<br /><br />
    	Remember to <strong>rate</strong>the game in your history <strong>once you have played it!</strong>
    </div>
    <a class="history-link-text" href="./history">Your History</a>
  </div>
  
<jsp:include page="/WEB-INF/jsp/footer.jsp" />
