<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equive="Content-type" content="text/html; charset=UTF-8">
    <title>Редактирование еды</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Edit meals</h2>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <input type="hidden" name="id" value="${meal.id}">
        <label>Date/Time: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"></label><br><br>
        <label>Description: <input type="text" name="description" value="${meal.description}"></label><br><br>
        <label>Calories: <input type="number" name="calories" value="${meal.calories}"></label><br><br>
        <button type="submit">Save</button>
        <button type="button" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>