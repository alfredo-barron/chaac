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
            response.json()
            .then(response=>{
                //get response the server
                console.log(response)
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
    })
    e.preventDefault();
});