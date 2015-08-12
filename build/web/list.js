//var gid = null;

$(function() {
    $("#games").on("click", "a", function(){
        
        window.open("game.html#"+"   "+ $(this).attr("href"));
//       gid = $(this).attr("href").substring(1);
//       console.log("GID from the List" + gid);
    });
//          $.getJSON("api/game"+gid)
//        .done(function(result){
//        var a = 0;
//                for (var i in result) {
//        console.log(">>" + result[i]);
//                // start
//                var str = "[data-pos=" + a + "]>img";
//                $(str).attr("src", "images/" + result[i] + ".gif");
//                console.log(">" + a);
//                a = a + 1;
//        }
//    });
    
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


