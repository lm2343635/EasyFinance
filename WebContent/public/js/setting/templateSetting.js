$(document).ready(function(){
	//加载所有模板
	TemplateManager.getTemplate(function(templates){
		for(var i in templates)
		{
			var tr='<tr id="template-'+templates[i].tpid+'">'+
						   '<td>'+templates[i].tpname+'</td>'+
						   '<td><img src="upload/'+templates[i].classification.cicon.uid+'/'+CLASSIFICATION_NO+'/'+templates[i].classification.cicon.iname+'">'+templates[i].classification.cname+'</td>'+
						   '<td><img src="upload/'+templates[i].shop.sicon.uid+'/'+SHOP_NO+'/'+templates[i].shop.sicon.iname+'">'+templates[i].shop.sname+'</td>'+
						   '<td><img src="upload/'+templates[i].account.aicon.uid+'/'+ACCOUNT_NO+'/'+templates[i].account.aicon.iname+'">'+templates[i].account.aname+'</td>'+
						   '<td><span onclick="deleteTemplate('+templates[i].tpid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
					   '</tr>';
			$("#template-table tbody").append(tr);
		}
	});
	
	$("#add-template").on("show.bs.modal", function (e){
		loadClssificationSelects("template-classification");
		loadAccountSelects("template-account");
		loadShopSelects("template-shop");
	});
	
	//绑定新增模板提交按钮点击事件
	$("#add-template-submit").click(function(){
		var tpname=$("#tpname").val();
		if(tpname==null||tpname=="")
			showPopoverTip("#add-template-wrong", 2000);
		else
		{
			hidePopoverTip("#add-template-wrong");
			var cid=$("#template-cid").val();
			var aid=$("#template-aid").val();
			var sid=$("#template-sid").val();
			TemplateManager.addTemplate(tpname,cid,aid,sid,function(tpid){
				if(tpid>0)
				{
					$(".add-success p span").text(tpname);
					$(".add-form").hide("normal");
					$(".modal-footer").hide("normal");
					$(".add-success").show("normal");		
					TemplateManager.getTemplateById(tpid,function(template){
						var tr='<tr id="template-'+template.tpid+'">'+
									   '<td>'+template.tpname+'</td>'+
									   '<td><img src="upload/'+template.classification.cicon.uid+'/'+CLASSIFICATION_NO+'/'+template.classification.cicon.iname+'">'+template.classification.cname+'</td>'+
									   '<td><img src="upload/'+template.shop.sicon.uid+'/'+SHOP_NO+'/'+template.shop.sicon.iname+'">'+template.shop.sname+'</td>'+
									   '<td><img src="upload/'+template.account.aicon.uid+'/'+ACCOUNT_NO+'/'+template.account.aicon.iname+'">'+template.account.aname+'</td>'+
									   '<td><span onclick="deleteTemplate('+template.tpid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
								   '</tr>';
						$("#template-table tbody").append(tr);
					});
				}
				else
					$(".add-fail").show("normal");
			});
		}
	});
	
	$(".add-success button").click(function(){
		$("#tpname").val("");	
		$("#template-aid").val("");
		$("#template-cid").val("");
		$("#template-sid").val("");
		var selects=["template-classification","template-account","template-shop"];
		var words=["Select a Classification","Select an Account","Select a Shop"];
		for(var i in selects)
		{
			$("#"+selects[i]+" .choosed-item img").attr("src","");
			$("#"+selects[i]+" .choosed-item span").text(words[i]);
		}
		$(".add-form").show("normal");
		$(".modal-footer").show("normal");
		$(".add-success").hide("normal");		
	});
});

/**
 * 加载分类下拉菜单
 * @param selectID
 */
function loadClssificationSelects(selectID)
{
	$("#"+selectID+" ul").empty();
	ClassificationManager.getClassification(function(classifications){
		for(var i in classifications)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseClassification('+classifications[i].cid+',\''+selectID+'\')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+classifications[i].cicon.uid+'/2/'+classifications[i].cicon.iname+'">'
				+classifications[i].cname
				+'</a></li>';
			$("#"+selectID+" ul").prepend(li);
		}
	});
}

/**
 * 加载商家下拉菜单
 * @param selectID
 */
function loadShopSelects(selectID)
{
	$("#"+selectID+" ul").empty();
	ShopManager.getShop(function(shops){
		for(var i in shops)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseShop('+shops[i].sid+',\''+selectID+'\')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+shops[i].sicon.uid+'/3/'+shops[i].sicon.iname+'">'
				+shops[i].sname
				+'</a></li>';
			$("#"+selectID+" ul").prepend(li);
		}
	});
}

/**
 * 加载账户下拉菜单
 * @param selectID
 */
function loadAccountSelects(selectID)
{
	$("#"+selectID+" ul").empty();
	AccountManager.getAccount(function(accounts){
		for(var i in accounts)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseAccount('+accounts[i].aid+',\''+selectID+'\')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+accounts[i].aicon.uid+'/1/'+accounts[i].aicon.iname+'">'
				+accounts[i].aname
				+'</a></li>';
			$("#"+selectID+" ul").prepend(li);
		}
	});
}

/**
 * 选择分类
 * @param cid
 * @param selectID
 * @returns
 */
function chooseClassification(cid,selectID)
{
	ClassificationManager.getClassificationById(cid,function(classification){
		$("#"+selectID+" .choosed-item span").text(classification.cname);
		$("#"+selectID+" .choosed-item img").attr("src","upload/"+classification.cicon.uid+"/2/"+classification.cicon.iname);
		$("#"+selectID+" input").val(cid);
	});
}

/**
 * 选择商家
 * @param sid
 * @param selectID
 * @returns
 */
function chooseShop(sid,selectID)
{
	ShopManager.getShopById(sid,function(shop){
		$("#"+selectID+" .choosed-item span").text(shop.sname);
		$("#"+selectID+" .choosed-item img").attr("src","upload/"+shop.sicon.uid+"/3/"+shop.sicon.iname);
		$("#"+selectID+" input").val(sid);
	});
}

/**
 * 选择账户
 * @param aid
 * @param selectID
 */
function chooseAccount(aid,selectID)
{
	AccountManager.getAccountById(aid,function(account){
		$("#"+selectID+" .choosed-item span").text(account.aname);
		$("#"+selectID+" .choosed-item img").attr("src","upload/"+account.aicon.uid+"/1/"+account.aicon.iname);
		$("#"+selectID+" input").val(aid);
	});
}

function deleteTemplate(tpid)
{
	TemplateManager.deleteTemplate(tpid,function(){
		$("#template-"+tpid).remove();
	});
}