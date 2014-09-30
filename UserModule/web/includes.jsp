<!-- Initialisierung von global benötigten Komponenten -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="SQLCon" driver="com.mysql.jdbc.Driver"
                   url="${initParam.SQL_URL}"
                   user="${initParam.SQL_User}"  password="${initParam.SQL_Password}"/>

