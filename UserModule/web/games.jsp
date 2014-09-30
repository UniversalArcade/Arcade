<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>


<p>
    TODO : Übersicht Games
</p>

<form method="POST" action="newGame.jsp">
    <input type="submit" name="send" value="neues Game anlegen" />
</form>


<%@include file="footer.jsp"%>
