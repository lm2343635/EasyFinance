$(document).ready(function(){
	//加载图标
	loadIcons("add-account", SYS_NULL_ID, ACCOUNT_NO, 4, 10, "offical-account-icons-ul", "li-offical","offical-account-icons-table",39);
	
	//绑定提交点击事件
	bindSubmit("add-account-submit",["aname","asset"], "add-account-wrong",function(vals,iid){
		if(isNum(vals[1]))
		{
			hidePopoverTip("#asset-not-number");
			AccountManager.addAccount(vals[0],iid,vals[1],function(aid){
				if(aid>0)
				{
					$(".add-success p span").text(vals[0]);
					$(".add-form").hide("normal");
					$(".modal-footer").hide("normal");
					$(".add-success").show("normal");		
					AccountManager.getAccountById(aid,function(account){
						var tr='<tr id="account-'+account.aid+'">'+
								  		'<td><img src="upload/'+account.aicon.uid+'/'+ACCOUNT_NO+'/'+account.aicon.iname+'"></td>'+
								  		'<td>'+account.aname+'</td>'+
								  		'<td>$'+account.ain+'</td>'+
								  		'<td>$'+account.aout+'</td>'+
								  		'<td>$'+account.asset+'</td>'+
								  		'<td><span onclick="deleteAccount('+account.aid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
								  '</tr>';
						$("#account-table tbody").append(tr);					
					});
				}
				else
					$(".add-fail").show("normal");
			});
		}
		else
			showPopoverTip("#asset-not-number", 2000);
	});
	
	//加载所有账户
	AccountManager.getAccount(function(accounts){
		for(var i in accounts)
		{
			var tr='<tr id="account-'+accounts[i].aid+'">'+
					  		'<td><img src="upload/'+accounts[i].aicon.uid+'/'+ACCOUNT_NO+'/'+accounts[i].aicon.iname+'"></td>'+
					  		'<td>'+accounts[i].aname+'</td>'+
					  		'<td>$'+accounts[i].ain+'</td>'+
					  		'<td>$'+accounts[i].aout+'</td>'+
					  		'<td>$'+accounts[i].asset+'</td>'+
					  		'<td><span onclick="deleteAccount('+accounts[i].aid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
					  '</tr>';
			$("#account-table tbody").append(tr);
		}
	});
});

/**
 * 删除账户
 * @param aid 账户id
 */
function deleteAccount(aid)
{
	AccountManager.deleteAccount(aid,function(){
		$("#account-"+aid).remove();
	});
}