<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 10.11.17
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Edit</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<c:set var="meal" value="${requestScope.meal}"/>
<form action="/topjava/meals" method="post" accept-charset="UTF-8">
    <input class="invisible" type="radio" name="action" value="update" checked>
    <input class="invisible" type="radio" name="id" value="<c:out value="${meal.id}"/>" checked>
    <table>
        <tr>
            <td>
                Edit description:
            </td>
            <td>
                <input type="text" name="description" value="<c:out value="${meal.description}"/>">
            </td>
        </tr>
        <tr>
            <td>
                Edit time:
            </td>
            <td>
                <javatime:format value="${meal.dateTime}" style="MM" var="formatedDate" />
                <input type="text" name="date" value="<c:out value="${formatedDate}"/>">
            </td>
        </tr>
        <tr>
            <td>
                Edit calories:
            </td>
            <td>
                <input type="text" name="calories" value="<c:out value="${meal.calories}"/>">
            </td>
        </tr>
    </table>
    <input type="submit" value="Submit">
</form>
</body>
</html>
