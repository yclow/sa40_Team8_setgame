var url = "http://localhost:8080/SetGameMobile/";
var gid = null;
var pos = [-1, -1];
var container = [];

$(document).on("pagecreate", "#home", function () {
    $("#listGame").on("click", function () {
        $.getJSON(url + "api/game")
                .done(function (games) {
                    for (var i in games)
                        $("#gamelist").append(createGameList(games[i]));
                    $.mobile.navigate("#list");
                });
    });
        $("#list").on("click", "li>a", function () {
        console.log("list link clicked");
        gid = $(this).attr("href").substring(1);
        $.mobile.navigate("#game");
    });
    $("#newGame").on("click", function () {
        console.log("Clicked Detect");
        $.get("create", {cmd: "newGame"})
                .done(function (number)
                {
                    gid=number.trim();
                $.getJSON(url + "api/game/" + number)
                        .done(function (result) {
                            $("#gameid").append(gid);
                           var a = 0;
                            for (var i in result) {                               
                                    console.log(">>" + result[i]);
                                    // start
                                                 
                     var str="[data-pos="+a+"]>img";
                  //$(str).empty();                    
                  //$(str).append("<img src='images/" + z + ".gif' >");                  
                    $(str).attr("src", "images/" + result[i] + ".gif");                   
                  console.log(">" + a);
                a=a+1;
                                    // end
                            }
                        });
                }
                );
        $.mobile.navigate("#game");

            
        
    });
});

$(document).on("pagecreate", "#game", function () {
   // $("#gameid").append(gid);
    $("[data-pos]").on("click", function () {
        var i = pos.shift();
        if ((container.length) == 3)
        {
            $("[data-pos='" + container[0] + "']").removeClass("selected");
            container.shift();

            i = $(this).attr("data-pos");
            $("[data-pos='" + i + "']").addClass("selected");
            container.push(i);
        }
        else
        {
            i = $(this).attr("data-pos");
            $("[data-pos='" + i + "']").addClass("selected");
            container.push(i);
            console.log(container.length);
        }
    });


//    $(document).on("pagecontainerbeforeshow", function (_, $ui) {
//        switch ($ui.toPage.attr("id")) {
//            case "game":
//                $.getJSON(url + "api/game/" + gid)
//                        .done(function (result) {
//                            for (var i in result) {
//                                console.log("> " + result[i]);
//                            }
//                            $("#game").listview("refresh");
//                        });
//                break;
//            default:
//        }
//    });

    $("#submitBtn").on("click", function () {
        $.getJSON("game", {
            gid: gid,
            p0: container[0],
            p1: container[1],
            p2: container[2]
        }).done(function (table)
        {
//            console.log(gid);
//            for (var i in container)
//                $("[data-pos='" + container[i] + "']").removeClass("selected");
//            pos = [-1, -1];
            var b = 0;
            for (var y in table)
            {
                     var str="[data-pos="+b+"]>img";
                  //$(str).empty();                    
                  //$(str).append("<img src='images/" + result[y] + ".gif' >");                  
                  $(str).attr("src", "images/" + table[y] + ".gif");       
                   // console.log("Table[y]:" + table[y]);
                b=b+1;
            }
        }).error(function ()
        {
            console.log(gid);
            alert("Not a Set!");
        });
    });
});

function createGameList(gid) {
    var $a = $("<a>").attr("href", "#" + gid).text(gid);
    return ($("<li>").append($a));
}

$.when(jqmReady).done(function () {
    console.log("all ready");
});
