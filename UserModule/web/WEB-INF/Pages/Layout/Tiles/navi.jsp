
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="app.beans.User" scope="session"/>


<ul>
                     
        <c:if test="${user.userLvl == 0}">

                <li>
                    <p>
                        <a href="login">Login</a>
                    </p>
                </li>
                <li>
                    <p>
                        <a href="RegistrationController">Registrieren</a>
                    </p>
                </li>

        </c:if>


    <c:if test="${user.userLvl > 0}">

                <li>
                    <p>
                        <a href="GameListController">Spiele Verwalten</a>
                    </p>
                </li>
<<<<<<< HEAD
               
                <!--
               <li>
                    <p>
                        <a href="ProfilController">Profil</a>
                    </p>
                </li> 
               -->
=======
                
>>>>>>> 22753a304fc8167a8b93e74d03b4ea6bb35deac1

                    <c:if test="${user.userLvl >= 200}">

                        <li>
                            <p>
                                <a href="admin.jsp">User Verwalten</a>
                            </p>
                        </li>

                    </c:if>

                 <li>
                    <p>
                        <a href="login?action=logout">Logout</a>
                    </p>
                </li>

    </c:if>             





    <c:if test="${user.userLvl > 0}">



    </c:if>            


    </ul>      
