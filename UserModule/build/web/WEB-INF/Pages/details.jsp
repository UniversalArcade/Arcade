<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="gameDetails" class="app.beans.GamesDetail" scope="request"/>

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="Spiel bearbeiten"/>
  <tiles:put name="body">

    
    
    <c:if test="${user.userLvl > 0}">
        <c:out value="${gameDetails.title }"/>
    </c:if> 


    <c:if test="${user.userLvl == 0}">
        <jsp:forward page="index.jsp"/>
    </c:if>       
            


    <!-- TODO : klickbarer link anstatt formular -->
    <form method="POST" action="GameListController">
        <input type="submit" name="send" value="zurück zur Übersicht" />
    </form>


  </tiles:put>
</tiles:insert>
