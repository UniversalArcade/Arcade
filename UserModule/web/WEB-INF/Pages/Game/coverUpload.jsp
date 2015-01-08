<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Poster hochladen"/>
  <tiles:put name="siteName" value="coverupload"/>
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
      Schritt 2 : Cover hochladen
       <a onmouseover="InfoBoxAnzeigen(event,'Wählen Sie das Cover Ihres Spiels aus. <br>Es muss eine Mindestauflösung von 640x480 Pixel haben und im .jpg Format sein .',20,-30);"
                   accesskey="" onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
    </p>
    
    <c:if test="${game.states['coverupload'] == 'complete'}">
        
        <p>
            <img src="getImage" width="250" height="400" />
        </p>
        
    </c:if>

    <form action="coverupload?action=update" method="post" enctype="multipart/form-data">
        <input id="fileInput" type="file" name="file" accept="image/jpeg"/>
                <br />
            <input id="fileSubmit" type="submit" value="Upload Picture" disabled="disabled"  />
    </form>
    <div id="upload" style="display:none;">Uploading..</div>   

  </tiles:put>
</tiles:insert>
