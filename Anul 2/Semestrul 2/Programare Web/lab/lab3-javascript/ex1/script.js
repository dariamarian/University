var lista1 = document.getElementById("lista1");
var lista2 = document.getElementById("lista2");

lista1.addEventListener("dblclick", function () {
    var element = lista1.options[lista1.selectedIndex];
    lista2.appendChild(element);
    lista1.removeChild(element);
});

lista2.addEventListener("dblclick", function () {
    var element = lista2.options[lista2.selectedIndex];
    lista1.appendChild(element);
    lista2.removeChild(element);
});