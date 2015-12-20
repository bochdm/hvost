$(document).ready(function(){

    jQuery("#new_carousel, #update_carousel").validate({
        ignore: ":hidden:not(textarea)",
        rules: {
            'title': {
                required: true
            },
            'content': {
                required: true
            },
            'link':{
                url:true
            }
        },
        messages: {
            'title':  {
                required: "Пожалуйста, укажите заголовок"
            },
            'content':  {
                required: "Пожалуйста, укажите основной текст"
            },
            'link':{
                url: "Пожалуйста, введите верный адрес (пример, http://www.onf.ru)"
            }
        },
        highlight: function(element) {
            //$(element).removeClass('has-success').addClass('has-error');
            console.log("nodeName -> " + $(element).prop('nodeName'));
            $(element).parent().find('iframe.wysihtml5-sandbox').css('border-color', 'red');
            $(element).parent().find('div.note-editable').css('border-color', 'red');
         //   $(element).next('iframe').addClass('border-color', 'red');
          //  $('.wysihtml5-sandbox').css('border-color', 'red');
        },
        errorPlacement: function(error, element) {
            if ($(element).prop('nodeName') === 'TEXTAREA'){
                error.insertBefore(element);
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

