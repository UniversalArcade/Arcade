<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>

<jsp:useBean id="customer" class="app.beans.Costumer" scope="request" />

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
                ... noch keinen Account? <a href="register.jsp">registrieren</a>
            </td>
        </tr>
     </table>
  </form>

<%@include file="footer.jsp"%>
