
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="app.beans.User" scope="session"/>
<jsp:useBean id="game" class="app.beans.Game" scope="session"/>
<jsp:useBean id="gameComponents" class="app.beans.GameComponents" scope="session"/>


<ul>
    
     <c:if test="${requestScope.currentSite == 'statistics'}" >
            <c:set var="statasticsClass" value="active" />
        </c:if>   
        
    <!-- nur bei edit modus -->
    <c:if test="${game.inEditMode}">
                <li>
                    <p>
                        <a class="${statasticsClass}" href="statistics">Statistik</a>
                    </p>
                </li>
     </c:if>
    <!-- / nur bei Edit modus -->            
    
    
    
    <c:forEach var="item" items="${gameComponents.components.entrySet()}" varStatus="bb">
        <c:set var="class" value="" />
        
        <c:if test="${game.states[ item.key ] == 'complete' }" >
            <c:set var="class" value="complete" />
        </c:if>
        <c:if test="${game.states[ item.key ] == 'incomplete' }" >
            <c:set var="class" value="inactive" />
        </c:if>
        
        <c:if test="${item.key == requestScope.currentSite}" >
            <c:set var="class" value="active" />
        </c:if>    
        
        
        
        <c:if test="${ item.key != 'exechooser' ||  (item.key == 'exechooser' && game.emulationGame == 0)}">
         <li>
            <p>
                <a class="${class}" href="${item.key}">
                    <c:out value="${item.value}" />
                </a>
            </p>
        </li>
        </c:if>
        
    </c:forEach>
    
    </ul>      
