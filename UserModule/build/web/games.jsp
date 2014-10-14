<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>

<jsp:useBean id="gamesList" class="app.beans.GamesList" scope="request"/>

    <c:if test="${user.userLvl > 0}">
       <c:forEach var="item" items="${gamesList.games}">

            <p>
                <a href="GameDetailController?gameID=${item.gameID}"> <c:out value="${item.title}" /> </a>
            </p>
        </c:forEach>     
    </c:if>
    
    <c:if test="${user.userLvl == 0}">
        <jsp:forward page="index.jsp"/>
    </c:if>       
            



<form method="POST" action="newGame.jsp">
    <input type="submit" name="send" value="neues Game anlegen" />
</form>


<%@include file="footer.jsp"%>
