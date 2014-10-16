
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="app.beans.User" scope="session"/>


<ul>
                     
        <c:if test="${user.userLvl == 0}">

                <li>
                    <p>
                        <a href="LoginController">Login</a>
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
                <li>
                    <p>
                        <a href="profil.jsp">Profil</a>
                    </p>
                </li>

                    <c:if test="${user.userLvl >= 200}">

                        <li>
                            <p>
                                <a href="admin.jsp">User Verwalten</a>
                            </p>
                        </li>

                    </c:if>

                 <li>
                    <p>
                        <a href="LoginController?action=logout">Logout</a>
                    </p>
                </li>

    </c:if>             





    <c:if test="${user.userLvl > 0}">



    </c:if>            


    </ul>      
