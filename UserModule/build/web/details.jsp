<!-- GameDetail Seite -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>

    <jsp:useBean id="gameDetails" class="app.beans.GamesDetail" scope="request"/>
    
    <c:if test="${user.userLvl > 0}">
        <c:out value="${gameDetails.title }"/>
    </c:if> 


    <c:if test="${user.userLvl == 0}">
        <jsp:forward page="index.jsp"/>
    </c:if>       
            



<form method="POST" action="GameListController">
    <input type="submit" name="send" value="zurück zur Übersicht" />
</form>


<%@include file="footer.jsp"%>
