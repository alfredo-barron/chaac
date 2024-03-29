$("#create").click(function(e){
    window.location.href = "/creation";
});
var spacing_x = 20;
var spacing_y = 20;
var schema;
var rightcard = false;
var tempblock;
var tempblock2;
var resData;
flowy(document.getElementById("canvas"),onGrab,onRelease,onSnapping,onRearrange,spacing_x,spacing_y);
function addEventListenerMulti(type, listener, capture, selector) {
    var nodes = document.querySelectorAll(selector);
    for (var i = 0; i < nodes.length; i++) {
        nodes[i].addEventListener(type, listener, capture);
    }
}
$("#save_schema").click(function(){
    console.log("se hizo click");
    if(schema!=null){
        fetch('/users/schemas',{
            "method":"PUT",
            "headers":{
                "Content-Type":"application/json"
            },
            "body":JSON.stringify(schema),
        }).then(response => response.text())
        .then(res=>{
            const json2=JSON.parse(res);
            if(json2.status==200){
                swal(json2.data,{
                    icon: "success",}) 
                }
            if(json2.status==400){
                swal(json2.data,{
                    icon: "error",}) 
                }
            });

    }
});
$("#remove_schema").click(function(){
    console.log(resData.id);
    if(schema!=null){
        fetch('/users/schemas/'+resData.id,{
            "method":"DELETE",
            "headers":{
                "Content-Type":"application/json"
            },
        }).then(response => response.text())
        .then(res=>{
            const json2=JSON.parse(res);
            if(json2.status==200){
                swal(json2.data,{
                    icon: "success",}) 
                }
                flowy.remove();
            if(json2.status==400){
                swal(json2.data,{
                    icon: "error",}) 
                }
            });

    }
});

$(".schema-list").click(function(){
    temp=this;
    if(schema==null){
        visualizeScheme(this.querySelector('.schemaId').value);
    }else{
        if(checkChanges()){
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
                    res<=value;
                    switch(value){
                        case "yes":
                            swal("changes will not be saved","", "warning");
                            setTimeout(function(){
                                visualizeScheme(temp.querySelector('.schemaId').value);
                            },300);
                            break;
                        case "not":
                            swal("press the save option","","warning");
                            break;
                        } 
                    });
        }else{
            visualizeScheme(temp.querySelector('.schemaId').value);
        }
    }
});
function visualizeScheme(id){
    fetch('/users/schemas/'+id,{
        "method":"GET",
        "headers":{
            "Content-Type":"application/json"
        },
    }).then(response => response.text())
    .then(data=>{
        res=JSON.parse(data);
            if (res.status=200){
                console.log(res.data);
                resData=res.data;
                getSchema(res);
                if ($("#canvas").html()==""){
                    flowy.import(JSON.parse( res.data.structure));
                }else{
                    flowy.deleteBlocks();
                    flowy.import(JSON.parse( res.data.structure));
                } 
            }
    });
}
function checkChanges(){
    var changes=false;
    if(resData.schema_name!=schema.schema_name){
        changes=true;
    }
    for(var i=0; i< schema.cenotes.length;i++){
        if(resData.cenotes[i].name!=schema.cenotes[i].name){
            changes=true;
        }
        if(resData.cenotes[i].name!=schema.cenotes[i].name){
            changes=true;
        }
        if(resData.cenotes[i].image!=schema.cenotes[i].image){
            changes=true;
        }
        if(resData.cenotes[i].network!=schema.cenotes[i].network){
            changes=true;
        }
        if(resData.cenotes[i].publicPort!=schema.cenotes[i].publicPort){
            changes=true;
        }
        if(resData.cenotes[i].distribuitor!=schema.cenotes[i].distribuitor){
            changes=true;
        }
        for(var j=0; j<schema.cenotes[i].bins.length;j++){
            if(resData.cenotes[i].bins[j].name!=schema.cenotes[i].bins[j].name){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].hostId!=schema.cenotes[i].bins[j].hostId){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].image!=schema.cenotes[i].bins[j].image){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].network!=schema.cenotes[i].bins[j].network){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].cacheSize!=schema.cenotes[i].bins[j].cacheSize){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].cachePolicy!=schema.cenotes[i].bins[j].cachePolicy){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].levels!=schema.cenotes[i].bins[j].levels){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].memory!=schema.cenotes[i].bins[j].memory){
                changes=true;
            }
            if(resData.cenotes[i].bins[j].capacity!=schema.cenotes[i].bins[j].capacity){
                changes=true;
            }
        }
    }
    return changes;
}
function getSchema(res){
    schema={
        "schema_name":res.data.schema_name,
        "cenotes":res.data.cenotes,
        "id": res.data.id
    }

}
function onSnapping(drag, first,parent){
    return false;
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
function onRearrange(block, parent){
    return true;
}
//methods to detect click on the blocks
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
//insert forms
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
                        <input type='text' id='name_bin' name='name_bin' value='"+bin.name+"' onchange>\
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
        if(document.getElementById("schema_name").value != schema.schema_name){
            return true;
        }
    }
    if(tempblock.querySelector('.blockelemtype').value=="2"){
        cenote=schema.cenotes[indexCenote(tempblock.querySelector('.blockid').value)]
        if(document.getElementById("name_cenote").value != cenote.name){
            return true;
        }
        if(document.getElementById("image_cenote").value != cenote.image){
            return true;
        }
        if(document.getElementById("network").value != cenote.network){
            return true;
        }
        if(document.getElementById("port").value != cenote.publicPort){
            return true;
        }
        if(document.getElementById("distribuidor").value != cenote.distribuitor){
            return true;
        }
    }
    if(tempblock.querySelector('.blockelemtype').value=="3"){
        bin=returnBin(tempblock.querySelector('.blockid').value);
        if(document.getElementById("name_bin").value != bin.name){
            return true;
        }
        if(document.getElementById("host_id").value != bin.hostId){
            return true;
        }
        if(document.getElementById("id_cenote").value != bin.cenoteId){
            return true;
        }
        if(document.getElementById("image").value != bin.image){
            return true;
        }
        if(document.getElementById("network").value != bin.network){
            return true;
        }
        if(document.getElementById("cacheSize").value != bin.cacheSize){
            return true;
        }
        if(document.getElementById("cachePolicy").value != bin.cachePolicy){
            return true;
        }
        if(document.getElementById("levels").value != bin.levels){
            return true;
        }
        if(document.getElementById("memory").value != bin.memory){
            return true;
        }
        if(document.getElementById("capacity").value != bin.capacity){
            return true;
        }
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