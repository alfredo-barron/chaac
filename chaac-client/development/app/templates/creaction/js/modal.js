var cerrar= document.querySelectorAll(".close")[0];
let abrir= document.querySelectorAll(".editar")[0];
var modal= document.querySelectorAll(".modal")[0];
var modalC= document.querySelectorAll(".modal-container")[0];

abrir.addEventListener("click",function(e){
	e.preventDefault();
	modalC.style.opacity="1";
	modalC.style.visibility="visible";
	modal.classList.toggle("modal-close");
});
cerrar.addEventListener("click",function(){
	e.preventDefault();
	modalC.style.opacity="0";
	modalC.style.visibility="hidden";
	modal.classList.toggle("modal-close");
});