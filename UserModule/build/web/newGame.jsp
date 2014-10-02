<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>


<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<p>
    Neues Spiel anlegen
</p>

 <c:choose>
     <c:when test="${game.newGameStep == 1}">
         
         <p>
           Schritt 1 : Spiel hochladen
         </p>
         
         <form action="GameManagerController" method="post" enctype="multipart/form-data">
            <input type="file" name="file" />
            <c:out value="${game.errors['gameID']}"/>    
            <br />
            <input type="submit" value="Upload Game" />
         </form>
         
       
     </c:when>
     <c:when test="${game.newGameStep == 2}">
         
         <p>
           Schritt 2 : Poster hochladen
         </p>
         
         <form action="GameManagerController" method="post" enctype="multipart/form-data">
            <input type="file" name="file" />
                <br />
            <input type="submit" value="Upload Picture" />
         </form>
             
     </c:when>
     <c:when test="${game.newGameStep == 3}">
         
         <p>
           Schritt 3 : Details bearbeiten
         </p>
         
         <form method="POST" action="GameManagerController">
            <table width="100%" border="0" cellspacing="1" cellpadding="3">
               <tr>
                   <td width="15%">Titel</td>
                   <td width="85%"><input type="text" name="title" value="${game.title}" size="30"/>
                       <c:out value="${game.errors['title']}"/>
                   </td>

               </tr>
               <tr>
                   <td width="15%">Beschreibung</td>
                   <td width="85%">
                        <textarea name="description" cols="25" rows="8"><c:out value="${game.description}"/></textarea> 
                        <c:out value="${game.errors['description']}"/>
                   </td>
               </tr>
               <tr>
                   <td width="15%">Mitwirkende</td>
                   <td width="85%">
                        <textarea name="credits" cols="25" rows="8"><c:out value="${game.credits}"/></textarea>
                        <c:out value="${game.errors['credits']}"/>
                   </td>
               </tr>
               <tr>
                   <td colspan="2"><input type="submit" name="send" value="weiter" />
               </tr>
            </table>
        </form>
     
     
     </c:when>
     <c:when test="${game.newGameStep == 4}">
         
         <jsp:useBean id="buttons" class="app.beans.ButtonLayout" scope="page"/>
         
         <p>
                Schritt 4 : Buttonlayout erstellen
         </p>
         
         
        <form method="POST" action="GameManagerController">
            <table width="100%" border="0" cellspacing="1" cellpadding="3">

                <tr>
                   <td width="15%">Button 1</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="1" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 2</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="2" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 3</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="3" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 4</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="4" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 5</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="5" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 6</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="6" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 7</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="7" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 8</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="8" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 9</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="9" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
                 <tr>
                   <td width="15%">Button 10</td>
                   <td width="85%"> 
                       <c:set var="buttonID" value="10" />
                       <%@include file="buttonLayoutSelector.jsp"%>
                   </td>
                 </tr>
                 
               <tr>
                   <td colspan="2"><input type="submit" name="send" value="Fertig" />
               </tr>
            </table>
        </form>
     </c:when>    
     <c:when test="${game.newGameStep == 5}">
         
         ${game.reset()}

         Sie haben das Spiel erfolgreich in die Datenbank eingetragen
     
     </c:when>    
       <c:otherwise>
           Fehler
       </c:otherwise>
    </c:choose>






<%@include file="footer.jsp"%>
