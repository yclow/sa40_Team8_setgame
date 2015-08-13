var gid;
var temp = null;
var sourceA;
var url = "http://localhost:8080/SetGameMobile/";
//var gid = null;
//var pos = [-1,-1];
//var container = [];

//$(function(){
//    
//})
$(function () {
    gid = window.location.hash.substring(1);
    console.log(window.location.hash);
    temp = window.location.hash.substring(5);
    console.log("Is the EO opened?");
    $("#gid").text(temp);
    gid = temp;
    sourceA = new EventSource(url + "api/gameevent/" +gid);
    sourceA.onmessage = function (event) {
            var input = JSON.parse(event.data);
            console.log("Before Stringify JSON");
            console.log(JSON.stringify(input));
            var a = 0;
            for (var i in input) {
                console.log(">>" + input[i].ID);
                // start

                var str = "[data-pos=" + a + "]>img";
                //$(str).empty();                    
                //$(str).append("<img src='images/" + z + ".gif' >");                  
                $(str).attr("src", "images/" + input[i].ID   + ".gif");
                console.log(">" + a);
                a = a + 1;
            }
            ;
            console.log("after Stringify JSON");
        };
        console.log("End of BroadCast");
    $.getJSON("board", {
        gid: gid
    }).done(function (result) {
        console.log("getting gid");
        var a = 0;
        for (var i in result) {
            console.log(">>" + result[i]);
            // start
            var str = "[data-pos=" + a + "]>img";
            $(str).attr("src", "images/" + result[i] + ".gif");
            console.log(">" + a);
            a = a + 1;
        }
        ;
        
        console.log("Got Array?");
//        sourceA.onmessage = function (event) {
//            var input = JSON.parse(event.data);
//            console.log(JSON.stringify(input));
//            var a = 0;
//            for (var i in input) {
//                console.log(">>" + input[i].ID);
//                // start
//
//                var str = "[data-pos=" + a + "]>img";
//                //$(str).empty();                    
//                //$(str).append("<img src='images/" + z + ".gif' >");                  
//                $(str).attr("src", "images/" + input[i].ID   + ".gif");
//                console.log(">" + a);
//                a = a + 1;
//            }
//            ;
//        };
    });
    $(document).ready(function()
    {
       $.getJSON("board", {
        gid: gid
    }).done(function (result) {
        console.log("getting gid");
        var a = 0;
        for (var i in result) {
            console.log(">>" + result[i]);
            // start
            var str = "[data-pos=" + a + "]>img";
            $(str).attr("src", "images/" + result[i] + ".gif");
            console.log(">" + a);
            a = a + 1;
        }
        ;
        
        console.log("Got Array?");
    });
    });
});



