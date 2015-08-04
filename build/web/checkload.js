var jqmReady = $.Deferred();

$(document).on("mobileinit", function() {
   jqmReady.resolve(); 
});


