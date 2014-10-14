<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>

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
                   <td width="15%">PermanentStore</td>
                   <td width="85%">
                       <input name="permanentStore" type="checkbox"  ${game.permanentStore == 1 ? "checked" : "" }/>
                       <c:out value="${game.errors['title']}"/>
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
                 <c:forEach begin="1" end="10" var="i"> 
                    <tr>
                        <td width="15%"><img src="img/ButtonSelector/ButtonSelector${i}.jpg" /></td>
                      <td width="85%"> 
                        
                        <select name="button${i}" size="1">                
                            <option value="unused"> Unbenutzt </option>
                            <c:forEach var="device" items="${buttons.devices}" varStatus="bb">
                                <optgroup label="${device}">
                                    <c:forEach var="item" items="${buttons.buttons[device]}" varStatus="bb">
                                        <option value="${item}"> 
                                            <c:out value="${item}" /> 
                                        </option>
                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                            
                      </td>
                    </tr>
                 </c:forEach>    
               <tr>
                   <td colspan="2"><input type="submit" name="send" value="weiter" />
               </tr>
            </table>
        </form>
     </c:when>    
     <c:when test="${game.newGameStep == 5}">
         <p> Ausführbare Datei auswählen </p>
         
        <div class="fileChooser"></div>
         <form method="POST" action="GameManagerController">
            <input type="hidden" name="exePath" class="exePath" value=""> 
            <input type="submit" value="Fertig" class="exePathSubmit" disabled="" />
        </form>
     
     </c:when>
     <c:when test="${game.newGameStep == 6}">
         
          ${game.reset()}

         Sie haben das Spiel erfolgreich in die Datenbank eingetragen
     </c:when>    
       <c:otherwise>
           Fehler
       </c:otherwise>
    </c:choose>






<%@include file="footer.jsp"%>
