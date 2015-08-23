var MonthNameEn=["January","February","March","April","May","June"
                 ,"July","August","September","October","November","December"];

$(document).ready(function(){
    loadShowTypeClassification();
    loadShowTypeAccount();
    loadShowTypeShop();
    loadRecordTime(new Date().getFullYear());
    
    $("#record-last-year").click(function(){
    	var year=parseInt($("#record-year").text());
    	TallyManager.getRecordTime(year-2,function(tallyListTime){
    		if(tallyListTime.months.length==0)
    			$("#record-last-year").hide();
    	});
    	$("#record-next-year").show();
    	year--;
    	loadRecordTime(year);
    });
    
    $("#record-next-year").click(function(){
    	var year=parseInt($("#record-year").text());
    	TallyManager.getRecordTime(year+2,function(tallyListTime){
    		if(tallyListTime.months.length==0)
    			$("#record-next-year").hide();
    	});
    	$("#record-last-year").show();
    	year++;
    	loadRecordTime(year);
    });
});

/**
 * 加载记录列表的时间列表
 * @param year
 */
function loadRecordTime(year)
{
	TallyManager.getRecordTime(year,function(tallyListTime){
		$("#record-year").text(tallyListTime.year);
		var months=tallyListTime.months;
		$("#record-months").empty();
		for(var i in months)
		{
			var a='<a id="record-'+year+'-'+months[i]+'" href="javascript:void(0)" onclick="loadRecord('+year+','+months[i]
				+')" class="list-group-item">'+MonthNameEn[months[i]-1]+'</a>';
			$("#record-months").append(a);
		}		
		loadRecord(year,new Date().getMonth()+1);
	});
}

/**
 * 加载记录
 * @param year 年
 * @param month 月
 * @param cid 类别id
 * @param aid 账户id
 * @param sid 商家id
 * @param remark 备注
 */
function loadRecord(year,month)
{
	
	var cid=$("#cid").val();
	var aid=$("#aid").val();
	var sid=$("#sid").val();
	var remark=$("#remark").val();
	$("#record-months a").removeClass("active");
	$("#record-"+year+"-"+month).addClass("active");
	$("#year").val(year);
	$("#month").val(month);
	UserManager.getUser(function(user){
		TallyManager.getRecord(year,month,cid,aid,sid,remark,function(dateRecords){
			
			$("#record-list tbody").empty();
			$("#record-showing-month").text(MonthNameEn[month-1]);
			var spend=0;
			var earn=0;
			for(var i in dateRecords)
			{
				spend+=Math.abs(dateRecords[i].spend);
				earn+=Math.abs(dateRecords[i].earn);
				var tr='<tr><td colspan="7">'
					+'<span><strong class="text-primary">'+dateRecords[i].date+'</strong></span>	'
					+'<span class="earn-data text-success"> Earn : $'+Math.abs(dateRecords[i].earn)+' </span>'
					+'<span class="spend-data text-danger"> Spend : $'+Math.abs(dateRecords[i].spend)+' </span>'
					+'</td></tr>';
				$("#record-list tbody").append(tr);
				for(var j in dateRecords[i].records)
				{
					var record=dateRecords[i].records[j];
					var tr='';
					if(record.money>0)
						tr='<tr class="success" id>';
					else
						tr='<tr class="warning">';
					tr+=    '<td class="td-time"> '+record.time.format("hh:mm")+'</td>'+
							   '<td class="td-classification"> '+record.classification.cname+'</td>'+
							   '<td class="td-account"> '+record.account.aname+'</td>'+
							   '<td class="td-shop"> '+record.shop.sname+'</td>'+
							   '<td class="td-money"> $'+Math.abs(record.money);	 		   
					if(record.money>0)
						tr+=' (earn)';
					else
						tr+=' (spend)';
					tr+='</td>'+
							'<td class="td-remark"> '+record.remark+'</td>'+
							'<td class="td-photo">';
					if(record.photo.pid!=SYS_RECORD_PHOTO_NULL)
					{
						var src="upload/"+user.uid+"/"+PHOTO_NO+"/"+record.photo.filename;
						var preview="upload/"+user.uid+"/"+PHOTO_NO+"/"+getName(record.photo.filename)+".thumbnail.jpg";
						tr+='<img id="photo-'+record.photo.pid+'" class="img-circle record-photo-thumbnail" src="'+preview+'" onclick="showOnePhoto(\''+src+'\')">';
					}
					tr+='</td></tr>';
					$("#record-list tbody").append(tr);
				}
			}
			$("#record-earn").text(earn);
			$("#record-spend").text(spend);
		});
	});
}

