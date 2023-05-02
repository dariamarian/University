var lista1 = $("#lista1");
var lista2 = $("#lista2");

lista1.on("dblclick", function() {
    var element = lista1.find("option:selected");
    lista2.append(element);
    element.parent().val(lista2.attr("id"));
});

lista2.on("dblclick", function() {
    var element = lista2.find("option:selected");
    lista1.append(element);
    element.parent().val(lista1.attr("id"));
});
