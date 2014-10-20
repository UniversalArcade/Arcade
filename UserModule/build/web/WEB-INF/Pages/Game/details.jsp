<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Spieledetails bearbeiten"/>
  <tiles:put name="body">
  
    <p>
      Schritt 1 : Details bearbeiten
       <a onmouseover="InfoBoxAnzeigen(event,'Hier können Sie die Details Ihres Spiels bearbeiten. Sie erscheinen später in der Gesamtansicht aller Spiele.',20,-30);"
        accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
    </p>

    <form method="POST" action="details">
        <input type="hidden" name="action" value="update"/>
       <table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr>
              <td width="15%">Titel</td>
              <td width="85%"><input type="text" name="title" value="${game.title}" size="30"/>
                  <c:out value="${game.errors['title']}"/> <a onmouseover="InfoBoxAnzeigen(event,'Geben Sie hier den Titel Ihres Spiels ein. <br>Dieser erscheint später in der Spieleansicht.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
              </td>

          </tr>
          <tr>
              <td width="15%">Beschreibung</td>
              <td width="85%">
                   <textarea name="description" cols="25" rows="8"><c:out value="${game.description}"/></textarea> 
                   <c:out value="${game.errors['description']}"/>
                    <a onmouseover="InfoBoxAnzeigen(event,'Geben Sie hier eine kurze Beschreibung Ihres Spiels ein. <br>Der Text erscheint später auf der Detailseite im Frontend als Spieleinfo.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
                   
              </td>
          </tr>
          <tr>
              <td width="15%">Mitwirkende</td>
              <td width="85%">
                   <textarea name="credits" cols="25" rows="8"><c:out value="${game.credits}"/></textarea>
                   <c:out value="${game.errors['credits']}"/>
                    <a onmouseover="InfoBoxAnzeigen(event,'Geben Sie hier die Personen ein, die an der Entwicklung des Spiels beteiligt waren. <br>Diese Info erscheint später auf der Detailseite Ihres Spiels.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
              </td>
          </tr>
           <tr>
              <td width="15%">PermanentStore</td>
              <td width="85%">
                  <input name="permanentStore" type="checkbox"  ${game.permanentStore == 1 ? "checked" : "" }/>
                  <c:out value="${game.errors['title']}"/>
                  <a onmouseover="InfoBoxAnzeigen(event,'Durch deaktivieren dieser Funktion behalten ihre Spieledaten den Stand nach jedem beenden . Soll das Spiel nach jedem beenden zurückgesetzt werden, setzen Sie hier den Haken.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
              </td>

          </tr>
          <tr>
              <td colspan="2"><input type="submit" name="send" value="${game.inEditMode ? "bearbeiten" : "weiter"}" />
          </tr>
       </table>
   </form>

  </tiles:put>
</tiles:insert>
