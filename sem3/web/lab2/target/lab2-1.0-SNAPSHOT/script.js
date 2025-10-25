let lastScroll = 0;
const tr = document.getElementsByTagName("tr")
const header = document.querySelector('.table');
const showTableButton = document.getElementById("show-table");
const hideTableButton = document.getElementById("hide-table");
const tbody = document.getElementById("result_body");

// Переменные для карусели
let currentGraphIndex = 0;
let graphsData = [];

tbody.addEventListener("mouseover", e => {
  const row = e.target.closest("tr");
  if (!row) return;
  const trash = row.querySelector(".trash");
  if (trash) {
    trash.classList.remove("show")
    trash.classList.add("show");
  }
});

tbody.addEventListener("mouseout", e => {
  const row = e.target.closest("tr");
  if (!row) return;
  const trash = row.querySelector(".trash");
  if (trash) {
    trash.classList.remove("show");
    trash.classList.add("hide");
  }
});

tbody.addEventListener("click", e => {
  if (e.target.classList.contains("trash")) {
    e.target.closest("tr").remove();
  }
});

showTableButton.addEventListener("click", event => {
    header.classList.add("visible")
})

hideTableButton.addEventListener("click", event => {
    header.classList.remove("visible")
})

document.getElementById("mainForm").addEventListener("submit", function(event){
    event.preventDefault();
    const error = document.getElementById("error-p");

    // Получаем сырое значение X как строку
    let xInput = document.getElementById("inputX").value.trim();
    try {
        xInput = String(xInput).replace(',', '.');
    } catch (e){
        console.log("No ,")
    }



    const yValues = Array.from(document.querySelectorAll('input[name="y"]:checked')).map(cb => parseFloat(cb.value));
    const rValues = Array.from(document.querySelectorAll('input[name="r"]:checked')).map(cb => parseFloat(cb.value));

    // Передаем сырое значение xInput в checkValues
    const mistakes = checkValues(xInput, yValues, rValues);

    if(mistakes !== ""){
        error.classList.add("visible");
        error.textContent = mistakes;
    } else {
        error.classList.remove("visible");
        error.textContent = "";
        event.target.submit();
    }
});

function checkValues(xInput, yValues, rValues){
    const allowedY = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
    const allowedR = [1, 1.5, 2, 2.5, 3];
    let mistakes = "";
    console.log(xInput)

    // Проверка X (теперь работает с сырой строкой)
    if (xInput === "") {
        mistakes += "Значение X не было подано.\n";
    } else {
        try {
            const x = new Decimal(xInput);
            if (x.lessThan(-5) || x.greaterThan(3)) {
                mistakes += "Значение X должно быть в промежутке от -5 до 3.\n";
            }
        } catch (e) {
            mistakes += "Неверный формат числа X.\n";
            console.log(e)
        }
    }

    // Проверка Y
    if (yValues.length === 0) {
        mistakes += "Выберите хотя бы одно значение Y.\n";
    } else {
        for (let yValue of yValues) {
            if (!allowedY.includes(yValue)) {
                mistakes += "Неверно подано значение Y.\n";
                break;
            }
        }
    }

    // Проверка R
    if (rValues.length === 0) {
        mistakes += "Выберите хотя бы одно значение R.\n";
    } else {
        for (let rValue of rValues) {
            if (!allowedR.includes(rValue)) {
                mistakes += "Неверно подано значение R.\n";
                break;
            }
        }
    }

    return mistakes;
}

// Функции для работы с каруселью графиков
function initializeGraphCarousel() {
    const carouselContainer = document.querySelector('.carousel-container');
    if (!carouselContainer) return;

    // Создаем основной график (с буквой R)
    createMainGraph();

    // Загружаем графики из сессии (будет вызвано из JSP)
    // loadGraphsFromSession() вызывается из JSP

    // Показываем первый график
    showGraph(0);
}

