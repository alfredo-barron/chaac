$("#signup_form").submit(function(e){
    var $form=$(this);
    var data= $form.serializeArray();
    dataJson= {
        name: data[0].value,
        password: data[3].value,
        email: data[2].value,
        user_name: data[1].value,
    };
    console.log(dataJson);
    fetch('/users',{
        "method":"POST",
        "headers":{
            "Content-Type":"application/json"
        },
        "body":JSON.stringify ( dataJson),
    }).then(response=>{
        if(response.ok){
            response.text()
            .then(response=>{
                //get response the server
                json= JSON.parse(response);
                if(json.status==200){
                    swal(json.data,{
                        icon: "success",
                    });
                    setTimeout(function(){
                        fetch('/login',{
                            "method":"POST",
                            "headers":{
                                "Content-Type":"application/json"
                            },
                            "body":JSON.stringify({
                                "email": dataJson.email,
                                "password": dataJson.password,
                            })
                        }).then(res=>{
                            res.text()
                        .then(res=>{
                            const json2=JSON.parse(res);
                            if(json2.status==200){
                                window.location.href = "/visualize";
                            }
                        });})
                    },2000);
                }else {
                    swal(json.data,{
                        icon: "error",
                    });
                }
            });
        }
    })
    e.preventDefault();
});

$("#login").submit(function(e){
    var $form2=$(this);
    var data2= $form2.serializeArray();
    console.log(data2)
    fetch('/login',{
        "method":"POST",
        "headers":{
            "Content-Type":"application/json"
        },
        "body":JSON.stringify ({
            "email": data2[0].value,
            "password": data2[1].value,
        }),
    }).then(response => response.text())
    .then(data => {
      const json = JSON.parse(data);
        if(json.status==200){
           window.location.href = "/visualize";
        }else{
            swal(json.data,{
                icon: "error",
            });
        };
    });
   
    e.preventDefault();
});
$("#logout").click(function(e){
    fetch('/logout',{
        "method":"POST",
        "headers":{
            "Content-Type":"application/json"
        },
    }).then(response => response.text())
    .then(data=>{
        res=JSON.parse(data);
        if(res.status==200){
            window.location.href = "/";
            console.log(res);
        }
    });
});