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
//hange te content to elemt and drag and drop
    	function onSnapping(drag, first,parent){
    		console.log(drag,"es el hijo","primero:",first);
    		console.log(parent,"es el padre","primero:",first);
    		var grab = drag.querySelector(".grabme");
        	grab.parentNode.removeChild(grab);
        	var blockin = drag.querySelector(".blockin");
        	blockin.parentNode.removeChild(blockin);
        	drag.classList.remove("blockelem-2");
        	drag.classList.add("blockelem");
        	if(first){
        		if(drag.querySelector('.blockelemtype').value=="1"){
        			drag.innerHTML+="<div class='blockyleft noselect'>\
					 	<div><h2 class='noselect' >Schema</h2>\
						</div>\
					  	<nav>\
					  		<ul>\
					  		<li><img class='icon' src='img/icons/suelto.png'> </li>\
							</ul>\
					</div>"; 
				}
				document.getElementById("Cenote").classList.add("create-flowy");
        		document.getElementById("Bin").classList.add("create-flowy");
        		return true;
        	}else{
        		if(drag.querySelector('.blockelemtype').value=="2"){
        			drag.innerHTML+="<div class='blockyleft noselect'>\
					<div><h2 class='noselect' >Cenote</h2></div>\
					<nav>\
						<ul>\
							<li><img class='icon' src='img/icons/suelto.png'> </li>\
							<li><button class='iconbutton editar'> <img class='icon' src='img/icons/editar.png'></button></li>\
							</ul>\
					</div>";
        		}
        		if(drag.querySelector('.blockelemtype').value=="3"){
        			drag.innerHTML+="<div class='blockyleft noselect'>\
						<div><h2 class='noselect' >Bin</h2></div>\
						<nav>\
							<ul>\
								<li><img class='icon' src='img/icons/suelto.png'> </li>\
								<li><button class='iconbutton editar'> <img class='icon' src='img/icons/editar.png'></button></li>\
							</ul>\
						</div>";
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
        		}else{ 
        			return true;
        		}
        	}
        	
    	}
		// when grab a elemet
		function onGrab(block){
			block.classList.add("blockdisabled");
		 	tempblock2 = block;
		}
		//reorganize the elemets
		function onRelease(){
			console.log(tempblock2);
			if (tempblock2) {
            	tempblock2.classList.remove("blockdisabled");
        	}
		}
		//metodos para dectectar click
		var beginTouch = function (event) {
			aclick = true;
			noinfo = false;
			if (event.target.closest(".create-flowy")) {
				noinfo = true;
			}
		}
		var checkTouch = function (event) {
			aclick = false;
		}
		addEventListenerMulti("touchstart", beginTouch, false, ".block");
		var aclick = false;
		var noinfo = false;
		var isSchema=false;
		var isCenote=false;
		var isBin=false;
		//insertar formularios
		var doneTouch = function (event) {
			if (event.type === "mouseup" && aclick && !noinfo) {
			  if (!rightcard && event.target.closest(".block") && !event.target.closest(".block").classList.contains("dragging")) {
					tempblock = event.target.closest(".block");
					rightcard = true;
					tempblock.classList.add("selectedblock");
					console.log(tempblock)
					var editar=document.getElementById("propwrap");
					var propiedades=document.getElementById("properties");
					var form=document.getElementById("form");
					form.removeChild(document.getElementById("formulario"));
					if(tempblock.querySelector('.blockelemtype').value=="1"){
						propiedades.classList.add("expanded");
            			editar.classList.add("itson");
						form.innerHTML+="<div id='formulario'>\
						<p class='header2'>Edit Schema</p>\
						</div>";
						isSchema=true;
					}
					if(tempblock.querySelector('.blockelemtype').value=="2"){
						propiedades.classList.add("expanded");
            			editar.classList.add("itson");
						form.innerHTML+="<div id='formulario'>\
							<p class='header2'>Edit Cenote</p>\
							<form>\
							<ul>\
								<li>\
    								<label for='id_cenote'>id:</label>\
    								<input type='text' id='id_cenote' name='id_cenote'>\
  								</li>\
  								<li>\
    								<label for='name_cenote'>Nombre:</label>\
    								<input type='text' id='name_cenote' name='cenote_name'>\
  								</li>\
  								<li>\
    								<label for='image_cenote'>image:</label>\
    								<input type='text' id='image_cenote' name='cenote_image'>\
  								</li>\
  								<li>\
								  <label for='network'>network:</label>\
								  <input type='text' id='network' name='network'>\
  								</li>\
								<li>\
								  <label for='port'>public port:</label>\
								  <input type='integer' id='port' name='port'>\
  								</li>\
								<li>\
								  <label for='distribuidor'>distribuidor:</label>\
								  <input type='text' id='distribuidor' name='distribuidor'>\
  								</li>\
 							</ul>\
							\
						</div>";
						isSchema=true;
					}
					if(tempblock.querySelector('.blockelemtype').value=="3"){
						propiedades.classList.add("expanded");
            			editar.classList.add("itson");
						form.innerHTML+="<div id='formulario'>\
						<p class='header2'>Edit Cenote</p>\
						<form>\
						<ul>\
							<li>\
								<label for='id_bin'>id:</label>\
								<input type='text' id='id_bin' name='id_bin'>\
							  </li>\
							  <li>\
								<label for='name_bin'>Nombre:</label>\
								<input type='text' id='name_bin' name='bin_cenote'>\
							  </li>\
							  <li>\
								<label for='host_id'>host id:</label>\
								<input type='text' id='host_id' name='host_id'>\
							  </li>\
							  <li>\
							  <label for='id_cenote'>cenote id:</label>\
							  <input type='text' id='id_cenote' name='id_cenote'>\
							  </li>\
							<li>\
							  <label for='image'>image:</label>\
							  <input type='integer' id='image' name='image'>\
							  </li>\
							<li>\
							  <label for='network'>network:</label>\
							  <input type='text' id='network' name='network'>\
							  </li>\
							  <li>\
							  <label for='cacheSize'>Cache size:</label>\
							  <input type='text' id='cacheSize' name='network'>\
							  </li>\
							  <li>\
							  <label for='cachePolicy'>cache policy:</label>\
							  <input type='text' id='cachePolicy' name='cachePolicy'>\
							  </li>\
							  <li>\
							  <label for='levels'>levels:</label>\
							  <input type='text' id='levles' name='levels'>\
							  </li>\
							  <li>\
							  <label for='memory'>memory:</label>\
							  <input type='text' id='memory' name='memory'>\
							  </li>\
							  <li>\
							  <label for='capacity'>capacity:</label>\
							  <input type='text' id='capacity' name='capacity'>\
							  </li>\
						 </ul>\
						\
					</div>";
						isSchema=true;
					}
			   } 
			}
		}
		document.getElementById("close").addEventListener("click", function(){
			if (rightcard) {
				rightcard = false;
				var form=document.getElementById("form");
					document.getElementById("properties").classList.remove("expanded");
					form.removeChild(document.getElementById("formulario"));
					form.innerHTML+='<div id="formulario"></div>';
				setTimeout(function(){
					document.getElementById("propwrap").classList.remove("itson"); 
				}, 300);
				 tempblock.classList.remove("selectedblock");
			} 
		 });
		addEventListener("mousedown", beginTouch, false);
		addEventListener("mousemove", checkTouch, false);
		addEventListener("mouseup", doneTouch, false);
		//boton borrar todos los bloques
		document.getElementById("removeblock").addEventListener("click", function(){
 			flowy.deleteBlocks();
 			document.getElementById("Bin").classList.remove("create-flowy");
			document.getElementById("Cenote").classList.remove("create-flowy");
		});
		document.getElementById("crear").addEventListener("click", function(){
			var contenido=flowy.output();
			
		});
});