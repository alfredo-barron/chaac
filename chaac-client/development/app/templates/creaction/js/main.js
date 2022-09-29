const lista=document.getElementById("lista");
<<<<<<< HEAD
=======
const modelo=document.getElementById("modelo");
>>>>>>> 9ac30bee6934ac462e0cad59ab59cebfaaffcaa9

Sortable.create(lista,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
<<<<<<< HEAD
	group: "lista-items"
<<<<<<< HEAD
=======
=======
	group: {
		name:"lista-items",
		pull: "clone",
		put: false
	}
>>>>>>> e8e6f98 (modificaciones a la ramma chaac_client_2)
});
Sortable.create(modelo,{
	chosenClass: "seleccion",
	ghostClass: "fantasma",
	group: "lista-items"
>>>>>>> 9ac30bee6934ac462e0cad59ab59cebfaaffcaa9
});