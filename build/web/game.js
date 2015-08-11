var SetCard = {
    source: null,    
    gid: null
};
//var url = "http://localhost:8080/SetGameMobile/";
//var gid = null;
//var pos = [-1,-1];
//var container = [];

//$(function(){
//    
//})
$(function () {
    SetCard.gid = window.location.hash.substring(1);
    $("#gid").text(SetCard.gid);
   $.getJSON("board", {
        gid: SetCard.gid
   }).done(function(result) {
        for (var i in result)
           flip(i, result[i]);
        SetCard.source = new EventSource("api/gameevent/" + SetCard.gid);
        SetCard.source.onmessage = handleMessage;
   })
    
 });

//function handleMessage(event) {
//    var move = JSON.parse(event.data);
//    flip(move.p0, move.p0pic);
//    flip(move.p1, move.p1pic);
//    console.log(">>> " + event.data);
//    if (!move.take) {
//        $("[data-pos='" + move.p0 + "']>img").delay(2000).queue(function () {
//            flip(move.p0, "harold");
//            $(this).dequeue();
//        });
//        $("[data-pos='" + move.p1 + "']>img").delay(2000).queue(function () {
//            flip(move.p1, "harold");
//            $(this).dequeue();
//        });
//    }
//}
//
//function flip(c, img) {
//    console.log("flip: " + c + ", " + img);
//    $("[data-pos='" + c + "']>img").attr("src", "images/" + img + ".png");
//}


