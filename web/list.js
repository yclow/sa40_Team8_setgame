
$(function() {
    $("#games").on("click", "a", function() {
        window.open("game.html#" + $(this).attr("href").substring(1));
    });
    
    $.getJSON("api/game")
        .done(function(result) {
            for (var i in result)
                $("#games").append(createLi(result[i]));            
        });
});

function createLi(gid) {
    var $a = $("<a>").attr("href", "#" + gid).text(gid);
    return ($("<li>").append($a));
}


