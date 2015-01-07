<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Poster hochladen"/>
  <tiles:put name="siteName" value="gameupload"/>
  <tiles:put name="dependence">
        <script src="Scripts/jquery-2.1.1.js"></script>

        <script language="Javascript">
	$(document).ready(function(){
          $('#fileInput').change(function(){
              $('#fileSubmit').removeAttr("disabled");
          });  
            
	  $('#fileSubmit').click(function(){
             $('#upload').show(); 
          });
	});
	</script>
  </tiles:put>
  <tiles:put name="body">

         <p>
           Schritt 1 : Spiel hochladen
              <a onmouseover="InfoBoxAnzeigen(event,'Wählen Sie hier Ihre Spieledaten aus. Sie müssen gepackt in einer .zip vorliegen und ausgewählt werden.',20,-30);"
              accesskey="" onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
         </p>
         
         <form action="gameupload?action=update" method="post" enctype="multipart/form-data">
             <input id="fileInput" type="file" name="file" accept="application/zip" />
                <c:out value="${game.errors['gameID']}"/>    
                <br />
            <input id="fileSubmit" type="submit" value="Upload Game" disabled="disabled"/>
         </form>
         <div id="upload" style="display:none;">Uploading..</div>       

  </tiles:put>
</tiles:insert>



