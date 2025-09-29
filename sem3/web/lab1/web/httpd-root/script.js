const sendButton = document.querySelector(".sendButton");
const error = document.getElementById("error");
const buttonsX = document.querySelectorAll(".buttonX");
const tbody = document.getElementById("results-body");
const clear_form = document.getElementById("clear_form");
const clear_table = document.getElementById("clear_table");
const inputY = document.getElementById("inputY");
const radioR = document.querySelectorAll(".radioR");
let resultsHistory = [];

let link = `https://se.ifmo.ru/~s466221/`;
let proxy = `proxy.php`;

function getLink(x, y, r){
    return link + proxy + `?x=${x}&y=${y.toString()}&r=${r}`;
}

const valuesX = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]
const valueR = [1, 2, 3, 4, 5]
function checkValues(x, y, r){
    if (!valuesX.includes(x)){
        error.classList.remove("error-false");
        error.textContent = "Неверно подано значение X"
        error.classList.add("error-true");
        return false;
    } if (y.lessThan(-3) || y.greaterThan(5)){
        error.classList.remove("error-false");
        error.textContent = "Неверно подано значение Y"
        error.classList.add("error-true");
        return false;
    }  if (!y.isFinite()) {
              error.classList.remove("error-false");
              error.textContent = "Отсутствует значение У"
              error.classList.add("error-true");
              return false;
    } if (!valueR.includes(r)){
        error.classList.remove("error-false");
        error.textContent = "Неверно подано значение R"
        error.classList.add("error-true");
        return false;
    } else {
        return true;
    }
}


let selectedX;
buttonsX.forEach(button => {
    button.addEventListener("click", (event) => {
        buttonsX.forEach(btn => btn.classList.remove("activeX"));
        selectedX = parseFloat(event.target.value);
        event.target.classList.add("activeX");
    })
});

clear_form.addEventListener("click", (event) => {
    selectedX = null;
    buttonsX.forEach(button => {
        button.classList.remove("activeX")
    })
    inputY.value = '';
    radioR.forEach(radio => {
        radio.checked = false;
    })
});

clear_table.addEventListener("click", (event) => {
    resultsHistory =[];
    localStorage.clear();
    tbody.innerHTML = ``
})

function sendRequest(x, y, r) {
      const url = getLink(x, y, r);

      const startTime = performance.now();

      fetch(url, {
        method: "GET"
      })
      .then(response => response.json())
      .then(result => {
        const endTime = performance.now();
        result.execTime = (endTime - startTime).toFixed(2) + " ms";

        saveResponseToLocalStorage(result);
        showResponse(result);
      })
      .catch(error => {
        console.error("Ошибка:", error);
      });
}

function showResponse(result){
    const tr = document.createElement("tr");
    tr.innerHTML = `
    <td class="res">${result.x}</td>
    <td class="res">${result.y}</td>
    <td class="res">${result.r}</td>
    <td class="res">${result.time}</td>
    <td class="res">${result.execTime}</td>
    <td class="res">${result.hit ? "ДА" : "НЕТ"}</td>
    `;
    tbody.insertBefore(tr, tbody.firstChild);
}

function saveResponseToLocalStorage(result){
    resultsHistory.push(result);
    localStorage.setItem("history", JSON.stringify(resultsHistory));
}

function loadFromLocalStorage() {
    const history = JSON.parse(localStorage.getItem("history")) || [];
    resultsHistory = history;

    history.forEach(result => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td class="res">${result.x}</td>
            <td class="res">${result.y}</td>
            <td class="res">${result.r}</td>
            <td class="res">${result.time}</td>
            <td class="res">${result.execTime || "-"}</td>
            <td class="res">${result.hit ? "ДА" : "НЕТ"}</td>
        `;
        tbody.appendChild(tr);
    });
}



sendButton.addEventListener("click", (event) => {
    event.preventDefault();

    const x = selectedX;
    const y = new Decimal(document.getElementById("inputY").value.trim());
    const r = parseFloat(document.querySelector("input[type='radio']:checked")?.value);

    if (checkValues(x, y, r)){
        error.classList.remove("error-true");
        error.classList.add("error-false");
        sendRequest(x, y, r);
    }
});

window.onload = loadFromLocalStorage;




