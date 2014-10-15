<!-- Startseite mit Begrüßungstext -->
<!-- Jede Contentseite bindet header und footer ein -->

<%@include file="header.jsp"%>

         <p>
           testupload
         </p>
         
         <form action="GameManagerController" method="post" enctype="multipart/form-data">
            <input type="file" name="file" />
                <c:out value="${game.errors['gameID']}"/>    
                <br />
            <input type="submit" value="Upload Game" />
         </form>

<%@include file="footer.jsp"%>
