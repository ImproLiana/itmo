let link = `https://se.ifmo.ru/~s466221/`;
let proxy = `proxy.php`;

function getLink(x, y, r){
    return link + proxy + `?x=${x.value}&y=${y.value.replace(/,/g, ".")}&r=${r.value}`;
}