document.addEventListener("DOMContentLoaded", function(){
	var spacing_x = 1;
	var spacing_y = 10;
	var rightcard = false;
    var tempblock;
    var tempblock2;
	var cont=0;
	let schema={
		"schema_name":"schema-01",
		"cenotes": [], 
	};
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
    		var grab = drag.querySelector(".grabme");
        	grab.parentNode.removeChild(grab);
        	var blockin = drag.querySelector(".blockin");
        	blockin.parentNode.removeChild(blockin);
        	if(first){
        		if(drag.querySelector('.blockelemtype').value=="1"){
        			drag.innerHTML+="<div class='blockyleft noselect'>\
					 	<div><h2 class='noselect' >Schema</h2>\
						</div>\
						<h2 class='blocktitle' id='schema_title'> name:"+schema.schema_name+"</h2>\
					  	<nav>\
					  		<ul>\
					  		<li><img class='icon' src='/static/img/icons/suelto.png'> </li>\
							</ul>\
					</div>"; 
				}
				document.getElementById("Cenote").classList.add("create-flowy");
        		document.getElementById("Bin").classList.add("create-flowy");
        		return true;
        	}else{
        		if(drag.querySelector('.blockelemtype').value=="2"){
					schema.cenotes.push({"id": drag.querySelector('.blockid').value,
					"name": "cenote-1",
					"image": "alfredobarron/chaac-cenote:1",
					"network": "my-net",
					"publicPort": 8081,
					"distribuitor": "ROUND_ROBIN",
				  	"bins": []});
        			drag.innerHTML+="<div class='blockyleft noselect'>\
					<div><h2 class='noselect' >Cenote</h2></div>\
					<h2 class='blocktitle' id='cenote_title' >"+schema.cenotes[indexCenote(drag.querySelector('.blockid').value)].name+"</h2>\
					<nav>\
						<ul>\
							<li><img class='blockicon icon' src='/static/img/icons/suelto.png'></li>\
						</ul>\
					</div>";
        		}
        		if(drag.querySelector('.blockelemtype').value=="3"){
					schema.cenotes[indexCenote(parent.querySelector('.blockid').value)].bins.push({
						"id": drag.querySelector('.blockid').value,
		  				"name": "bin-1",
		  				"hostId": "host-1",
		  				"cenoteId": parent.querySelector('.blockid').value,
		  				"image": "alfredobarron/chacc-bin:1",
		  				"network": "my-net",
		  				"cacheSize": 20,
		  				"cachePolicy": "LFU",
		  				"levels": 1,
		  				"memory": "2GB",
		  				"capacity": "40GB"
					});
        			drag.innerHTML+="<div class='blockyleft noselect'>\
						<div><h2 class='noselect' >Bin</h2></div>\
						<nav>\
							<ul>\
								<li><img class='icon' src='/static/img/icons/suelto.png'> </li>\
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
					cont++;
        			return true;
        		}
        	}
        	
    	}
		//function search cenote
		function indexCenote(id){
			for(var j=0;j<=schema.cenotes.length;j++){
				if(schema.cenotes[j].id==id){
					return j;
				}
			}
			 
		}
		function indexBin(id){
		 	for(var j=0;j<schema.cenotes.length;j++){
				for(var k=0; k<=schema.cenotes[j].bins.length;k++){
					if(schema.cenotes[j].bins[k].id==id){
						return k;
					}
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
						<form>\
							<ul>\
								<li>\
    								<label for='schema_name'>schema name:</label>\
    								<input type='text' id='schema_name' name='schema_name' value='"+schema.schema_name+"'>\
  								</li>\
						</div>";
						isSchema=true;
					}
					if(tempblock.querySelector('.blockelemtype').value=="2"){
						propiedades.classList.add("expanded");
            			editar.classList.add("itson");
						//search cenote 
						cenote=schema.cenotes[indexCenote(tempblock.querySelector('.blockid').value)]
						form.innerHTML+="<div id='formulario'>\
							<p class='header2'>Edit Cenote</p>\
							<form>\
							<ul>\
								<li>\
    								<label for='id_cenote'>id:</label>\
    								<label  name='id_cenote' value='"+cenote.id+"'>\
  								</li>\
  								<li>\
    								<label for='name_cenote'>Nombre:</label>\
    								<input type='text' id='name_cenote' name='cenote_name' value='"+cenote.name+"'>\
  								</li>\
  								<li>\
    								<label for='image_cenote'>image:</label>\
    								<input type='text' id='image_cenote' name='cenote_image' value='"+cenote.image+"'>\
  								</li>\
  								<li>\
								  <label for='network'>network:</label>\
								  <input type='text' id='network' name='network' value='"+cenote.network+"'>\
  								</li>\
								<li>\
								  <label for='port'>public port:</label>\
								  <input type='integer' id='port' name='port' value='"+cenote.publicPort+"'>\
  								</li>\
								<li>\
								  <label for='distribuidor'>distribuidor:</label>\
								  <input type='text' id='distribuidor' name='distribuidor' value='"+cenote.distribuitor+"'>\
  								</li>\
 							</ul>\
							\
						</div>"
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
		//btn delete all blocks
		document.getElementById("removeblock").addEventListener("click", function(){
 			flowy.deleteBlocks();
 			document.getElementById("Bin").classList.remove("create-flowy");
			document.getElementById("Cenote").classList.remove("create-flowy");
		});
		document.getElementById("crear").addEventListener("click", function(){
			var structure=flowy.output();
			crear={
				"schema_name": schema.schema_name,
				"cenotes": schema.cenotes,
				"structure" : structure
			}
			console.log(crear);
			
		});
		//btn save form
		document.getElementById("guardar").addEventListener("click", function(){
			console.log("el dato selecionado es:",tempblock.id);
			if(tempblock.id=="Schema"){
				schema.name_schema=document.getElementById("schema_name").value;
			}
			if(tempblock.id=="Cenote"){
					index=indexCenote(tempblock.querySelector('.blockid').value)
						schema.cenotes[index].name=document.getElementById("name_cenote").value;
						schema.cenotes[index].image=document.getElementById("image_cenote").value;
						schema.cenotes[index].network= document.getElementById("network").value;
						schema.cenotes[index].publicPort= document.getElementById("port").value,
						schema.cenotes[index].distribuitor=document.getElementById("distribuidor").value;
			}
			if(tempblock.id=="Bin"){
				index=indexBin(tempblock.querySelector('blockid').id);

			}
		});
});