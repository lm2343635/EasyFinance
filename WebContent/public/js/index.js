var LOGIN_SUCCESS=0;
var PASSWORD_WRONG=1;
var NO_USING_ACCOUNT_BOOK=2;

$(document).ready(function(){
	var screenHeight=getScreenHeight();
	
	$("#carousel-example-generic .carousel-inner .item ").height(getScreenHeight());
	$(".opacity-content").css("left",(getScreenWidth()-1008)/2+"px");
	
	$("#login-form").css("left",(getScreenWidth()-$("#login-form").width())/2+"px");
	var loginTop=(screenHeight-$("#login-form").height())/2;
	$("#login-form").animate({
		top: loginTop+"px",
		opacity: 0.85
	},"slow");
	
	$("#login-email").blur(function(){
		UserManager.isEmailExist($(this).val(),function(data){
			if(data==true)
			{
				$("#login-email-not-exist").hide();
				$("#login-email-exist").show();
				$("#login-email-not-exist").popover("hide");
			}		
			else
			{
				$("#login-email-exist").hide();
				$("#login-email-not-exist").show();
				$("#login-email-not-exist").popover("show");
				setTimeout(function() {
					$("#login-email-not-exist").popover("hide");
				}, 2500);
			}				
		});
	});
	
	$("body").keydown(function() {
		if (event.keyCode==13) {
			$("#login-submit").click();
		}
	});
	
	$("#login-submit").click(function() {
		var email=$("#login-email").val();
		var password=$("#login-password").val();
		if($("#login-email-exist").is(":visible")&&password!=""&&password!=null)
			UserManager.login(email,password,function(data){
				if(data==LOGIN_SUCCESS)
					location.href="main.html";
				else if(data==PASSWORD_WRONG)
				{
					$("#login-wrong-password").show();
					$("#login-wrong-password").popover("show");
					setTimeout(function() {
						$("#login-wrong-password").popover("hide");
					}, 2500);
				}
				else
				{
					UserManager.getUser(function(user){
						$("#warning-username").text(user.uname);
						$("#registering").hide("normal");
						$("#not-set-account-book").show("normal");
						$("#uid").val(user.uid);
						$("#login-collapse").collapse("show");
						$("#register-collapse").collapse("hide");
					});
				}
			});
	});
	
	$("#register-username").blur(function(){
		if($(this).val()!=""&&$(this).val()!=null)
			$("#register-username-ok").show();
		else
			$("#register-username-ok").hide();
	});
	
	$("#register-email").blur(function(){
		if(!isEmailAddress($(this).val()))
		{
			$("#register-not-email").show();
			$("#register-not-email").popover("show");
			setTimeout(function() {
				$("#register-not-email").popover("hide");
			}, 2500);
		}
		else
		{
			$("#register-not-email").hide();
			$("#register-not-email").popover("hide");
			UserManager.isEmailExist($(this).val(),function(data){
				if(data==true)
				{
					$("#register-email-not-exist").hide();
					$("#register-email-exist").show();
					$("#register-email-exist").popover("show");
				}		
				else
				{
					$("#register-email-exist").hide();
					$("#register-email-not-exist").show();
					$("#register-email-exist").popover("hide");
				}				
			});
		}
	});
	
	$("#register-password").blur(function(){
		if($(this).val()!=""&&$(this).val()!=null)
			$("#register-password-ok").show();
		else
			$("#register-password-ok").hide();
	});
	
	$("#register-submit").click(function(){
		var username=$("#register-username").val();
		var email=$("#register-email").val();
		var password=$("#register-password").val();
		var repeatPassword=$("#register-password-repeat").val();
		
		if(password!=repeatPassword)
		{			
			$("#register-password-not-equal").show();
			$("#register-password-not-equal").popover("show");
		}
		else
		{
			$("register-password-not-equal").hide();
			$("register-password-not-equal").popover("hide");
			if($("#register-username-ok").is(":visible")&&$("#register-password-ok").is(":visible")
					&&$("#register-email-not-exist").is(":visible")&&repeatPassword!=""&&repeatPassword!=null)
				UserManager.register(username,email,password,function(data){
					if(data>0)
					{
						$("#register-success-username").text(username);
						$("#registering").hide("normal");
						$("#register-success").show("normal");
						$("#uid").val(data);
					}
					else
					{
						
					}
				});
		}
	});
	
	$("#search-exchange-rate").click(function(){
		$("#load-other-functions").load("otherFunctions/exchangeRate.html");
	});
	
});

function previous()
{
	
}

function next()
{
	selectIconPage(1);
	SetManager.getIconSize(SYS_NULL_ID,0,function(size){
		var pageCount=size/3;
		if(size%3!=0)
			pageCount++;
		for(var i=2;i<=pageCount;i++)
			$("#offical-account-book-icons-ul").append("<li id='li-offical-"+i+"'><a href='javascript:void(0)'' onclick='selectIconPage("+i+")'>"+i+"</a></li>");
	});
	$("#register-success").hide("normal");
	$("#not-set-account-book").hide("normal");
	$("#create-account-book").show("normal");
}

function submitAccountBook()
{
	var abname=$("#abname").val();
	var iid=$("#iid").val();
	if(abname==null||abname==""||iid==null||iid=="")
	{
		$("#add-account-book-wrong").show();
		$("#add-account-book-wrong-content").popover("show");
	}
	else
	{
		$("#add-account-book-wrong").hide();
		$("#add-account-book-wrong-content").popover("hide");
		AccountBookManager.addAccountBookWithUid($("#uid").val(),abname,iid,function(abid){
			if(abid>SYS_NULL_ID)
			{
				$("#add-account-book-success-name").text(abname);
				$("#add-account-book-form").hide("normal");
				$(".modal-footer").hide("normal");
				$("#add-account-book-success").show("normal");			
				$("#create-account-book").hide("normal");
			}
			else
				$("#add-account-book-fail").show("normal");
		});
	}
}

function selectIconPage(pageNumber)
{
	$("#offical-account-book-icons-ul li").removeClass("active");
	$("#li-offical-"+pageNumber).addClass("active");
	SetManager.getIcon(SYS_NULL_ID,0,pageNumber-1,3,function(icons){
		$("#offical-account-book-icons-tr").empty();
		for(var i=0;i<icons.length;i++)
		{
			$("#offical-account-book-icons-tr").append("<td><img id='icon"+icons[i].iid+"' onclick='selectIcon("+icons[i].iid
						+")' class='img-thumbnail img-select' src='upload/"+SYS_NULL_ID+"/0/"+icons[i].iname+"' width='106px'></td>");
		}
	});
}

function selectIcon(iid)
{
	$(".img-select").removeClass("img-selected");
	$("#icon"+iid).addClass("img-selected");
	$("#iid").val(iid);
}
