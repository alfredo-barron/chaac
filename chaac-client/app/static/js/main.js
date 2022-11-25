document.addEventListener("DOMContentLoaded", function(){
	var spacing_x = 1;
	var spacing_y = 10;
	var rightcard = false;
    var tempblock;
    var tempblock2;
	var contBin=0;
	var contCenote=0;
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
						<h2 class='blocktitle' id='schema_title'> Name: "+schema.schema_name+"</h2>\
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
        		if(drag.querySelector('.blockelemtype').value=="2"&&parent.querySelector('.blockelemtype').value!="2"&&parent.querySelector('.blockelemtype').value!="3"){
					schema.cenotes.push({"blockid": drag.querySelector('.blockid').value,
					"id": Math.floor(Math.random() * 999999),
					"name": "cenote-"+contCenote,
					"image": "alfredobarron/chaac-cenote:1",
					"network": "my-net",
					"publicPort": 8081,
					"distribuitor": "ROUND_ROBIN",
				  	"bins": []});
        			drag.innerHTML+="<div class='blockyleft noselect'>\
					<div><h2 class='noselect' >Cenote</h2></div>\
					<h2 class='blocktitle' id='cenote_title' > Name:"+schema.cenotes[indexCenote(drag.querySelector('.blockid').value)].name+"</h2>\
					<nav>\
						<ul>\
							<li><img class='blockicon icon' src='/static/img/icons/suelto.png'></li>\
						</ul>\
					</div>";
					contCenote++;
        		}
        		if(drag.querySelector('.blockelemtype').value=="3"&&parent.querySelector('.blockelemtype').value!="3"&&parent.querySelector('.blockelemtype').value!="1"){
					schema.cenotes[indexCenote(parent.querySelector('.blockid').value)].bins.push({
						"blockid": drag.querySelector('.blockid').value,
						"parentid": parent.querySelector('.blockid').value,
						"id":Math.floor(Math.random() * 999999),
		  				"name": "bin-"+contBin,
		  				"hostId": "host-1",
						"cenoteId":schema.cenotes[indexCenote(parent.querySelector('.blockid').value)].id,
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
						<h2 class='blocktitle' > Name: bin-"+contBin+"</h2>\
						<nav>\
							<ul>\
								<li><img class='icon' src='/static/img/icons/suelto.png'> </li>\
							</ul>\
						</div>";
						contBin++;
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
					console.log(schema);
        			return true;
        		}
        	}
        	
    	}
		//function search cenote
		function indexCenote(id){
			for(var j=0;j<=schema.cenotes.length;j++){
				if(schema.cenotes[j].blockid==id){
					return j;
				}
			}
			 
		}
		function returnBin(id){
		 	for(var j=0;j<schema.cenotes.length;j++){
				for(var k=0; k<schema.cenotes[j].bins.length;k++){
					if(schema.cenotes[j].bins[k].blockid==id){
						return schema.cenotes[j].bins[k];
					}
				}
			}
		}

		function indexBin(id){
			for(var j=0;j<schema.cenotes.length;j++){
				for(var k=0; k<schema.cenotes[j].bins.length;k++){
					if(schema.cenotes[j].bins[k].blockid == id){
						return  [j,k];
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
    								<label  id='id_cenote'>"+cenote.id+"</label>\
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
						console.log(tempblock.querySelector('.blockid').value);
						bin=returnBin(tempblock.querySelector('.blockid').value);
						console.log(bin);
						form.innerHTML+="<div id='formulario'>\
						<p class='header2'>Edit Cenote</p>\
						<form>\
						<ul>\
							<li>\
								<label for='id_bin'>id:</label>\
								<label id='id_bin' >"+bin.id+"</label>\
							  </li>\
							  <li>\
								<label for='name_bin'>Nombre:</label>\
								<input type='text' id='name_bin' name='bin_cenote' value='"+bin.name+"' onchange>\
							  </li>\
							  <li>\
								<label for='host_id'>host id:</label>\
								<input type='text' id='host_id' name='host_id' value='"+bin.hostId+"'>\
							  </li>\
							  <li>\
							  <label for='id_cenote'>cenote id:</label>\
							  <label id='id_cenote' >"+bin.cenoteId+"</label>\
							  </li>\
							<li>\
							  <label for='image'>image:</label>\
							  <input type='integer' id='image' name='image' value='"+bin.image+"'>\
							  </li>\
							<li>\
							  <label for='network'>network:</label>\
							  <input type='text' id='network' name='network' value='"+bin.network+"'>\
							  </li>\
							  <li>\
							  <label for='cacheSize'>Cache size:</label>\
							  <input type='text' id='cacheSize' name='cacheSize' value='"+bin.cacheSize+"'>\
							  </li>\
							  <li>\
							  <label for='cachePolicy'>cache policy:</label>\
							  <input type='text' id='cachePolicy' name='cachePolicy' value='"+bin.cachePolicy+"'>\
							  </li>\
							  <li>\
							  <label for='levels'>levels:</label>\
							  <input type='text' id='levles' name='levels' value='"+bin.levels+"'>\
							  </li>\
							  <li>\
							  <label for='memory'>memory:</label>\
							  <input type='text' id='memory' name='memory' value='"+bin.memory+"'>\
							  </li>\
							  <li>\
							  <label for='capacity'>capacity:</label>\
							  <input type='text' id='capacity' name='capacity' value='"+bin.capacity+"'>\
							  </li>\
						 </ul>\
						\
					</div>";
					}
			   } 
			}
		}

		function changeform(){
			if(tempblock.querySelector('.blockelemtype').value=="1"){
				console.log(document.getElementById("schema_name").value);
				if(document.getElementById("schema_name").value != schema.schema_name){
					return true;
				}
			}
			if(tempblock.querySelector('.blockelemtype').value=="2"){
				cenote=schema.cenotes[indexCenote(tempblock.querySelector('.blockid').value)]
			}
			if(tempblock.querySelector('.blockelemtype').value=="3"){
				bin=returnBin(tempblock.querySelector('.blockid').value);
			}
		}

		document.getElementById("close").addEventListener("click", function(){
			if (rightcard) {
				if(changeform()){
					swal("you have unsaved changes","Do you want to continue without saving?",{
						icon: "warning",
						buttons:{
							not:{
								text: "not"
							},
    						yes:{
								text:"yes"
							},
						}
					}).then((value) => {
						switch (value) {
					   
						  case "not":
							swal("press the save option","","warning");
							break;
					   
						  case "yes":
							swal("changes will not be saved","", "warning");
							rightcard = false;
							var form=document.getElementById("form");
							document.getElementById("properties").classList.remove("expanded");
							form.removeChild(document.getElementById("formulario"));
							form.innerHTML+='<div id="formulario"></div>';
						setTimeout(function(){
							document.getElementById("propwrap").classList.remove("itson"); 
						}, 300);
					 	tempblock.classList.remove("selectedblock");
							break;
						}
					  });
				}else{
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
			} 
		 });
		addEventListener("mousedown", beginTouch, false);
		addEventListener("mousemove", checkTouch, false);
		addEventListener("mouseup", doneTouch, false);
		//btn delete all blocks
		document.getElementById("removeblock").addEventListener("click", function(){
 			flowy.deleteBlocks();
			console.log(schema);
			schema={
				"schema_name":"schema-01",
				"cenotes": [], 
			};
			contBin=0;
			contCenote=0;
			console.log(schema);
 			document.getElementById("Bin").classList.remove("create-flowy");
			document.getElementById("Cenote").classList.remove("create-flowy");
			swal("schematic removed",{
				icon: "success",
			});
		});
		document.getElementById("crear").addEventListener("click", function(){
			if(contBin>0&&contCenote>0){
				var structure=flowy.output();
				crear={
					"schema_name": schema.schema_name,
					"cenotes": schema.cenotes,
					"structure" : structure
				}
				fetch('/users/schemas',{
					"method":"POST",
					"headers":{
						"Content-Type":"application/json"
					},
					"body":crear,
				}).then(res=>{
					res.text()
				.then(res=>{
					const json2=JSON.parse(res);
					if(json2.status==201){
						swal(json2.data,{
							icon: "succes",
						});
					}else{
						swal(json2.data,{
							icon: "error",
						});
					}
				});})
			}else{
				swal("the scheme must contain at least one cenote and one bin",{
					icon: "error",
				});
			}
			
		});
		//btn save form
		document.getElementById("guardar").addEventListener("click", function(){
			console.log("el dato selecionado es:",tempblock.id);
			if(tempblock.id=="Schema"){
				schema.schema_name=document.getElementById("schema_name").value;
				tittle=tempblock.querySelector(".blocktitle");
				tittle.innerHTML="Name: "+schema.schema_name;
			}
			if(tempblock.id=="Cenote"){
					index=indexCenote(tempblock.querySelector('.blockid').value)
						schema.cenotes[index].name=document.getElementById("name_cenote").value;
						schema.cenotes[index].image=document.getElementById("image_cenote").value;
						schema.cenotes[index].network= document.getElementById("network").value;
						schema.cenotes[index].publicPort= document.getElementById("port").value,
						schema.cenotes[index].distribuitor=document.getElementById("distribuidor").value;
						tittle=tempblock.querySelector(".blocktitle");
						tittle.innerHTML="Name: "+schema.cenotes[index].name;
			}
			if(tempblock.id=="Bin"){
				index= indexBin(tempblock.querySelector('.blockid').value);
				schema.cenotes[index[0]].bins[index[1]].name=document.getElementById("name_bin").value;
				schema.cenotes[index[0]].bins[index[1]].hostId=document.getElementById("host_id").value;
				schema.cenotes[index[0]].bins[index[1]].image=document.getElementById("image").value;
				schema.cenotes[index[0]].bins[index[1]].network=document.getElementById("network'").value;
				schema.cenotes[index[0]].bins[index[1]].cacheSize=document.getElementById("cacheSize").value;
				schema.cenotes[index[0]].bins[index[1]].cachePolicy=document.getElementById("cachePolicy").value;
				schema.cenotes[index[0]].bins[index[1]].levels=document.getElementById("levles").value;
				schema.cenotes[index[0]].bins[index[1]].memory=document.getElementById("memory").value;
				schema.cenotes[index[0]].bins[index[1]].capacity=document.getElementById("capacity").value;
				tittle=tempblock.querySelector(".blocktitle");
				tittle.innerHTML="Name: "+schema.cenotes[index[0]].bins[index[1]].name;
			}
		});
});