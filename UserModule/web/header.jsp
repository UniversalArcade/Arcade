<!-- Start HTML Dokument und Einbindung der Navigation-->

<%@include file="includes.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="description" content="Mafiatorte" />
        <title>Mafiatorte</title>
        <link href="_stylesheet.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div class="holder">
            <div class="top"></div>
            <div class="navi">
                
                <jsp:useBean id="user" class="app.beans.User" scope="session"/>
                
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
                                    <a href="games.jsp">Spiele Verwalten</a>
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