const lista=document.getElementById("lista");
const modelo=document.getElementById("modelo");

Sortable.create(lista,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
	group: "lista-items"
});
Sortable.create(modelo,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
	group: "lista-items"
});