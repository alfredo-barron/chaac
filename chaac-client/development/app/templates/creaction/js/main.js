const lista=document.getElementById("lista");


const modelo=document.getElementById("modelo");


Sortable.create(lista,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",

	group: "lista-items",


	group: {
		name:"lista-items",
		pull: "clone",
		put: false
	}
	,
	 sort: false
});
Sortable.create(modelo,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
	group: "lista-items"

});