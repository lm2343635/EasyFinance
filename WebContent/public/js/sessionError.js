var count=5;

$(document).ready(function(){
	setInterval(function(){
		count--;
		$("#count").html(count);
	}, 1000);
	setTimeout(function(){
		location.href="index.html";
	}, 5000);
});

$("#toIndex").click(function(){
	location.href="index.html";
});