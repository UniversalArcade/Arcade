<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="gamesList" class="app.beans.GamesList" scope="request"/>

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="Auflistung deiner Spiele"/>
  <tiles:put name="body">

    <c:forEach var="item" items="${gamesList.games}">
         <p>
             <a href="gameManager?action=edit&gameID=${item.gameID}"> <c:out value="${item.title}" /> </a> LIVE
         </p>
    </c:forEach>     
    
    <form method="POST" action="gameManager?action=new">
        <input type="submit" name="send" value="neues Game anlegen" />
    </form>


  </tiles:put>
</tiles:insert>
