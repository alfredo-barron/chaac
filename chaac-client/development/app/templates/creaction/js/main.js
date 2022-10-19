document.addEventListener("DOMContentLoaded", function(){
	var spacing_x = 40;
	var spacing_y = 100;
	var rightcard = false;
    var tempblock;
    var tempblock2;
// Initialize Flowy
	flowy(document.getElementById("canvas"), onGrab, onRelease,onSnapping,spacing_x,spacing_y);
		function addEventListenerMulti(type, listener, capture, selector) {
        	var nodes = document.querySelectorAll(selector);
        	for (var i = 0; i < nodes.length; i++) {
            	nodes[i].addEventListener(type, listener, capture);
        	}
    	}
    	function onSnapping(drag, first,parent){
    		var grab = drag.querySelector(".grabme");
        	grab.parentNode.removeChild(grab);
        	var blockin = drag.querySelector(".blockin");
        	blockin.parentNode.removeChild(blockin);
        	if(drag.querySelector('.blockelemtype').value=="1"){
        		drag.innerHTML+="<div class='blockyleft noselect'> <div><h2 class='noselect' >cenote</h2></div> <nav><ul><li><img class='icon' src='img/icons/suelto.png'> </li><li><button class='iconbutton'> <img class='icon' src='img/icons/editar.png'></button></li></ul> </div>";
        	}
        	if(drag.querySelector('.blockelemtype').value=="2"){
        		drag.innerHTML+="<div class='blockyleft noselect'> <div><img class='icon' src='img/icons/suelto.png'></div><h2 class='blocktitle no-seleccionable' >bin</h2> </div>";
        	}
        	if(drag.querySelector('.blockelemtype').value=="3"){
        		drag.innerHTML+="<div class='blockyleft noselect'> <div><img class='icon' src='img/icons/suelto.png'></div><h2 class='blocktitle' >ball</h2> </div>";
        	}
        	return true;
    	}
		function onGrab(block){
			block.classList.add("blockdisabled");
		 	tempblock2 = block;
		}
		function onRelease(){
			if (tempblock2) {
            	tempblock2.classList.remove("blockdisabled");
        	}
		}
		function onSnap(block, first, parent){
			 return true;
		}
		function onRearrange(block, parent){
		// When a block is rearranged
		}
});