<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 09.11.17
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Create</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<form action="/topjava/meals" method="post">
    <input class="invisible" type="radio" name="action" value="create" checked>
    <table>
        <tr>
            <td>
                Input description:
            </td>
            <td>
                <input type="text" name="description">
            </td>
        </tr>
        <tr>
            <td>
                Input time:
            </td>
            <td>
                <input type="text" name="date">
            </td>
        </tr>
        <tr>
            <td>
                Input calories:
            </td>
            <td>
                <input type="text" name="calories">
            </td>
        </tr>
    </table>
    <input type="submit" value="Submit">
</form>
</body>
</html>
