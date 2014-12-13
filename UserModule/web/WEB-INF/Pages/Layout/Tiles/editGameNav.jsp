
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="app.beans.User" scope="session"/>
<jsp:useBean id="game" class="app.beans.Game" scope="session"/>


<ul>
                     
        
    <!-- nur bei edit modus -->
    <c:if test="${game.inEditMode}">
                <li>
                    <p>
                        <a href="statistics">Statistik</a>
                    </p>
                </li>
     </c:if>
    <!-- / nur bei Edit modus -->            
                
                <li>
                    <p>
                        <a href="details">Details</a>
                    </p>
                </li>
                <li>
                    <p>
                        <a href="coverupload">Cover</a>
                    </p>
                </li>
                <li>
                    <p>
                        <a href="buttonlayout">Button Layout</a>
                    </p>
                </li>
                <li>
                    <p>
                        <a href="gameupload">Game upload</a>
                    </p>
                </li>
                
                <c:if test="${game.emulationGame == 0}">
                <li>
                    <p>
                        <a href="exechooser">Choose exe file</a>
                    </p>
                </li>
                </c:if>
  
    </ul>      
