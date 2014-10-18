<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<tiles:insert template="../Layout/gameLayout.jsp">
    <tiles:put name="title" value="Statistiken zu ${game.title}"/> 
  <tiles:put name="body">
      <p>
          TODO: dein spiel ist live / nicht live (ändern)
          
      </p> 

    <p>
        anzahl aufrufe: <c:out value="${game.gameStarts}" />
    </p>
    <p>
        Zeit gespielt: <c:out value="${game.gameDuration}" />
    </p>

    

  </tiles:put>
</tiles:insert>
