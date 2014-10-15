<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="gamesList" class="app.beans.GamesList" scope="request"/>

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="Auflistung deiner Spiele"/>
  <tiles:put name="body">

    <c:if test="${user.userLvl > 0}">
       <c:forEach var="item" items="${gamesList.games}">
            <p>
                <a href="GameDetailController?gameID=${item.gameID}"> <c:out value="${item.title}" /> </a>
            </p>
        </c:forEach>     
    </c:if>
    
    <c:if test="${user.userLvl == 0}">
        <jsp:forward page="index.jsp"/>
    </c:if>           
            

    <form method="POST" action="GameManagerController">
        <input type="submit" name="send" value="neues Game anlegen" />
    </form>


  </tiles:put>
</tiles:insert>
