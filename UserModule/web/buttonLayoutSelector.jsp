<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<select name="button${buttonID}" size="1">                
    <option value="unused"> Unbenutzt </option>
    <c:forEach var="device" items="${buttons.devices}" varStatus="bb">
        
        <optgroup label="${device}">
            <c:forEach var="item" items="${buttons.buttons[device]}" varStatus="bb">
                <option value="${item}"> 
                    <c:out value="${item}" /> 
                </option>
            </c:forEach>
                    
                
        </optgroup>
    </c:forEach>
</select>