$("form[name=signup_for]").submit(function(e){
    console.log("entraste");
    var $form=$(this);
    var data= $form.serializeArray();
    console.log(data);
    var url="localhost:45000/users"
    /*$.ajax({
        url: url,
        type: "POST",
        data: data,
        dataType: "json",
        success: function(resp){
            console.log(resp);
        },
        error: function(resp){
            console.log(resp);
        }

    });*/
    e.preventDefault();
});