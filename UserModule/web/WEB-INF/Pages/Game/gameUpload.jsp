<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Poster hochladen"/>
  <tiles:put name="siteName" value="gameUpload"/> 
  <tiles:put name="body">

         <p>
           Schritt 1 : Spiel hochladen
              <a onmouseover="InfoBoxAnzeigen(event,'Wählen Sie hier Ihre Spieledaten aus. Sie müssen gepackt in einer .zip vorliegen und ausgewählt werden.',20,-30);"
              accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
         </p>
         
         <form action="gameupload?action=update" method="post" enctype="multipart/form-data">
            <input type="file" name="file" />
                <c:out value="${game.errors['gameID']}"/>    
                <br />
            <input type="submit" value="Upload Game" />
         </form>

  </tiles:put>
</tiles:insert>



