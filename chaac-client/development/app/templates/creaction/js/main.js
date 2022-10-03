const lista=document.getElementById("lista");


const modelo=document.getElementById("modelo");


const listaComponentes=Sortable.create(lista,{
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
const listaModelo=Sortable.create(modelo,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
	group: "lista-items",
	onAdd: function(Sortable){
		if (Sortable.getElementById=='pool'){
			console.log("entro una pool")
		}
	}

});