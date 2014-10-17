<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Poster hochladen"/> 
  <tiles:put name="body">

         <p>
           Schritt 1 : Spiel hochladen
         </p>
         
         <form action="gameupload?action=update" method="post" enctype="multipart/form-data">
            <input type="file" name="file" />
                <c:out value="${game.errors['gameID']}"/>    
                <br />
            <input type="submit" value="Upload Game" />
         </form>

  </tiles:put>
</tiles:insert>



