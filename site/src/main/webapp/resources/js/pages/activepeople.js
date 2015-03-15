$(document).ready(function(){

    jQuery("#send_question").validate({
        rules: {
            'questionText': {
                required: true
            },
            'contact_email': {
                email: true
            }/*,
            'author':{
                required: true
            }*/
        },
        messages: {
            'questionText':  {
                required: "Пожалуйста, укажите вопрос"
            },
            'contact_email': {
                email: "Пожалуйста, укажите существующий адрес электронной почты"
            }
        },
        highlight: function(element) {
            $(element).closest('.control-group').removeClass('success').addClass('error');
        },
        success: function(element) {

            element
              .closest('.control-group').removeClass('error').addClass('success');
        },
        submitHandler: showThanks
    });


/*    $("#newQuestion").click(function(){
        bootbox.alert("Спасибо за Ваш вопрос! Мы постараемся ответить Вам в ближайшее время", function() {
            console.log("Alert Callback");
        });
    });*/

});

function showThanks(form){
    bootbox.alert("Спасибо за Ваш вопрос! Мы постараемся ответить Вам в ближайшее время", function() {
        form.submit();
    });


}
