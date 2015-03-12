<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

<jsp:include page="/WEB-INF/jsp/header.jsp" >
	<jsp:param name="title" value="Thank you!" />
</jsp:include>

  <div class="w-container checkout-container">
    <h2>Thanks for your purchase!</h2>
    <div class="order-info">Enjoy your awesome game! And thanks for supporting us broke college students!
      <br>
      <br>For reference, your order number is:
      <%
      	Random rand = new Random();
      
      	out.print("#" + rand.nextInt(10000));
      %>
      <br>
      <br>Remember to <strong>rate </strong>the game in your history <strong>once you have played it!</strong>
    </div><a class="history-link-text" href="./history.jsp">Your History</a>
  </div>
  
<jsp:include page="/WEB-INF/jsp/footer.jsp" />
