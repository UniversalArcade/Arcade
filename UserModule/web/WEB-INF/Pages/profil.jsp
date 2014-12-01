<!-- Profilseite zum anzeigen und ändern der pers. Daten-->
<!-- Jede Contentseite bindet header und footer ein -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="customer" class="app.beans.Costumer" scope="request" />

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="Profil"/>
  
  <tiles:put name="body">

       

        <p>
            <span style="font-weight:bold; font-size:100%">Profil</span> 
        </p>

        <form method="POST" action="ProfilController">
            <table width="100%" border="0" cellspacing="1" cellpadding="3">
                <tr>
                    <td width="35%">Aktuelle Email Adresse</td>
                    <td width="65%">   <c:out value="${customer.getMail()}"/>             </td>
          
                </tr>
                
                <tr>
                    
                    <td width="35%">Mail ändern</td>
                    <td width="65%"><input type="text" name="mail" value="${customer.mail}" size="30"/>
                        
                        <c:out value="${customer.errors['mail']}"/>
                         
                        <a onmouseover="InfoBoxAnzeigen(event,'Hier können Sie Ihre Email Adresse ändern.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
                    </td>

                </tr> 
                <tr>
                 
                </tr>  
                <tr>
                       <td width="35%">Passwort zur Bestätigung</td>
                    <td width="65%">
                        <input type="password" name="password" value="${customer.password}" size="30"/>
                        
                        <c:out value="${customer.errors['password']}"/>
                        
                        <a onmouseover="InfoBoxAnzeigen(event,'Geben Sie hier bitte Ihre aktuelles Passwort zur validierung ein.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
                    </td>
                    
                    <td colspan="2"><input type="submit" name="send" value="Ändern" />               
                    </td>
                </tr>
             </table>
          </form>

  </tiles:put>
</tiles:insert>