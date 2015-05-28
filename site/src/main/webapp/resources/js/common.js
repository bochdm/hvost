/**
 * Created by kseniaselezneva on 28/05/15.
 */
$(document).ready(function(){
    console.log("scroll before");
    jQuery('a[href^="#"]').click(function(){
        console.log("scroll in");
        //Сохраняем значение атрибута href в переменной:
        var target = $(this).attr('href');
        jQuery('html, body').animate({scrollTop: $(target).offset().top}, 800);
        return false;
    });
});