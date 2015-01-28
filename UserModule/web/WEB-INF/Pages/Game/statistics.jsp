<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<tiles:insert template="../Layout/gameLayout.jsp">
    <tiles:put name="title" value="Status zu ${game.title}"/> 
    <tiles:put name="siteName" value="Status"/> 
  <tiles:put name="body">
      <p>
          TODO: dein spiel ist live / nicht live (ändern)
          <input name="liveToggle" type="checkbox"  ${game.life == 1 ? "checked" : "" }/>
          <c:out value="${game.errors['title']}"/> 
      </p> 

    <p>
        anzahl aufrufe: <c:out value="${game.gameStarts}" />
    </p>
    <p>
        Zeit gespielt: <c:out value="${game.gameDuration}" />
    </p>

     <td colspan="2"><input type="submit" name="send" value="${game.inEditMode ? "bearbeiten" : "weiter"}" />

  </tiles:put>
</tiles:insert>
