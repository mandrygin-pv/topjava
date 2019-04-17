<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equive="Content-type" content="text/html; charset=UTF-8">
    <title>Список еды</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<section>
    <table border="1">
        <tr>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan="2">Action</th>
        </tr>
        <c:forEach var="meal" items="${meals}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color: ${meal.excess ? 'red' : 'green'};">
                <td><c:out value="${TimeUtil.dateFormat(meal.dateTime)}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="meals?action=add">Add meal</a></p>
</section>
</body>
</html>