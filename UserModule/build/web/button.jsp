<%-- 
    Document   : button
    Created on : 02.10.2014, 10:56:59
    Author     : Martin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id="buttons" class="app.beans.ButtonLayout" scope="page"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
            <select name="button0" size="1">                
                <c:forEach var="device" items="${buttons.devices}" varStatus="bb">
                    <optgroup label="${device}">
                        <c:forEach var="item" items="${buttons.buttons[device]}" varStatus="bb">
                            <option value="s"> <c:out value="${item}" /> </option>
                        </c:forEach>
                    </optgroup>
                </c:forEach>
            </select>
        
    </body>
</html>
