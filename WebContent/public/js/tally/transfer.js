var MonthNameEn=["January","February","March","April","May","June"
                 ,"July","August","September","October","November","December"];

$(document).ready(function(){
	loadShowTypeAccount();
	loadTransferTime(new Date().getFullYear());
	
	$("#transfer-last-year").click(function(){
    	var year=parseInt($("#transfer-year").text());
    	TallyManager.getTransferTime(year-2,function(tallyListTime){
    		if(tallyListTime.months.length==0)
    			$("#transfer-last-year").hide();
    	});
    	$("#transfer-next-year").show();
    	year--;
    	loadTransferTime(year);
    });
    
    $("#transfer-next-year").click(function(){
    	var year=parseInt($("#transfer-year").text());
    	TallyManager.getTransferTime(year+2,function(tallyListTime){
    		if(tallyListTime.months.length==0)
    			$("#transfer-next-year").hide();
    	});
    	$("#transfer-last-year").show();
    	year++;
    	loadTransferTime(year);
    });
});

function showRecord()
{
	$("#tally-list .panel-body").load("tally/record.html");
}

function loadShowTypeAccount()
{
	AccountManager.getAccount(function(accounts){
		for(var i in accounts)
		{
			var li_in=' <li><a href="javascript:void(0)" onclick="chooseAccountIn('+accounts[i].aid+')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+accounts[i].aicon.uid+'/1/'+accounts[i].aicon.iname+'">'
				+accounts[i].aname
				+'</a></li>';
			var li_out=' <li><a href="javascript:void(0)" onclick="chooseAccountOut('+accounts[i].aid+')">'
			+'<img class="img-rounded select-img" height="18px" src="upload/'+accounts[i].aicon.uid+'/1/'+accounts[i].aicon.iname+'">'
			+accounts[i].aname
			+'</a></li>';
			$("#show-type-account-out ul").prepend(li_out);
			$("#show-type-account-in ul").prepend(li_in);
		}
	});
}

/**
 * 加载转账列表的时间列表
 * @param year
 */
function loadTransferTime(year)
{
	TallyManager.getTransferTime(year,function(tallyListTime){
		$("#transfer-year").text(tallyListTime.year);
		var months=tallyListTime.months;
		$("#transfer-months").empty();
		for(var i in months)
		{
			var a='<a id="transfer-'+year+'-'+months[i]+'" href="javascript:void(0)" onclick="loadTransfer('+year+','+months[i]
				+',-1,-1,null)" class="list-group-item">'+MonthNameEn[months[i]-1]+'</a>';
			$("#transfer-months").append(a);
		}
		loadTransfer(year, new Date().getMonth()+1);
	});
	TallyManager.getTransferTime(year-1,function(tallyListTime){
		if(tallyListTime.months.length==0)
			$("#transfer-last-year").hide();
	});
	TallyManager.getTransferTime(year+1,function(tallyListTime){
		if(tallyListTime.months.length==0)
			$("#transfer-next-year").hide();
	});
}

/**
 * 加载转账列表
 * @param year 年
 * @param month 月
 * @param tfout 转出账户
 * @param tfin 转入账户
 * @param remark 备注
 */
function loadTransfer(year,month)
{
	var tfout=$("#tfout").val();
	var tfin=$("#tfin").val()
	var remark=$("#tf-remark").val();
	$("#transfer-months a").removeClass("active");
	$("#transfer-"+year+"-"+month).addClass("active");
	$("#tf-year").val(year);
	$("#tf-month").val(month);
	TallyManager.getTransfer(year,month,tfout,tfin,remark,function(dateTransfers){
		$("#transfer-list tbody").empty();
		$("#transfer-showing-month").text(MonthNameEn[month-1]);
		var allMoney=0;
		for(var i in dateTransfers)
		{
			allMoney+=dateTransfers[i].money;
			var tr='<tr><td colspan="5">'
				+'<span><strong class="text-primary">'+dateTransfers[i].date+'</strong></span>	'
				+'<span class="earn-data text-success"> Transfer Money : $'+dateTransfers[i].money+' </span>'
				+'</td></tr>';
			$("#transfer-list tbody").append(tr);
			for(var j in dateTransfers[i].transfers)
			{
				var transfer=dateTransfers[i].transfers[j];
				var tr='<tr class="warning">';
					tr+=    '<td class="td-time"> '+transfer.time.format("hh:mm")+'</td>'
					+'<td class="td-account"> '+transfer.tfout.aname+'</td>'
					+'<td class="td-account"> '+transfer.tfin.aname+'</td>'
					+'<td class="td-money"> $'+transfer.money+'</td>'
					+'<td class="td-remark"> '+transfer.remark+'</td>'
				+'</tr>';
				$("#transfer-list tbody").append(tr);
			}
		}
		$("#all-transfer-money").text(allMoney);
	});
}

/**
 * 选择转出账户
 * @param aid
 */
function chooseAccountOut(aid)
{
	$("#tfout").val(aid);
	if(aid==-1)
	{
		$("#show-type-account-out .dropdown-toggle span").text("All Transfer Out");
		$("#show-type-account-out .dropdown-toggle img").attr("src","");
	}
	else
	{
		AccountManager.getAccountById(aid,function(account){
			$("#show-type-account-out .dropdown-toggle span").text(account.aname);
			$("#show-type-account-out .dropdown-toggle img").attr("src","upload/"+account.aicon.uid+"/1/"+account.aicon.iname);
		});
	}
	loadTransfer($("#tf-year").val(), $("#tf-month").val());
}

/**
 * 选择转入账户
 * @param aid
 */
function chooseAccountIn(aid)
{
	$("#tfin").val(aid);
	if(aid==-1)
	{
		$("#show-type-account-in .dropdown-toggle span").text("All Transfer In");
		$("#show-type-account-in .dropdown-toggle img").attr("src","");
	}
	else
	{
		AccountManager.getAccountById(aid,function(account){
			$("#show-type-account-in .dropdown-toggle span").text(account.aname);
			$("#show-type-account-in .dropdown-toggle img").attr("src","upload/"+account.aicon.uid+"/1/"+account.aicon.iname);
		});
	}
	loadTransfer($("#tf-year").val(), $("#tf-month").val());
}

/**
 * 搜索备注
 * @returns {Boolean}
 */
function transferRemark()
{
	loadTransfer($("#tf-year").val(), $("#tf-month").val());
	return false;
}

/**
 * 显示所有转账记录
 */
function showAllTransfer()
{
	$("#tfout").val(-1);
	$("#show-type-account-out .dropdown-toggle span").text("All Transfer Out");
	$("#show-type-account-out .dropdown-toggle img").attr("src","");
	$("#tfin").val(-1);
	$("#show-type-account-in .dropdown-toggle span").text("All Transfer In");
	$("#show-type-account-in .dropdown-toggle img").attr("src","");
	$("#tf-remark").val("");
	loadTransfer($("#tf-year").val(), $("#tf-month").val());
}