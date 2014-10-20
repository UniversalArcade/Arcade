<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Poster hochladen"/> 
  <tiles:put name="body">

    <p>
      Schritt 2 : Cover hochladen
       <a onmouseover="InfoBoxAnzeigen(event,'Wählen Sie das Cover Ihres Spiels aus. <br>Es muss eine Mindestauflösung von 640x480 Pixel haben und im .jpg Format sein .',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
    </p>

    <form action="coverupload?action=update" method="post" enctype="multipart/form-data">
           
        <input type="file" name="file" />
                <br />
            <input type="submit" value="Upload Picture" />
    </form>

  </tiles:put>
</tiles:insert>
