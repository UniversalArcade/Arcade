<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="message" class="app.beans.Message" scope="session"/>

<c:forEach var="item" items="${message.messages}" varStatus="bb">
    
    <p class="${item.getType()}">
            <c:out value="${item.getMessage()}" />
    </p>
    
    
</c:forEach>
    
  


