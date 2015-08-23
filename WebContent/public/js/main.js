$(document).ready(function(){
	checkSession(function(){
		//加载主页面
		$("#mainContent").load("basic.html");
		//记载导航条
		$("#navigation>li a").click(function(){
			if($(this).attr('href') != '#')
			{
				var This=this;
				checkSession(function(){
					$("#mainContent").load($(This).attr('href'));	
				});
			}
			return false;
		});
		//加载正在使用的账本
		loadUsingAccountBook();
		//加载所有账本
		AccountBookManager.getAccountBook(function(accountBooks){
			for(var i in accountBooks)
			{
				var li= '<li>'
							+'<a href="javascript:void(0)" onclick="changeUsingAccountBook('+accountBooks[i].abid+')">'
								+'<img class="img-rounded select-img" height="18px" src="upload/'+accountBooks[i].abicon.uid+'/0/'+accountBooks[i].abicon.iname+'">'
								+accountBooks[i].abname
							+'</a>'
						+'</li>';
				$("#select-account-books").prepend(li);
			}			
		});
	});
});

/**
 * 切换当前正在使用的账本
 * @param abid 账本id
 */
function changeUsingAccountBook(abid)
{
	checkSession(function(){
		AccountBookManager.setUsingAccountBook(abid,function(success){
			if(success)
			{
				loadUsingAccountBook();
				$("#mainContent").load("basic.html");
			}
		});
	});
}

/**
 * 加载正在使用的账本 
 */
function loadUsingAccountBook()
{
	AccountBookManager.getUsingAccountBook(function(usingAccountBook){
		$("#using-account-book span").text(usingAccountBook.abname);
		$("#using-account-book img").attr("src","upload/"+SYS_NULL_ID+"/0/"+usingAccountBook.abicon.iname);
	});
}