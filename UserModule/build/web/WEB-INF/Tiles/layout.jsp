<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- START alt include -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="SQLCon" driver="com.mysql.jdbc.Driver"
                   url="${initParam.SQL_URL}"
                   user="${initParam.SQL_User}"  password="${initParam.SQL_Password}"/>

<jsp:useBean id="user" class="app.beans.User" scope="session"/>
<jsp:useBean id="game" class="app.beans.Game" scope="session"/>

<!-- ENDE alt Include -->


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>




<!-- START alte header -->

<!DOCTYPE html>
<html>
    <head>
        <title> <tiles:getAsString name="title" /> </title>
        <meta name="description" content="Newschool Arcade" />
        <link href="_stylesheet.css" rel="stylesheet" type="text/css" />
        <tiles:getAsString name="dependence" ignore="true"/>
    </head>
    <body  <tiles:getAsString name="bodyAttr" ignore="true"/> >
        <div class="holder">
            <div class="top"></div>
            <div class="navi">
                
                
                
                <ul>
                     
                    <c:if test="${user.userLvl == 0}">
                    
                            <li>
                                <p>
                                    <a href="index.jsp">Login</a>
                                </p>
                            </li>
                            <li>
                                <p>
                                    <a href="register.jsp">Registrieren</a>
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

   
            </div>
            <div class="pad">

<!-- ENDE alte Header -->

        <tiles:getAsString name="body"/>

<!-- START alte footer -->

       </div>
                    <div class="basket">
                    
                    </div>
                <div class="footer"></div>
            </div> 
    </body>
</html>