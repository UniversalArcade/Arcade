<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<tiles:insert template="../Layout/layout.jsp">
  <tiles:put name="title" value="Buttonlayout bearbeiten"/> 
  <tiles:put name="body">

   <p>
                Schritt 4 : Buttonlayout erstellen
         </p>
         
         
        <form method="POST" action="GameButtonLayoutController">
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

  </tiles:put>
</tiles:insert>
