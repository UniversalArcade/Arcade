<!-- Startseite mit Begr��ungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>


<p>
    TODO : �bersicht Games, wenn User noch kein Game angelegt hat, redirect zu newGame.jsp
</p>

<form method="POST" action="newGame.jsp">
    <input type="submit" name="send" value="neues Game anlegen" />
</form>


<%@include file="footer.jsp"%>
