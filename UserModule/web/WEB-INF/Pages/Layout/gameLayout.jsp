<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title> <tiles:getAsString name="title" /> </title>
        <meta name="description" content="Newschool Arcade" />
        <link href="_stylesheet.css" rel="stylesheet" type="text/css" />
        <script src="Scripts/tooltip.js"></script>
        <tiles:getAsString name="dependence" ignore="true"/>
    </head>
    <body  <tiles:getAsString name="bodyAttr" ignore="true"/> >
        <div class="holder">
            <div class="top"></div>
            <div class="navi">
                <jsp:include page="Tiles/navi.jsp" />    
            </div>
            <div class="gameNavi">
                <c:set var="currentSite" scope="request"> 
                    <tiles:getAsString name="siteName"/>
                </c:set> 
                <jsp:include page="Tiles/editGameNav.jsp" />    
            </div>
            <div id="message">
                <jsp:include page="Tiles/message.jsp" />  
            </div>
            <div class="pad">
                
                <div id="InfoBox" style="z-index:1; visibility:hidden;">
                    <div id="BoxInnen"><span id="BoxInhalte">&nbsp;</span></div>
                </div>
                
                <tiles:getAsString name="body"/>
            </div>
            <div class="basket"></div>
            <div class="footer"></div>
        </div> 
    </body>
</html>