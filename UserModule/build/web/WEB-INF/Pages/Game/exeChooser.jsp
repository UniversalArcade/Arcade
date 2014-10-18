<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Choose .exe file"/>
  <tiles:put name="dependence">
        <script src="Scripts/d3.js" charset="utf-8"></script>
        <script src="Scripts/exeChooser.js"></script>
  </tiles:put>
  <tiles:put name="bodyAttr" value="onload = 'init(${game.filePathJSON})' " />
  <tiles:put name="body">

        <p> Ausführbare Datei auswählen </p>
         
        <div class="fileChooser"></div>
         <form method="POST" action="exechooser?action=update">
            <input type="hidden" name="exePath" class="exePath" value=""> 
            <input type="submit" value="${game.inEditMode ? "bearbeiten" : "fertig"}" class="exePathSubmit" disabled="" />
        </form>

  </tiles:put>
</tiles:insert>
