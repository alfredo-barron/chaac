$("form[name=signup_for").submit(function(e){
    var $form=$(this);
    var data= $form.serialize();
    $.ajax({
        url: "/",
        type: "PUT",
        data: data,
        dataType: "json",
        success: function(resp){
            console.log(resp);
        },
        error: function(resp){
            console.log(resp);
        }

    });

    e.preventDefault();
});