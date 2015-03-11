$(document).ready(function(){

    jQuery("#send_question").validate({
        rules: {
            'questionText': {
                required: true
            },
            'contact_email': {
                required: true,
                email: true
            },
            'author':{
                required: true
            }
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
              .text('OK!').addClass('valid')
              .closest('.control-group').removeClass('error').addClass('success');
        }
    });

}); // end document.ready
