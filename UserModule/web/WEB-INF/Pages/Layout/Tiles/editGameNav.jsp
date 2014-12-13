
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="app.beans.User" scope="session"/>
<jsp:useBean id="game" class="app.beans.Game" scope="session"/>
<jsp:useBean id="gameComponents" class="app.beans.GameComponents" scope="session"/>


<ul>
    
    
    
    
    <c:out value="Hallo + ${requestScope.currentSite}" />
    
    
    
    
        
    <!-- nur bei edit modus -->
    <c:if test="${game.inEditMode}">
                <li>
                    <p>
                        <a href="statistics">Statistik</a>
                    </p>
                </li>
     </c:if>
    <!-- / nur bei Edit modus -->            
    
    <c:forEach var="item" items="${gameComponents.components.entrySet()}" varStatus="bb">
        
       
        <c:if test="${ item.key != 'exechooser' ||  (item.key == 'exechooser' && game.emulationGame == 0)}">
         <li>
            <p>
                <a href="${item.key}">
                    <c:out value="${item.value}" />
                </a>
            </p>
        </li>
        </c:if>
        
    </c:forEach>
    
    </ul>      
