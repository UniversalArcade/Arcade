<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<tiles:insert template="../Layout/gameLayout.jsp">
  <tiles:put name="title" value="Buttonlayout bearbeiten"/> 
  <tiles:put name="siteName" value="buttonlayout"/> 
  <tiles:put name="body">

         <p>
                Schritt 3 : Buttonlayout erstellen
                 <a onmouseover="InfoBoxAnzeigen(event,'Legen Sie hier die Tastenbelegung Ihres Spiels fest. <br>Sie müssen nicht alle Tasten belegen, aber mindestens eine Taste muss eine Funktion besitzen. Nicht erlaubt sind vom System belegte Kombination wie ALT+TAB oder ALT+ENTER usw. Auch nicht gestattet ist das belegen der F1-F12 Tasten.',20,-30);"
                   accesskey="" onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
         </p>
         
         
        <form method="POST" action="buttonlayout">
           <input type="hidden" name="action" value="update" />
            <table width="100%" border="0" cellspacing="1" cellpadding="3">
                 <c:forEach begin="1" end="10" var="i"> 
                    <tr>
                        <td width="15%"><img src="img/ButtonSelector/ButtonSelector${i}.jpg" /></td>
                      <td width="85%"> 
                          
                         
                            <select name="${game.emulationGame > 0 ? "" : "button"}${game.emulationGame > 0 ? "" : i}" size="1"  ${game.emulationGame > 0 ? " disabled" : ""}>                
                              <option value="unused"> Unbenutzt </option>
                              <c:forEach var="device" items="${buttons.devices}" varStatus="bb">
                                  <optgroup label="${device}">
                                      <c:forEach var="item" items="${buttons.buttons[device]}" varStatus="bb">
                                          <option value="${item}" ${item == ((game.buttonLayout.get(i-1)).keySet().toArray())[0] ? 'selected' : ''}> 
                                              <c:out value="${item}" />  
                                          </option>
                                      </c:forEach>
                                  </optgroup>
                              </c:forEach>
                          </select>
                          
                              
                            <c:if test="${game.emulationGame > 0 ? true : false}" >
                              <input type="hidden" name="button${i}" value="${((game.buttonLayout.get(i-1)).keySet().toArray())[0]}" />    
                            </c:if> 
                              
                          <a onmouseover="InfoBoxAnzeigen(event,'Wählen Sie hier die Tastenbelegung aus, die ausgeführt werden soll.',20,-30);"
                          accesskey="" onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a> 
                          <br>
                       
                        
                       
                        
                        
                        <input type="text" name="function${i}" value="${ ((game.buttonLayout.get(i-1)).values().toArray())[0]}" size="12"/>    
                        <a onmouseover="InfoBoxAnzeigen(event,'Tragen Sie hier den Namen der Funktion ein. Soll zum Bsp. mit Joystick UP die Aktion springen ausgeführt werden ,tragen Sie hier springen ein. Die Bezeichnung erscheint später in der Spieledetailansicht.',20,-30);"
                   accesskey="" onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
                      </td>
                      
                    </tr>
                 </c:forEach>    
               <tr>
                   <td colspan="2"><input type="submit" name="send" value="${game.inEditMode ? "bearbeiten" : "weiter"}" />
               </tr>
            </table>
        </form>

  </tiles:put>
</tiles:insert>
