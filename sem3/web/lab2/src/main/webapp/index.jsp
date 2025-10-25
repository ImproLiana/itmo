<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.BigDecimal" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=BBH+Sans+Hegarty&family=Russo+One&display=swap" rel="stylesheet">    
    <link rel="stylesheet" href="style.css">    
    <title>Document</title>
</head>
<body>
    <div class="modal-overlay" id="modal">
        <div class="modal">
            <p></p>
            <button id="button-ok" onclick="fadeOutModal()">OK</button>
            <button id="button-yes">Да, удалить</button>
            <button id="button-no">Нет, отмена</button>
        </div>
    </div>
    <div class="head">
        <p id="project_name">Попадание точки на график</p>
    </div>
    <div class="main_info">
        <div class="header pink" >
            <p>Выполнила:<br>Козлова Ульяна Сергеевна<br>P3220, s466221<br>Вариант: 45633</p>
        </div>
        <div class="description pink">
            <p>Выберите точку с помощью кнопок или выберите радиус и отметьте точку на графике</p>
        </div>
    </div>
    <div class="main">
       <div class="picture pink">
           <div class="carousel-container">
               <div class="graph-info" id="graph-info">
                   Основной график (выберите R для отправки)
               </div>
               <button class="carousel-prev">‹</button>
               <button class="carousel-next">›</button>
               <div class="carousel">
                   <!-- Основной график будет создан через JavaScript -->
               </div>
               <div class="carousel-indicators">
                   <!-- Индикаторы будут созданы через JavaScript -->
               </div>
           </div>
       </div>
        <div class="selector pink">
        <form id="mainForm" action="controller" method="GET">
            <div class="formX" novalidate>
                <p>Выбор X</p>
                <input type="text" style="display:none">
                <label><input autocomplete="off" oninvalid="return false;" type="text" name="x" id="inputX"></label>
            </div>
            <div class="formY">
                <p>Выбор Y</p>
                <div class="checkbox-group">
                    <label><input type="checkbox" name="y" value="-5">-5</label>
                    <label><input type="checkbox" name="y" value="-4">-4</label>
                    <label><input type="checkbox" name="y" value="-3">-3</label>
                    <label><input type="checkbox" name="y" value="-2">-2</label>
                    <label><input type="checkbox" name="y" value="-1">-1</label>
                    <label><input type="checkbox" name="y" value="0">0</label>
                    <label><input type="checkbox" name="y" value="1">1</label>
                    <label><input type="checkbox" name="y" value="2">2</label>
                    <label><input type="checkbox" name="y" value="3">3</label>
                </div>
            </div>
            <div class="formR">
                <p>Выбор R</p>
                <label><input type="checkbox" name="r" value="1">1</label>
                <label><input type="checkbox" name="r" value="1.5">1.5</label>
                <label><input type="checkbox" name="r" value="2">2</label>
                <label><input type="checkbox" name="r" value="2.5">2.5</label>
                <label><input type="checkbox" name="r" value="3">3</label>
            </div>
            <p id="error-p" style="color: red"></p>
            <button id="send" style="margin-bottom: 5px;">Отправить</button>
            </form>
            <button id="clear-values">Очистить значения</button>
        </div>
    </div>
    <button id="show-table">Показать историю</button>
    <div class="table pink">
        <button id="clear-all">Очистить всю историю</button>
        <button id="hide-table">Скрыть историю</button>
        <table class="resuts">
        <%
        ServletContext context = application; // application — это встроенная JSP-переменная для ServletContext
        List<Map<String, Object>> results = (List<Map<String, Object>>) context.getAttribute("results");
        %>

            <tr id="tr1">
                <th class="res">X</th>
                <th class="res">Y</th>
                <th class="res">R</th>
                <th class="res">Настоящее время</th>
                <th class="res">Время выполнения</th>
                <th class="res">Попадание (да/нет)</th>
                <th style="background: #F3619C"></th>
            </tr>
            <tbody id="result_body">
            </tbody>
             <%
             if (results != null && !results.isEmpty()) {
                for (Map<String, Object> res : results) {
            %>
                <tr data-id="<%= res.get("id") %>">
                    <th class="res"><%= res.get("x") %></th>
                    <th class="res"><%= res.get("y") %></th>
                    <th class="res"><%= res.get("r") %></th>
                    <th class="res"><%= res.get("currentTime") %></th>
                    <th class="res"><%= res.get("executionTime") %></th>
                    <th class="res"><%= "true".equals(res.get("hit").toString()) ? "Да" : "Нет" %></th>
                    <th style="background: #F3619C">
                        <button class="delete-row-btn trash" onclick="deleteResult(<%= res.get("id") %>)" title="Удалить эту запись" style="border: none">
                        <img src="img/bak.png">
                        </button>
                    </th
                </tr>
           <% } %>
           <% } %>
        </table>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/decimal.js@10.4.3/decimal.min.js"></script>
