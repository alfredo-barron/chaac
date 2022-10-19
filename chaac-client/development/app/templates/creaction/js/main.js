document.addEventListener("DOMContentLoaded", function(){
	var spacing_x = 10;
	var spacing_y = 10;
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
    		console.log(drag,"es el hijo","primero:",first);
    		console.log(parent,"es el padre","primero:",first);
    		var grab = drag.querySelector(".grabme");
        	grab.parentNode.removeChild(grab);
        	var blockin = drag.querySelector(".blockin");
        	blockin.parentNode.removeChild(blockin);
        	if(first){
        		if(drag.querySelector('.blockelemtype').value=="1"){
        			drag.innerHTML+="<div class='blockyleft noselect'> <div><h2 class='noselect' >Schema</h2></div> <nav><ul><li><img class='icon' src='img/icons/suelto.png'> </li><li><button class='iconbutton'> <img class='icon' src='img/icons/editar.png'></button></li></ul> </div>";
        		
        		}
        		if(drag.querySelector('.blockelemtype').value=="2"){
        			drag.innerHTML+="<div class='blockyleft noselect'> <div><h2 class='noselect' >Cenote</h2></div> <nav><ul><li><img class='icon' src='img/icons/suelto.png'> </li><li><button class='iconbutton'> <img class='icon' src='img/icons/editar.png'></button></li></ul> </div>";
        		}
        		return true;
        	}else{
        		if(drag.querySelector('.blockelemtype').value=="2"){
        			drag.innerHTML+="<div class='blockyleft noselect'> <div><h2 class='noselect' >Cenote</h2></div> <nav><ul><li><img class='icon' src='img/icons/suelto.png'> </li><li><button class='iconbutton'> <img class='icon' src='img/icons/editar.png'></button></li></ul> </div>";
        		}
        		if(drag.querySelector('.blockelemtype').value=="3"){
        			drag.innerHTML+="<div class='blockyleft noselect'> <div><h2 class='noselect' >Bin</h2></div> <nav><ul><li><img class='icon' src='img/icons/suelto.png'> </li><li><button class='iconbutton'> <img class='icon' src='img/icons/editar.png'></button></li></ul> </div>";
        		}
        		if(drag.querySelector('.blockelemtype').value=="1"&&parent.querySelector('.blockelemtype').value=="1"){
        			return false;
        		}else if(drag.querySelector('.blockelemtype').value=="2"&&parent.querySelector('.blockelemtype').value=="2"){
        			return false;
        		}else if(drag.querySelector('.blockelemtype').value=="3"&&parent.querySelector('.blockelemtype').value=="3"){
        			return false;
        		}else if(drag.querySelector('.blockelemtype').value=="3"&&parent.querySelector('.blockelemtype').value=="1"){
        			return false;
        		}else if(drag.querySelector('.blockelemtype').value=="2"&&parent.querySelector('.blockelemtype').value=="3"){
        			return false;
        		}else if(drag.querySelector('.blockelemtype').value=="1"&&parent.querySelector('.blockelemtype').value=="3"){
        			return false;
        		}else if(drag.querySelector('.blockelemtype').value=="1"&&parent.querySelector('.blockelemtype').value=="2"){
        			return false;
        		}else return true;
        	}
        	
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
			if(block.querySelector('.blockelemtype').value=="1"&&parent.querySelector('.blockelemtype')=="1"){
        		return false;
        	}else
			return true;
		}
		function onRearrange(block, parent){
		}
		document.getElementById("removeblock").addEventListener("click", function(){
 			flowy.deleteBlocks();
}		);
});