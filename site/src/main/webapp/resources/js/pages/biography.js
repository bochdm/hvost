$(document).ready(function(){

    jQuery("#newblockbiography").validate({
        ignore: ":hidden:not(textarea)",
        rules: {
            'title': {
                required: true
            },
            'text': {
                required: true
            }
        },
        messages: {
            'title':  {
                required: "Пожалуйста, укажите заголовок"
            },
            'text':  {
                required: "Пожалуйста, укажите основной текст"
            }
        },
        highlight: function(element) {
            //$(element).removeClass('has-success').addClass('has-error');
            console.log("nodeName -> " + $(element).prop('nodeName'));
            $(element).parent().find('iframe.wysihtml5-sandbox').css('border-color', 'red');
         //   $(element).next('iframe').addClass('border-color', 'red');
          //  $('.wysihtml5-sandbox').css('border-color', 'red');
        },
        errorPlacement: function(error, element) {
            if ($(element).prop('nodeName') === 'TEXTAREA'){
                error.insertBefore(element.parent());
            }
            if ($(element).prop('nodeName') === 'INPUT'){
                error.insertBefore(element.parent());
            }
          //  element.offset();
          //  error.appendTo(element.parent().next());
            //error.insertBefore(element);
            error.css('color', 'red');
        },
        success: function(element) {
            $(element).removeClass('has-error').addClass('has-success');
            $(element).remove();
        }

    });

});

