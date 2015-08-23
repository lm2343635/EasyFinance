//一页最多放六个
var MAX_NUMBER_A_PAGE=6;

$(document).ready(function(){
	//加载第一页账本
	selectAccountBookPage(1);
	//得到账本的数量，并加载页码
	loadPageNumber(1,function(){});
});

$("#add-account-book").on("show.bs.modal", function (e) {
	selectIconPage(1);
	SetManager.getIconSize(SYS_NULL_ID,ACCOUNT_BOOK_NO,function(size){
		var pageCount=size/3;
		if(size%3!=0)
			pageCount++;
		$("#offical-account-book-icons-ul").empty();
		var li='<li class="active" id="li-offical-1"><a href="javascript:void(0)" onclick="selectIconPage(1)">1</a></li>';
		$("#offical-account-book-icons-ul").append(li);
		for(var i=2;i<=pageCount;i++)
		{
			var li="<li id='li-offical-"+i+"'><a href='javascript:void(0)'' onclick='selectIconPage("+i+")'>"+i+"</a></li>";
			$("#offical-account-book-icons-ul").append(li);
		}		
	});
});

//绑定新增账本提价按钮点击事件
$("#add-account-book-submit").click(function(){
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
		var length=$(".label").length;
		AccountBookManager.addAccountBook(abname,iid,function(abid){
			if(abid>0)
			{
				$("#add-account-book-success-name").text(abname);
				$("#add-account-book-form").hide("normal");
				$(".modal-footer").hide("normal");
				$("#add-account-book-success").show("normal");		
				AccountBookManager.getAccountBookSize(function(size){
					var pageCount=parseInt(size/6); 
					//如果label 有六个说明该页已经放满了，继续添加的应该拿到下一页，应该重新加载页码。?????
					if(length==MAX_NUMBER_A_PAGE)
					{
						//之所以要用回调，是因为loadPageNumber必须在selectAccountBookPage之前执行，这里由loadPageNumber来控制selectAccountBookPage的执行事件
						//否则页码没有增加，没法选择最新的那一页
						loadPageNumber(pageCount+2,function(){
							selectAccountBookPage(pageCount+1);
						});
					}
					else
						selectAccountBookPage(pageCount+1);
				});

			}
			else
				$("#add-account-book-fail").show("normal");
		});
	}
});

$("#add-account-book-success").click(function(){
	$("#abname").val("");
	$("#iid").val("");		
	$(".modal-footer").show("normal");
	$("#add-account-book-success").hide("normal");		
	$("#add-account-book-form").show("normal");
});

/**
 * 选择页数
 * @param pageNumber 页码
 */
function selectIconPage(pageNumber)
{
	$("#offical-account-book-icons-ul li").removeClass("active");
	$("#li-offical-"+pageNumber).addClass("active");
	SetManager.getIcon(SYS_NULL_ID,ACCOUNT_BOOK_NO,pageNumber-1,3,function(icons){
		$("#offical-account-book-icons-tr").empty();
		for(var i=0;i<icons.length;i++)
		{
			$("#offical-account-book-icons-tr").append("<td><img id='icon"+icons[i].iid+"' onclick='selectIcon("+icons[i].iid
						+")' class='img-thumbnail img-select' src='upload/"+SYS_NULL_ID+"/"+ACCOUNT_BOOK_NO+"/"+icons[i].iname+"'></td>");
		}
	});
}

/**
 * 选择图标
 * @param iid 图标id
 */
function selectIcon(iid)
{
	$(".img-select").removeClass("img-selected");
	$("#icon"+iid).addClass("img-selected");
	$("#iid").val(iid);
}

/**
 * 选择账本页码
 * @param pageNumber 页码
 */
