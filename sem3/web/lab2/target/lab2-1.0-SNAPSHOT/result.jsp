<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<link href="https://fonts.googleapis.com/css2?family=BBH+Sans+Hegarty&family=Russo+One&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    </head>
    <title>Результаты</title>
<body>
<p id="project_name">Результаты проверки:</p>

<div class="pink visible">
<table border="1">
  <tr><th class="res">X</th><th class="res">Y</th><th class="res">R</th><th class="res">Попадание</th><th class="res">Настоящее время</th><th class="res">Время выполнения</th></tr>
  <%
    java.util.List<java.util.Map<String, Object>> results =
            (java.util.List<java.util.Map<String, Object>>) request.getAttribute("results");

    if (results != null) {
        for (java.util.Map<String, Object> r : results) {
  %>
        <tr>
            <td class="res"><%= r.get("x") %></td>
            <td class="res"><%= r.get("y") %></td>
            <td class="res"><%= r.get("r") %></td>
            <td class="res"><%= (Boolean) r.get("hit") ? "Да" : "Нет" %></td>
            <td class="res"><%= r.get("currentTime") %></td>
            <td class="res"><%= r.get("executionTime") %></td>
        </tr>
  <%
        }
    }
  %>
</table>
</div>

<form action="controller" method="GET" style="display: inline;">
    <button type="submit">Назад</button>
</form>
</body>
</html>
