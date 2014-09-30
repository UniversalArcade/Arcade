<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>


<jsp:useBean id="game" class="app.beans.Game" scope="request"/>

<p>
    Neues Spiel anlegen
</p>

<form method="POST" action="GameManager">
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
            <td width="15%">Cover</td>
            <td width="85%"><input type="file"  name="cover" size="30"/>
                
            </td>
            
        </tr>
         <tr>
            <td width="15%">ButtonLayout</td>
            <td width="85%"> 
                <select name="button0" size="1">';                
                        <option value="A">A</option>
                        <option value="B">B</option>
                        <option value="C">C</option>
                </select>
                <select name="button1" size="1">';                
                        <option value="A">A</option>
                        <option value="B">B</option>
                        <option value="C">C</option>
                </select>
            </td>
          </tr>
          <tr>
            <td width="15%">Spieldateien</td>
            <td width="85%"><input type="file" name="gameFiles" size="30"/>         
            </td>
            
        </tr>
        <tr>
            <td colspan="2"><input type="submit" name="send" value="Hinzufügen" />
        </tr>
     </table>
  </form>


<%@include file="footer.jsp"%>
