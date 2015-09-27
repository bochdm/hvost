$(document).ready(function(){

    console.log('newcarousel.js');

    jQuery("#new_carousel").validate({
        ignore: ":hidden:not(textarea)",
        rules: {
            'title': {
                required: true
            },
            'content': {
                required: true
            }
        },
        messages: {
            'title':  {
                required: "Пожалуйста, укажите заголовок"
            },
            'content':  {
                required: "Пожалуйста, укажите основной текст"
            }
        },
        highlight: function(element) {
            //$(element).removeClass('has-success').addClass('has-error');
            $(element).closest('iframe').css('background-color', 'red');
        },
        success: function(element) {
            $(element).removeClass('has-error').addClass('has-success');
            $(element).remove();
        }
    });

});

