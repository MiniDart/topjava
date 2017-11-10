<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 08.11.17
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Meals list</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<c:set var="list" value="${requestScope.mealsList}"/>
<table>
    <tr>
        <th>Время</th>
        <th>Название</th>
        <th>Калории</th>
        <th></th>
    </tr>
    <c:forEach var="item" items="${list}">
        <tr <c:if  test="${item.exceed}">class="exceeded"</c:if>>
            <javatime:format value="${item.dateTime}" style="MM" var="formatedDate" />
            <td>
                <c:out value="${formatedDate}"/>
            </td>
            <td>
        <c:out value="${item.description}"/>
            </td>
            <td>
                <c:out value="${item.calories}"/>
            </td>
            <td><a href="edit?id=<c:out value="${item.id}"/>">Edit</a> <a href="delete?id=<c:out value="${item.id}"/>">Delete</a> </td>
        </tr>
    </c:forEach>
</table>
<a href="create"><button>Create meal</button></a>
</body>
</html>
