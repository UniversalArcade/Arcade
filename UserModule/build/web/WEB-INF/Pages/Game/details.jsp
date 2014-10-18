<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Spieledetails bearbeiten"/> 
  <tiles:put name="body">

    <p>
      Schritt 1 : Details bearbeiten
    </p>

    <form method="POST" action="details">
        <input type="hidden" name="action" value="update"/>
       <table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr>
              <td width="15%">Titel</td>
              <td width="85%"><input type="text" name="title" value="${game.title}" size="30"/>
                  <c:out value="${game.errors['title']}"/>
              </td>

          </tr>
          <tr>
              <td width="15%">Beschreibung</td>
              <td width="85%">
                   <textarea name="description" cols="25" rows="8"><c:out value="${game.description}"/></textarea> 
                   <c:out value="${game.errors['description']}"/>
              </td>
          </tr>
          <tr>
              <td width="15%">Mitwirkende</td>
              <td width="85%">
                   <textarea name="credits" cols="25" rows="8"><c:out value="${game.credits}"/></textarea>
                   <c:out value="${game.errors['credits']}"/>
              </td>
          </tr>
           <tr>
              <td width="15%">PermanentStore</td>
              <td width="85%">
                  <input name="permanentStore" type="checkbox"  ${game.permanentStore == 1 ? "checked" : "" }/>
                  <c:out value="${game.errors['title']}"/>
              </td>

          </tr>
          <tr>
              <td colspan="2"><input type="submit" name="send" value="${game.inEditMode ? "bearbeiten" : "weiter"}" />
          </tr>
       </table>
   </form>

  </tiles:put>
</tiles:insert>