function createMainGraph() {
    const carousel = document.querySelector('.carousel');
    const mainGraph = document.createElement('div');
    mainGraph.className = 'graph-item active';
    mainGraph.setAttribute('data-r', 'main');
    mainGraph.innerHTML = `
        <svg width="370" height="370" class="main-graph">
            <!-- Основная фигура -->
            <!-- Прямоугольник -->
            <rect x="92.5" y="185" width="92.5" height="92.5" fill="#D9FB60"></rect>

            <!-- Четверть круга -->
            <circle cx="185" cy="185" r="46.5" fill="#D9FB60" class="no-shadow"></circle>
            <rect class="no-shadow" x="92.5" y="138.75" width="92.5" height="46.25" fill="#F3619C"></rect>
            <polygon class="no-shadow" points="231,231 231.25,185 185,231.25" fill="#F3619C"></polygon>

            <!-- Треугольник -->
            <polygon points="185,185 231.25,185 185,231.25" fill="#D9FB60"></polygon>

            <!-- Оси -->
            <line x1="185" y1="0" x2="185" y2="370" stroke="#FFF5E1" stroke-width="1.5"></line>
            <line x1="0" y1="185" x2="370" y2="185" stroke="#FFF5E1" stroke-width="1.5"></line>

            <!-- Стрелочки -->
            <line x1="185" y1="0" x2="188" y2="10" stroke="#FFF5E1" stroke-width="1.5"></line>
            <line x1="185" y1="0" x2="182" y2="10" stroke="#FFF5E1" stroke-width="1.5"></line>

            <line x1="360" y1="182" x2="370" y2="185" stroke="#FFF5E1" stroke-width="1.5"></line>
            <line x1="360" y1="188" x2="370" y2="185" stroke="#FFF5E1" stroke-width="1.5"></line>

            <!-- Метки на осях -->
            <line x1="92.5" y1="182" x2="92.5" y2="188" stroke="#FFF5E1"></line>
            <line x1="277.5" y1="182" x2="277.5" y2="188" stroke="#FFF5E1"></line>
            <line x1="138.75" y1="182" x2="138.75" y2="188" stroke="#FFF5E1"></line>
            <line x1="231.25" y1="182" x2="231.25" y2="188" stroke="#FFF5E1"></line>

            <line x1="182" y1="277.5" x2="188" y2="277.5" stroke="#FFF5E1"></line>
            <line x1="182" y1="92.5" x2="188" y2="92.5" stroke="#FFF5E1"></line>
            <line x1="182" y1="138.75" x2="188" y2="138.75" stroke="#FFF5E1"></line>
            <line x1="182" y1="231.25" x2="188" y2="231.25" stroke="#FFF5E1"></line>

            <!-- Подписи -->
            <text x="80" y="180" font-size="12" fill="#FFF5E1">-R</text>
            <text x="130" y="180" font-size="12" fill="#FFF5E1">-R/2</text>
            <text x="235" y="180" font-size="12" fill="#FFF5E1">R/2</text>
            <text x="280" y="180" font-size="12" fill="#FFF5E1">R</text>

            <text x="190" y="95" font-size="12" fill="#FFF5E1">R</text>
            <text x="190" y="140" font-size="12" fill="#FFF5E1">R/2</text>
            <text x="190" y="230" font-size="12" fill="#FFF5E1">-R/2</text>
            <text x="190" y="280" font-size="12" fill="#FFF5E1">-R</text>

            <text x="190" y="15" font-size="14" fill="#FFF5E1">y</text>
            <text x="355" y="200" font-size="14" fill="#FFF5E1">x</text>
        </svg>
    `;
    carousel.appendChild(mainGraph);

    // Добавляем обработчик клика для основного графика
    mainGraph.querySelector('svg').addEventListener('click', handleMainGraphClick);
}

function createGraphForR(rValue) {
    const carousel = document.querySelector('.carousel');
    const graphItem = document.createElement('div');
    graphItem.className = 'graph-item';
    graphItem.setAttribute('data-r', rValue);

    // Создаем SVG с конкретным значением R
    const svgContent = createSvgWithRValue(rValue);
    graphItem.innerHTML = svgContent;

    carousel.appendChild(graphItem);

    // Добавляем обработчик клика для графика с конкретным R
    graphItem.querySelector('svg').addEventListener('click', function(event) {
        handleSpecificGraphClick(event, rValue);
    });

    return graphItem;
}

