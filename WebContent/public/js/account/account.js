$(document).ready(function(){
	addFormDate(".checking-select-time",0);
	//加载账户中心信息
	AccountManager.getAccountCenterInfo(function(info) {
		$("#total-assets").text(info[0]);
		$("#total-liabilities").text(info[1]);
		$("#net-assets").text(info[2]);
	});
	//加载所有账户
	AccountManager.getAccount(function(accounts) {
		$("#account-list").empty();
		for(var i in accounts)
		{
			var a='<a id="account-'+accounts[i].aid+'" onclick="changeAccount('+accounts[i].aid+')" href="javascript:void(0)" class="list-group-item"><img src="upload/'
				+accounts[i].aicon.uid+'/1/'+accounts[i].aicon.iname+'">'+accounts[i].aname+'</a>';
			$("#account-list").append(a);
		}
		if(accounts.length>0)
			changeAccount(accounts[0].aid);	
	});
});

//绑定对账时间提交按钮点击事件
$("#checking-time-submit").click(function(){
	var start=$("#checking-start-time").val();
	var end=$("#checking-end-time").val();
	if(start==null||start==""||end==null||end=="")
		showPopoverTip("#time-empty", 3000);
	else
		accountChecking($("#aid").val(),start,end,$("#type").val());
});

/**
 * 更换当前正在查看的账本
 * @param aid
 */
function changeAccount(aid)
{
	$("#account-list a").removeClass("active");
	$("#account-"+aid).addClass("active");
	$("#aid").val(aid);
	//加载账户信息
	AccountManager.getAccountById(aid,function(account){
		$("#account-asset").text(account.ain-account.aout);
		$("#account-inflow").text(account.ain);
		$("#account-outflow").text(account.aout);
	});
	//加载对账单
	accountChecking(aid,getThisMonthStart(),getThisMonthEnd(),0);
}

function accountChecking(aid,start,end,type)
{
	AccountHistoryManager.getAccountHistories(start,end,type,aid,function(historeis){
		$("#checking-table tbody").empty();
		for(var i in historeis)
		{
			var tr='<tr>'+
							'<td>'+historeis[i].date+'</td>'+
							'<td>'+historeis[i].inflow+'</td>'+
							'<td>'+historeis[i].outflow+'</td>'+
							'<td>'+historeis[i].totalInflow+'</td>'+
							'<td>'+historeis[i].totalOutflow+'</td>'+
							'<td>'+historeis[i].surplus+'</td>'+
						'</tr>';
			$("#checking-table tbody").append(tr);
		}
	});
}

/**
 * 选择查看类型
 * @param type 按年查看：2 按月查看：1 按日查看：0
 */
function changeType(type,startID,endID)
{
	var text=["By Day","By Month","By Year"];
	$("#choose-type a span").text(text[type]);
	$(".start-time").empty();
	$(".end-time").empty();
	$(".datetimepicker").remove();
	$(".start-time").append('<input id="'+startID+'" type="text" readonly class="inputs form_datetime form-control select-time earn-select-time"  placeholder="Start Time">');
	$(".end-time").append('<input id="'+endID+'" type="text" readonly class="inputs form_datetime form-control select-time earn-select-time"  placeholder="End Time">');
	addFormDate(".earn-select-time",type);
	$("#type").val(type);
}

