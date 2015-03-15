$(document).ready(function(){

    $("#delete").click(function(e) {
        bootbox.confirm("Удалить статью? Вы уверены?", function() {
            console.log("Alert Callback");
        });
    });
});