function createSvgWithRValue(rValue) {
    const halfR = rValue / 2;

    return `
        <svg width="370" height="370" class="specific-graph" data-r="${rValue}">
            <!-- Основная фигура -->
            <!-- Прямоугольник -->
            <rect x="92.5" y="185" width="92.5" height="92.5" fill="#D9FB60"></rect>

            <!-- Четверть круга -->
            <circle cx="185" cy="185" r="46.5" fill="#D9FB60" class="no-shadow"></circle>
            <rect class="no-shadow" x="92.5" y="138.75" width="92.5" height="46.25" fill="#F3619C"></rect>
            <polygon class="no-shadow" points="231,231 231.25,185 185,231.25" fill="#F3619C"></polygon>

            <!-- Треугольник -->
            <polygon points="185,185 231.25,185 185,231.25" fill="#D9FB60"></polygon>

            <!-- Оси -->
            <line x1="185" y1="0" x2="185" y2="370" stroke="#FFF5E1" stroke-width="1.5"></line>
            <line x1="0" y1="185" x2="370" y2="185" stroke="#FFF5E1" stroke-width="1.5"></line>

            <!-- Стрелочки -->
            <line x1="185" y1="0" x2="188" y2="10" stroke="#FFF5E1" stroke-width="1.5"></line>
            <line x1="185" y1="0" x2="182" y2="10" stroke="#FFF5E1" stroke-width="1.5"></line>

            <line x1="360" y1="182" x2="370" y2="185" stroke="#FFF5E1" stroke-width="1.5"></line>
            <line x1="360" y1="188" x2="370" y2="185" stroke="#FFF5E1" stroke-width="1.5"></line>

            <!-- Метки на осях -->
            <line x1="92.5" y1="182" x2="92.5" y2="188" stroke="#FFF5E1"></line>
            <line x1="277.5" y1="182" x2="277.5" y2="188" stroke="#FFF5E1"></line>
            <line x1="138.75" y1="182" x2="138.75" y2="188" stroke="#FFF5E1"></line>
            <line x1="231.25" y1="182" x2="231.25" y2="188" stroke="#FFF5E1"></line>

            <line x1="182" y1="277.5" x2="188" y2="277.5" stroke="#FFF5E1"></line>
            <line x1="182" y1="92.5" x2="188" y2="92.5" stroke="#FFF5E1"></line>
            <line x1="182" y1="138.75" x2="188" y2="138.75" stroke="#FFF5E1"></line>
            <line x1="182" y1="231.25" x2="188" y2="231.25" stroke="#FFF5E1"></line>

            <!-- Подписи с конкретными значениями -->
            <text x="80" y="180" font-size="12" fill="#FFF5E1">-${rValue}</text>
            <text x="125" y="180" font-size="12" fill="#FFF5E1">-${halfR}</text>
            <text x="230" y="180" font-size="12" fill="#FFF5E1">${halfR}</text>
            <text x="275" y="180" font-size="12" fill="#FFF5E1">${rValue}</text>

            <text x="190" y="95" font-size="12" fill="#FFF5E1">${rValue}</text>
            <text x="190" y="140" font-size="12" fill="#FFF5E1">${halfR}</text>
            <text x="190" y="230" font-size="12" fill="#FFF5E1">-${halfR}</text>
            <text x="190" y="280" font-size="12" fill="#FFF5E1">-${rValue}</text>

            <text x="190" y="15" font-size="14" fill="#FFF5E1">y</text>
            <text x="355" y="200" font-size="14" fill="#FFF5E1">x</text>
        </svg>
    `;
}

function handleMainGraphClick(event) {
    const selectedR = document.querySelectorAll('input[name="r"]:checked');
    if (selectedR.length === 0) {
        showModal("Сначала выберите радиус (R)", "info");
        return;
    }

    // Все выбранные радиусы
    const rValues = Array.from(selectedR).map(cb => parseFloat(cb.value));

    // Получаем координаты клика относительно SVG
    const svg = event.currentTarget;
    const point = svg.createSVGPoint();
    point.x = event.clientX;
    point.y = event.clientY;

    const svgPoint = point.matrixTransform(svg.getScreenCTM().inverse());

    // Центр и масштаб
    const centerX = 185;
    const centerY = 185;
    const scale = 92.5 / rValues[0]; // по первому R

    const xValue = (svgPoint.x - centerX) / scale;
    const yValue = (centerY - svgPoint.y) / scale;

    const roundedX = Math.round(xValue * 100) / 100;
    const roundedY = Math.round(yValue * 100) / 100;

    console.log(`Клик: X=${roundedX}, Y=${roundedY}, R=[${rValues.join(', ')}]`);

    // Формируем строку запроса: r=1&r=1.5&r=2 ...
    const params = new URLSearchParams();
    params.append("x", roundedX);
    params.append("y", roundedY);
    rValues.forEach(r => params.append("r", r));

    // Редиректим на контроллер с готовой строкой запроса
    window.location.href = `controller?${params.toString()}`;
}



