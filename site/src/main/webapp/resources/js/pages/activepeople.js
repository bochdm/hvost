$(document).ready(function(){

    jQuery("#send-question").validate({
        rules: {
            'author': {
                required: true
            },
            'questionText': {
                required: true
            },
            'contact_email': {
                email: true
            }
        },
        messages: {
            'author':  {
                required: "Пожалуйста, укажите свое имя"
            },
            'questionText':  {
                required: "Пожалуйста, укажите вопрос"
            },
            'contact_email': {
                email: "Пожалуйста, укажите существующий адрес электронной почты"
            }
        },
        highlight: function(element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(element) {
            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
            $(element).remove();
        }
    });

});
