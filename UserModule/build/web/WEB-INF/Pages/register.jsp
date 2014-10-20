<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="customer" class="app.beans.Costumer" scope="request"/>

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="registrieren"/>
  <tiles:put name="body">

<c:choose>
    <c:when test="${!customer.registrationComplete}">

    <p>
        Registrieren
    </p>

    <form method="POST" action="RegistrationController">
        <table width="100%" border="0" cellspacing="1" cellpadding="3">
            <tr>
                <td width="15%">Mail</td>
                <td width="85%"><input type="text" name="mail" value="${customer.mail}" size="30"/>
                    <c:out value="${customer.errors['mail']}"/>
                    <a onmouseover="InfoBoxAnzeigen(event,'Geben Sie hier Ihre Email ein. Es funktioniert hier nur Ihre @haw-hamburg.de Adresse. <br>Bsp.: Max.Mustermann@haw-hamburg.de',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
                </td>

            </tr>
            <tr>
                <td width="15%">Passwort</td>
                <td width="85%">
                    <input type="password" name="password" value="${customer.password}" size="30"/>
                    <c:out value="${customer.errors['password']}"/>
                     <a onmouseover="InfoBoxAnzeigen(event,'Das Passwort muss zwischen <br>6-30 Zeichen lang sein und mindestens ein Sonderzeichen und eine Zahl enthalten.',20,-30);"
                   accesskey=""onmouseout="InfoBoxAusblenden();" href="javascript:void(0)">?</a>
                </td>
            </tr>
             <tr>
                <td width="15%">Passwort widerholen</td>
                <td width="85%">
                    <input type="password" name="passwordWDH" value="${customer.passwordWDH}" size="30"/>
                    <c:out value="${customer.errors['passwordWDH']}"/>
                </td>
            </tr>   
            <tr>
                <td colspan="2"><input type="submit" name="send" value="Abschicken" /></td>
            </tr>
         </table>
      </form>

    </c:when>
    <c:otherwise>
        Du hast dich erfolgreich registriert, bitte mail bestätigen 
    </c:otherwise>
</c:choose>

  </tiles:put>
</tiles:insert>