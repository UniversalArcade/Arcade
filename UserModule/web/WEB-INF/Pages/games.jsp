<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="gamesList" class="app.beans.GamesList" scope="request"/>

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="Auflistung deiner Spiele"/>
  <tiles:put name="body">
    <p>
        <span style="font-weight:bold; font-size:100%">Spieleverwaltung</span>
    </p>  
    <form method="POST" action="GameListController">
        <table width="100%" border="0" cellspacing="1" cellpadding="3">  

            <c:forEach var="item" items="${gamesList.games}">
                 <p>
                     <a href="gameManager?action=edit&gameID=${item.gameID}"> <c:out value="${item.title}" /> </a> LIVE <input type="submit" name="send " value="löschen" /> 
                 </p>
            </c:forEach>     
            

        </table>
    </form>
    <form method="POST" action="gameManager?action=new">
        <input type="submit" name="send" value="neues Game anlegen" />
    </form>


  </tiles:put>
</tiles:insert>