function selectAccountBookPage(pageNumber)
{
	$("#account-book-page-ul li").removeClass("active");
	$("#account-book-page-"+pageNumber).addClass("active");
	$("#account-books").empty();
	AccountBookManager.getUsingAccountBook(function(usingAccountBook){
		AccountBookManager.getAccountBook(pageNumber-1,MAX_NUMBER_A_PAGE,function(accountBooks){
			var tr='<tr>';
			for(var i in accountBooks)
			{
				tr+="<td title='Click the name of Account Book to choose what you want to use.' class='td-select' id='account-book-"+accountBooks[i].abid+"' style='background-image: url(upload/"+SYS_NULL_ID+"/"+ACCOUNT_BOOK_NO+"/"+accountBooks[i].abicon.iname+");'>"+
							"<p class='text-center' title='Choose this Account Book as your using Account Book.'><span class='label label-info' onclick='changeUsingAccountBook("+accountBooks[i].abid+")'>"+accountBooks[i].abname+"</span></p>"+
							"<span title='Edit this Account Book' onclick='modifyAccountBook("+accountBooks[i].abid+")' class='glyphicon glyphicon-edit modify-account-book'></span>";		
				//如果该账本是正在使用的账本，就要加水印，并去掉删除按钮，正在使用的账本是不能被删除的
				if(accountBooks[i].abid==usingAccountBook.abid)
					tr+="<div><img class='img-circle account-book-seleted' src='public/images/suite_selected_indicator.png'/></div>";
				else
					tr+="<a data-trigger='confirm' href='javascript:void(0)' onclick='deleteAccountBook("+accountBooks[i].abid+")'><span title='Delete this Account Book.'  class='glyphicon glyphicon-trash delete-account-book'></span></a>";	
				tr+="</td>";
				if((i+1)%3==0)
				{
					tr+='</tr>';
					$("#account-books").append(tr);
					tr='<tr>';
				}
				if((i+1)%3!=0&&i==accountBooks.length-1)
				{
					for(var j=(i+1)%3;j<3;j++)
						tr+="<td title='Choose Account Book' class='td-select'></td>";
					$("#account-books").append(tr);
				}
			}
		});
	});
}


/**
 * 更换当前使用的账本
 * @param abid 账本id
 */
function changeUsingAccountBook(abid)
{
	AccountBookManager.getUsingAccountBook(function(usingAccountBook){
		var a="<a data-trigger='confirm' href='javascript:void(0)' onclick='deleteAccountBook("+usingAccountBook.abid
			+")'><span title='Delete this Account Book.'  class='glyphicon glyphicon-trash delete-account-book'></span></a>";
		$("#account-book-"+usingAccountBook.abid).append(a);
		AccountBookManager.setUsingAccountBook(abid,function(success){
			if(success)
			{
				loadUsingAccountBook();
				$(".td-select div").remove();
				var div="<div><img class='img-circle account-book-seleted' src='public/images/suite_selected_indicator.png'/></div>";
				$("#account-book-"+abid).append(div);
				$("#account-book-"+abid+" a").remove();
			}		
		});
	});	
}

/**
 * 得到账本的数量，并加载页码
 * @param usingPageNumber 当前使用的页码
 */
function loadPageNumber(usingPageNumber,callback)
{
	AccountBookManager.getAccountBookSize(function(size){
		var pageCount=parseInt(size/MAX_NUMBER_A_PAGE); 
		if(size%MAX_NUMBER_A_PAGE!=0)
			pageCount++;
		$("#account-book-page-ul").empty();
		for(var i=1;i<=pageCount;i++)
		{
			var li='<li ';
			if(usingPageNumber==i)
				li+='class="active"';
			li+=' id="account-book-page-'+i+'"><a href="javascript:void(0)" onclick="selectAccountBookPage('+i+')">'+i+'</a></li>'
			$("#account-book-page-ul").append(li);
		}		
		callback();
	});
}

/**
 * 删除账本（有点问题？？删除后应该定位到当前页）
 * @param abid 账本id
 */
function deleteAccountBook(abid)
{
	var tip="Warning! This operation may delete the data you recorded in this account book. Are you sure to detele this account book?";
	var length=$(".label").length;
	modalComfirm(tip,function(){
		AccountBookManager.deleteAccountBook(abid,function(){
			AccountBookManager.getAccountBookSize(function(size){
				var pageCount=parseInt(size/6); 
				if(length==1)
				{
					loadPageNumber(pageCount,function(){
						selectAccountBookPage(pageCount);
					});
				}
				else
					selectAccountBookPage(pageCount+1);
			});
		});
	});
}

function modifyAccountBook(abid)
{
	console.log(abid);
}