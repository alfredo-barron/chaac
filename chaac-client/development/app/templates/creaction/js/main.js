const lista=document.getElementById("lista");

Sortable.create(lista,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
	group: "lista-items"
});