function handleSpecificGraphClick(event, r) {
    // Получаем координаты клика относительно SVG
    const svg = event.currentTarget;
    const point = svg.createSVGPoint();
    point.x = event.clientX;
    point.y = event.clientY;

    const svgPoint = point.matrixTransform(svg.getScreenCTM().inverse());

    // Преобразуем координаты из пикселей в значения X, Y
    const centerX = 185;
    const centerY = 185;
    const scale = 92.5 / r;

    const xValue = (svgPoint.x - centerX) / scale;
    const yValue = (centerY - svgPoint.y) / scale; // Инвертируем Y

    // Округляем для точности
    const roundedX = Math.round(xValue * 100) / 100;
    const roundedY = Math.round(yValue * 100) / 100;

    console.log(`Клик на графике R=${r}: X=${roundedX}, Y=${roundedY}`);

    // Устанавливаем значение X в поле ввода
    document.getElementById('inputX').value = roundedX;

    // Снимаем все выбранные checkbox для Y
    document.querySelectorAll('input[name="y"]').forEach(checkbox => {
        checkbox.checked = false;
    });

    // Автоматически выбираем соответствующий R
    document.querySelectorAll('input[name="r"]').forEach(checkbox => {
        checkbox.checked = (parseFloat(checkbox.value) === r);
    });

    // Отправляем форму с вычисленными координатами
    sendFromGraph(roundedX, roundedY, r);
}

function sendFromGraph(x, y, r) {
    // Создаем временную форму для отправки
    const tempForm = document.createElement('form');
    tempForm.method = 'GET';
    tempForm.action = 'controller';

    // Добавляем параметры
    const xInput = document.createElement('input');
    xInput.type = 'hidden';
    xInput.name = 'x';
    xInput.value = x;
    tempForm.appendChild(xInput);

    const yInput = document.createElement('input');
    yInput.type = 'hidden';
    yInput.name = 'y';
    yInput.value = y;
    tempForm.appendChild(yInput);

    const rInput = document.createElement('input');
    rInput.type = 'hidden';
    rInput.name = 'r';
    rInput.value = r;
    tempForm.appendChild(rInput);

    // Добавляем форму на страницу и отправляем
    document.body.appendChild(tempForm);
    tempForm.submit();
}

function showGraph(index) {
    const graphs = document.querySelectorAll('.graph-item');
    const indicators = document.querySelectorAll('.carousel-indicator');

    if (graphs.length === 0) return;

    // Скрываем все графики
    graphs.forEach(graph => graph.classList.remove('active'));
    indicators.forEach(indicator => indicator.classList.remove('active'));

    // Показываем выбранный график
    if (graphs[index]) {
        graphs[index].classList.add('active');
        if (indicators[index]) {
            indicators[index].classList.add('active');
        }
        currentGraphIndex = index;
    }
}

function nextGraph() {
    const graphs = document.querySelectorAll('.graph-item');
    if (graphs.length === 0) return;

    const nextIndex = (currentGraphIndex + 1) % graphs.length;
    showGraph(nextIndex);
}

function prevGraph() {
    const graphs = document.querySelectorAll('.graph-item');
    if (graphs.length === 0) return;

    const prevIndex = (currentGraphIndex - 1 + graphs.length) % graphs.length;
    showGraph(prevIndex);
}

function addPointToGraph(x, y, r, hit) {
    // Находим или создаем график для данного R
    let graphItem = document.querySelector(`.graph-item[data-r="${r}"]`);

    if (!graphItem && r !== 'main') {
        // Создаем новый график для этого R
        graphItem = createGraphForR(r);
        updateCarouselIndicators();
    }

    const svg = graphItem ? graphItem.querySelector('svg') : null;
    if (!svg) return;

    // Преобразуем координаты в пиксели SVG
    const scale = 92.5 / r;
    const centerX = 185;
    const centerY = 185;

    const pixelX = centerX + x * scale;
    const pixelY = centerY - y * scale;

    // Создаем элемент точки
    const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    point.setAttribute('cx', pixelX);
    point.setAttribute('cy', pixelY);
    point.setAttribute('r', '3');
    point.setAttribute('fill', hit ? '#00FF00' : '#FF0000');
    point.setAttribute('stroke', '#FFFFFF');
    point.setAttribute('stroke-width', '1');
    point.setAttribute('class', 'result-point');
    point.setAttribute('data-x', x);
    point.setAttribute('data-y', y);
    point.setAttribute('data-r', r);

    // Добавляем всплывающую подсказку
    const titleElem = document.createElementNS("http://www.w3.org/2000/svg", "title");
    titleElem.textContent = `X: ${x}, Y: ${y}, R: ${r}, Попадание: ${hit ? 'Да' : 'Нет'}`;
    point.appendChild(titleElem);
    svg.appendChild(point);

    point.addEventListener('click', (event) => {
      event.stopPropagation(); // не даём клику дойти до svg
      showToast(`X: ${x}, Y: ${y}, R: ${r}, Попадание: ${hit ? "Да" : "Нет"}`);
    });

}