<script src="script.js"></script>
<script>
    // Создаем графики для уникальных R из сессии
    function loadGraphsFromSession() {
       <%
       java.util.Set<Float> uniqueR = new java.util.HashSet<>();

       if (results != null) {
           for (java.util.Map<String, Object> point : results) {
               Object rObj = point.get("r");
               if (rObj instanceof Number) {
                   uniqueR.add(((Number)rObj).floatValue());
               }
           }

           for (Float r : uniqueR) {
       %>
               createGraphForR(<%= String.format(Locale.ROOT, "%.2f", (double) r) %>);
       <%
           }
       }
       %>
       updateCarouselIndicators();
    }

    // Функция для отрисовки всех точек из сессии при загрузке страницы
    function drawPointsFromSession() {
        <%
        if (results != null) {
            for (java.util.Map<String, Object> point : results) {
                Object xObj = point.get("x");
                Object yObj = point.get("y");
                Object rObj = point.get("r");
                Object hitObj = point.get("hit");
                Object idObj = point.get("id");

                double x = 0;
                if (xObj instanceof java.math.BigDecimal) {
                    x = ((java.math.BigDecimal)xObj).doubleValue();
                } else if (xObj instanceof Number) {
                    x = ((Number)xObj).doubleValue();
                }

                float y = 0;
                if (yObj instanceof Number) {
                    y = ((Number)yObj).floatValue();
                }

                float r = 0;
                if (rObj instanceof Number) {
                    r = ((Number)rObj).floatValue();
                }

                boolean hit = false;
                if (hitObj instanceof Boolean) {
                    hit = (Boolean)hitObj;
                }

                long id = 0;
                if (idObj instanceof Number) {
                    id = ((Number)idObj).longValue();
                }
        %>
                addPointToGraph(<%= String.format(Locale.ROOT, "%.2f", (double) x) %>,
                                <%= String.format(Locale.ROOT, "%.2f", (float) y) %>,
                                <%= String.format(Locale.ROOT, "%.2f", (float) r) %>,
                                <%= hit %>,
                                <%= id %>);
        <%
            }
        }
        %>
    }

    // Модифицируем функцию showGraph для обновления информации
    const originalShowGraph = showGraph;
    showGraph = function(index) {
        originalShowGraph(index);
        updateGraphInfo();
    };

    // Инициализация при загрузке страницы
    document.addEventListener('DOMContentLoaded', function() {
        initializeGraphCarousel();
        loadGraphsFromSession();
        drawPointsFromSession();

        // Добавляем обработчики для кнопок карусели
        document.querySelector('.carousel-prev')?.addEventListener('click', prevGraph);
        document.querySelector('.carousel-next')?.addEventListener('click', nextGraph);

        // Обработчик для кнопки очистки всей истории
        document.getElementById('clear-all')?.addEventListener('click', clearAllResults);
    });
</script>
</html>