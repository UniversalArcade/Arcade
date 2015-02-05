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
      <input type="hidden" name="delete" value="update"/>
        <table width="100%" border="0" cellspacing="1" cellpadding="3">  
          <td><b>Titel</b></td> <td><b>Status On-/Offline</b></td> <td><b>Löschen</b></td>  
            
                
            <c:forEach var="item" items="${gamesList.games}">           
              <c:choose>  
                  
                <c:when test="${item.live == '1'}">
                    <p>
                        <tr>
                    <td><a href="gameManager?action=edit&gameID=${item.gameID}"> <c:out value="${item.title}" /> </td>
                    </a> <td><img src="img/green.png" width="10" height="10"/></td><td> <input type="submit" name=${item.gameID} value="löschen" /> </td></tr>
                 </p>
                </c:when> 
                <c:otherwise>
                <p>
                     <tr>
                    <td><a href="gameManager?action=edit&gameID=${item.gameID}"> <c:out value="${item.title}" /> </td>
                    </a> <td><img src="img/red.png" width="10" height="10"/></td><td> <input type="submit" name=${item.gameID} value="löschen" /> </td></tr>
                 </p>
                </c:otherwise>
              </c:choose>  
                 
            </c:forEach>     
            

        </table>
    </form>
    <form method="POST" action="gameManager?action=new">
        <input type="submit" name="send" value="neues Game anlegen" />
    </form>


  </tiles:put>
</tiles:insert>
