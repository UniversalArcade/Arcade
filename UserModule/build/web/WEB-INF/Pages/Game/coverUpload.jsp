<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<tiles:insert template="../Layout/layout.jsp">
  <tiles:put name="title" value="Poster hochladen"/> 
  <tiles:put name="body">

    <p>
      Schritt 2 : Poster hochladen
    </p>

    <form action="coverupload?action=update" method="post" enctype="multipart/form-data">
           
        <input type="file" name="file" />
                <br />
            <input type="submit" value="Upload Picture" />
    </form>

  </tiles:put>
</tiles:insert>