function loadShowTypeClassification()
{
	ClassificationManager.getClassification(function(classifications){
		for(var i in classifications)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseShowTypeClassification('+classifications[i].cid+')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+classifications[i].cicon.uid+'/2/'+classifications[i].cicon.iname+'">'
				+classifications[i].cname
				+'</a></li>';
			$("#show-type-classification ul").prepend(li);
		}
	});
}

function loadShowTypeAccount()
{
	AccountManager.getAccount(function(accounts){
		for(var i in accounts)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseShowTypeAccount('+accounts[i].aid+')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+accounts[i].aicon.uid+'/1/'+accounts[i].aicon.iname+'">'
				+accounts[i].aname
				+'</a></li>';
			$("#show-type-account ul").prepend(li);
		}
	});
}

function loadShowTypeShop()
{
	ShopManager.getShop(function(shops){
		for(var i in shops)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseShowTypeShop('+shops[i].sid+')">'
				+'<img class="img-rounded select-img" height="18px" src="upload/'+shops[i].sicon.uid+'/3/'+shops[i].sicon.iname+'">'
				+shops[i].sname
				+'</a></li>';
			$("#show-type-shop ul").prepend(li);
		}
	});
}

/**
 * 选择类别
 * @param cid
 */
function chooseShowTypeClassification(cid)
{
	checkSession(function(){
		$("#cid").val(cid);
		if(cid==-1)
		{
			$("#show-type-classification .dropdown-toggle span").text("All Classification");
			$("#show-type-classification .dropdown-toggle img").attr("src","");
		}
		else if(cid==0)
		{
			$("#show-type-classification .dropdown-toggle span").text("Null Classification Items");
			$("#show-type-classification .dropdown-toggle img").attr("src","");
		}
		else
		{
			ClassificationManager.getClassificationById(cid,function(classification){
				$("#show-type-classification .dropdown-toggle span").text(classification.cname);
				$("#show-type-classification .dropdown-toggle img").attr("src","upload/"+classification.cicon.uid+"/2/"+classification.cicon.iname);
			});
		}	
		loadRecord($("#year").val(),$("#month").val());
	});
}

/**
 * 选择账户
 * @param aid
 */
function chooseShowTypeAccount(aid)
{
	checkSession(function(){
		$("#aid").val(aid);
		if(aid==-1)
		{
			$("#show-type-account .dropdown-toggle span").text("All Account");
			$("#show-type-account .dropdown-toggle img").attr("src","");
		}
		else if(aid==0)
		{
			$("#show-type-account .dropdown-toggle span").text("Null Account Items");
			$("#show-type-account .dropdown-toggle img").attr("src","");
		}
		else
		{
			AccountManager.getAccountById(aid,function(account){
				$("#show-type-account .dropdown-toggle span").text(account.aname);
				$("#show-type-account .dropdown-toggle img").attr("src","upload/"+account.aicon.uid+"/1/"+account.aicon.iname);
			});
		}
		loadRecord($("#year").val(),$("#month").val());
	});
}

/**
 * 选择商家
 * @param sid
 */
function chooseShowTypeShop(sid)
{
	checkSession(function(){
		$("#sid").val(sid);
		if(sid==-1)
		{
			$("#show-type-shop .dropdown-toggle span").text("All Shop");
			$("#show-type-shop .dropdown-toggle img").attr("src","");
		}
		else if(sid==0)
		{
			$("#show-type-shop .dropdown-toggle span").text("Null Shop Items");
			$("#show-type-shop .dropdown-toggle img").attr("src","");
		}
		else
		{
			ShopManager.getShopById(sid,function(shop){
				$("#show-type-shop .dropdown-toggle span").text(shop.sname);
				$("#show-type-shop .dropdown-toggle img").attr("src","upload/"+shop.sicon.uid+"/3/"+shop.sicon.iname);
			});
		}
		loadRecord($("#year").val(),$("#month").val());
	});
}

/**
 * 搜索备注
 * @returns {Boolean}
 */
function searchRemark()
{
	checkSession(function(){
		loadRecord($("#year").val(),$("#month").val());
	});
	return false;
}

/**
 * 显示所有记录
 */
function showAllRecord()
{
	checkSession(function(){
		$("#cid").val(-1);
		$("#show-type-classification .dropdown-toggle span").text("All Classification");
		$("#show-type-classification .dropdown-toggle img").attr("src","");
		$("#aid").val(-1);
		$("#show-type-account .dropdown-toggle span").text("All Account");
		$("#show-type-account .dropdown-toggle img").attr("src","");
		$("#sid").val(-1);
		$("#show-type-shop .dropdown-toggle span").text("All Shop");
		$("#show-type-shop .dropdown-toggle img").attr("src","");
		$("#remark").val("");
		loadRecord($("#year").val(),$("#month").val());
	});
}