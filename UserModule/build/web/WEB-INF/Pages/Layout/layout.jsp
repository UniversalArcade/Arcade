<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<!DOCTYPE html>
<html>
    <head>
        <title> <tiles:getAsString name="title" /> </title>
        <meta name="description" content="Newschool Arcade" />
        <link href="_stylesheet.css" rel="stylesheet" type="text/css" />
        <tiles:getAsString name="dependence" ignore="true"/>
    </head>
    <body  <tiles:getAsString name="bodyAttr" ignore="true"/> >
        <div class="holder">
            <div class="top"></div>
            <div class="navi">
                <jsp:include page="Tiles/navi.jsp" />    
            </div>
            <div class="pad">
                <tiles:getAsString name="body"/>
            </div>
            <div class="basket"></div>
            <div class="footer"></div>
        </div> 
    </body>
</html>