function updateCarouselIndicators() {
    const indicatorsContainer = document.querySelector('.carousel-indicators');
    const graphs = document.querySelectorAll('.graph-item');

    indicatorsContainer.innerHTML = '';

    graphs.forEach((_, index) => {
        const indicator = document.createElement('span');
        indicator.className = `carousel-indicator ${index === currentGraphIndex ? 'active' : ''}`;
        indicator.setAttribute('data-index', index);
        indicator.addEventListener('click', () => showGraph(index));
        indicatorsContainer.appendChild(indicator);
    });
}

// Обработчик для кнопки очистки значений
document.getElementById("clear-values").addEventListener("click", function() {
    document.getElementById("inputX").value = "";
    document.querySelectorAll('input[name="y"]:checked').forEach(checkbox => {
        checkbox.checked = false;
    });
    document.querySelectorAll('input[name="r"]:checked').forEach(checkbox => {
        checkbox.checked = false;
    });
});

async function deleteResult(id) {
    const confirm = await showModal("Удалить запись из истории?", "delete");
    if (confirm) {
        fetch(`clearResults?id=${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.status === 200) {
                // Удаляем строку из таблицы
                const row = document.querySelector(`tr[data-id="${id}"]`);
                if (row) {
                    row.remove();
                }
                // Удаляем точку с графика
                removePointFromGraph(id);
                console.log(`Result ${id} deleted successfully`);
            } else {
                throw new Error('Failed to delete result');
            }
        })
        .catch(error => {
            console.error('Error deleting result:', error);
        });
    } else {
        console.log("Удаление отменено пользователем");
    }
}

// Удаление всех результатов
async function clearAllResults() {
    const confirm = await showModal("Вы уверены, что хотите очистить всю историю?", "delete")
    if (confirm) {
        fetch('clearResults', {
            method: 'DELETE'
        })
        .then(response => {
            if (response.status === 204) {
                clearTableUI();
                clearGraphs();
                console.log('All results cleared successfully');
            } else {
                throw new Error('Failed to clear results');
            }
        })
        .catch(error => {
            console.error('Error clearing results:', error);
        });
    } else {
        console.log("Удаление отменено пользователем")
    }
}

// Удаление точки с графика по ID
function removePointFromGraph(id) {
    const point = document.querySelector(`circle[data-id="${id}"]`);
    if (point) {
        point.remove();
    }
}

// Обновляем функцию addPointToGraph для поддержки ID
function addPointToGraph(x, y, r, hit, id) {
    // Находим или создаем график для данного R
    let graphItem = document.querySelector(`.graph-item[data-r="${r}"]`);

    if (!graphItem && r !== 'main') {
        // Создаем новый график для этого R
        graphItem = createGraphForR(r);
        updateCarouselIndicators();
    }

    const svg = graphItem ? graphItem.querySelector('svg') : null;
    if (!svg) return;

    // Преобразуем координаты в пиксели SVG
    const scale = 92.5 / r;
    const centerX = 185;
    const centerY = 185;

    const pixelX = centerX + x * scale;
    const pixelY = centerY - y * scale;

    // Создаем элемент точки
    const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    point.setAttribute('cx', pixelX);
    point.setAttribute('cy', pixelY);
    point.setAttribute('r', '3');
    point.setAttribute('fill', hit ? '#00FF00' : '#FF0000');
    point.setAttribute('stroke', '#FFFFFF');
    point.setAttribute('stroke-width', '1');
    point.setAttribute('class', 'result-point');
    point.setAttribute('data-x', x);
    point.setAttribute('data-y', y);
    point.setAttribute('data-r', r);
    point.setAttribute('data-id', id); // Добавляем ID точки

    // Добавляем всплывающую подсказку
    point.setAttribute('title', `X: ${x}, Y: ${y}, R: ${r}, Попадание: ${hit ? 'Да' : 'Нет'}`);

    svg.appendChild(point);

    point.addEventListener('click', (event) => {
      event.stopPropagation(); // не даём клику дойти до svg
      showToast(`X: ${x}, Y: ${y}, R: ${r}, Попадание: ${hit ? "Да" : "Нет"}`);
    });
}

// Очистка таблицы UI
function clearTableUI() {
    // Очищаем тело таблицы
    const tbody = document.getElementById("result_body");
    if (tbody) {
        tbody.innerHTML = '';
    }

    // Очищаем строки результатов (кроме заголовка)
    const rows = document.querySelectorAll('tr[data-id]');
    rows.forEach(row => row.remove());
}

// Очистка графиков
function clearGraphs() {
    // Очищаем все точки на всех графиках
    const points = document.querySelectorAll('.result-point');
    points.forEach(point => point.remove());

    // Удаляем все графики кроме основного
    const graphs = document.querySelectorAll('.graph-item[data-r]:not([data-r="main"])');
    graphs.forEach(graph => graph.remove());

    // Обновляем индикаторы карусели
    updateCarouselIndicators();

    // Показываем основной график
    showGraph(0);
}

function showModal(message, logic) {
  return new Promise((resolve) => {
    const modal = document.getElementById('modal');
    const text = modal.querySelector('p');
    const buttonYes = document.getElementById("button-yes");
    const buttonNo = document.getElementById("button-no");

    // Сбрасываем состояния
    modal.classList.remove('active-info', 'active-delete', 'fade-out');
    text.textContent = message;

    if (logic === "info") {
      modal.classList.add('active-info');
      resolve(); // просто сообщение, без ответа
    }

    if (logic === "delete") {
      modal.classList.add('active-delete');

      // пересоздаём кнопки, чтобы не дублировались слушатели
      const newYes = buttonYes.cloneNode(true);
      const newNo = buttonNo.cloneNode(true);
      buttonYes.parentNode.replaceChild(newYes, buttonYes);
      buttonNo.parentNode.replaceChild(newNo, buttonNo);

      newYes.addEventListener("click", () => {
        fadeOutModal();
        resolve(true); // пользователь подтвердил
      });

      newNo.addEventListener("click", () => {
        fadeOutModal();
        resolve(false); // пользователь отменил
      });
    }
  });
}

function fadeOutModal() {
  const modal = document.getElementById('modal');
  modal.classList.add('fade-out');
  setTimeout(() => {
    modal.classList.remove('active-info', 'active-delete', 'fade-out');
  }, 400);
}

// Обновляем информацию о графике при переключении
    function updateGraphInfo() {
       const info = document.getElementById('graph-info');
       const activeGraph = document.querySelector('.graph-item.active');

       if (activeGraph) {
           const rValue = activeGraph.getAttribute('data-r');
           if (rValue === 'main') {
               info.textContent = 'Основной график (выберите R для отправки)';
           } else {
               info.textContent = `График для R = ${rValue} (R уже выбран)`;
           }
       }
    }

// Создаем контейнер для уведомлений, если его нет
function ensureToastContainer() {
  if (!document.getElementById('toast-container')) {
    const container = document.createElement('div');
    container.id = 'toast-container';
    document.body.appendChild(container);
  }
}

// Функция создания уведомления
function showToast(message) {
  ensureToastContainer();
  const container = document.getElementById('toast-container');
  const toast = document.createElement('div');
  toast.className = 'toast';
  toast.innerHTML = `
    ${message}
    <span class="toast-close">&times;</span>
  `;
  container.appendChild(toast);

  // Анимация появления
  setTimeout(() => toast.classList.add('show'), 50);

  // Кнопка закрытия
  toast.querySelector('.toast-close').addEventListener('click', () => {
    removeToast(toast);
  });

  // Удаление через 10 секунд
  setTimeout(() => removeToast(toast), 10000);
}

// Плавное удаление уведомления
function removeToast(toast) {
  toast.classList.remove('show');
  setTimeout(() => toast.remove(), 400);
}



