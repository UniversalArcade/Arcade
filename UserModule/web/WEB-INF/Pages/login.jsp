<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="customer" class="app.beans.Costumer" scope="request" />

<tiles:insert template="Layout/layout.jsp">
  <tiles:put name="title" value="Willkommen"/>
  
  <tiles:put name="body">

       

        <p>
            Login
        </p>

        <form method="POST" action="LoginController">
            <table width="100%" border="0" cellspacing="1" cellpadding="3">
                <tr>
                    <td width="15%">Mail</td>
                    <td width="85%"><input type="text" name="mail" value="${customer.mail}" size="30"/>
                        <c:out value="${customer.errors['mail']}"/>
                    </td>

                </tr>
                <tr>
                    <td width="15%">Passwort</td>
                    <td width="85%">
                        <input type="password" name="password" value="${customer.password}" size="30"/>
                        <c:out value="${customer.errors['password']}"/>
                    </td>
                </tr>   
                <tr>
                    <td colspan="2"><input type="submit" name="send" value="anmelden" />
                        ... noch keinen Account? <a href="RegistrationController">registrieren</a>
                    </td>
                </tr>
             </table>
          </form>

  </tiles:put>
</tiles:insert>


