$(document).ready(function() {
	var aux = $("div#fcontent").find("span");
	aux.each(function(index,value){
		var src = $(value).find("img").attr("src");
		var link = $(value).find("a").attr("href",src);
	});
})
