//标签页加载页面
var adds=["addSpend","addEarn","addTransfer"];
//record-photo图片内外边框差
var RECORD_PHOTO_MARGIN=16;
//add的div宽度
var ADD_DIV_WIDTH=995;

$(document).ready(function(){   	
	//加载页面
	$("#"+adds[0]).load("tally/"+adds[0]+".html");
    $('.showInfo:eq(0)').addClass('show');    
    $("#record-panel").load("tally/record.html");
    $("#transfer-panel").load("tally/transfer.html");
    $("#transfer-panel").hide();
    //setAutoHeight("#tally-list", 410);
    
    //绑定照片上传组件
    $("#upload-record-photo").fileupload({
    	autoUpload:true,
    	url:"PhotoServlet?task=uploadRecordPhoto",
    	dataType:"json",
    	acceptFileTypes: /^image\/(gif|jpeg|png)$/,
    	done:function(e,data){
            $("#uploaded-record-photo").attr("src","upload/"+data.result.uid+"/"+PHOTO_NO+"/"+data.result.filename);
            $("#uploaded-record-photo-panel").show("normal");
            $("#pid").val(data.result.pid);
    	},
    	progressall:function(e,data){
		    var progress=parseInt(data.loaded/data.total*100, 10);
		    $("#upload-record-photo-progress .progress-bar").css("width",progress+"%");
		    $("#upload-record-photo-progress .progress-bar").text(progress+"%");
    	}
    });
});

//绑定重置上传模态框按钮点击事件
$("#upload-record-photo-reset").click(function(){
	resetUploadPhoto();
});

//关闭上传照片模态框后重置上传模态框，设置照片
$("#upload-record-photo-modal").on("hidden.bs.modal",function(e){
	resetUploadPhoto();
});

//绑定选择照片点击事件
$("#choose-from-album").click(function(){
	$("#upload-record-photo-modal .modal-dialog").addClass("modal-lg");
	$("#modal-body-upload").hide("normal");
	$("#modal-body-choose").show("normal");
	$(this).hide("normal");
	$("#back-to-upload").show("normal");
});

//绑定返回上传点击事件
$("#back-to-upload").click(function(){
	$("#upload-record-photo-modal .modal-dialog").removeClass("modal-lg");
	$("#modal-body-choose").hide("normal");
	$("#modal-body-upload").show("normal");
	$(this).hide("normal");
	$("#choose-from-album").show("normal");
});

//绑定保存图片按钮点击事件
$("#save-record-photo").click(function(){
	var pid=$("#pid").val();
	if(pid!=SYS_RECORD_PHOTO_NULL)
		loadRecordPhoto(pid);
	$("#upload-record-photo-modal").modal("hide");
});

//记账区域隐藏，显示
$("#add-display").click(function(){
	if($("#add-record-content").css("display")!="none")
	{
		$("#add-record").animate({"height":"55px"}, 400 , function(){
			$("#add-record-content").hide();
		});
		$("#tally-list").css("height",(getScreenHeight()-205)+"px");
		$(this).removeClass("glyphicon-chevron-up");
		$(this).addClass("glyphicon-chevron-down");	
	}
	else
	{
		$("#add-record").animate({"height":"260px"}, 400, function(){
			$("#add-record-content").show();
		});
		$("#tally-list").css("height",(getScreenHeight()-405)+"px");
		$(this).removeClass("glyphicon-chevron-down");	
		$(this).addClass("glyphicon-chevron-up");	
	}
});

//绑定标签页切换事件
$(".nav-pills li").click(function(){
	var This=this;
	checkSession(function(){
		$(".nav-pills li").removeClass("active");
		$(".showInfo").removeClass("show");
		$(This).addClass('active');
		var index=$('.nav-pills li').index(This);
		$("#"+adds[index]).load("tally/"+adds[index]+".html");
		$(".showInfo").eq(index).addClass('show');
		$("#pid").val(SYS_RECORD_PHOTO_NULL);
	});
});

$("#tally-list-type button").click(function(){
	var This=this;
	checkSession(function(){
		var ids=["record-panel","transfer-panel"];
		for(var i in ids)
			$("#"+ids[i]).hide();
		$("#"+ids[$(This).index()]).show();
		$("#tally-list-type button").removeClass("btn-success");
		$("#tally-list-type button").addClass("btn-default");
		$(This).addClass("btn-success");
	})
});

/**
 * 加载分类下拉菜单
 * @param selectID
 */
function loadClssificationSelects(selectID)
{
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

function loadTemplateSelect(selectID,classificationID,accountID,shopID)
{
	TemplateManager.getTemplate(function(templates){
		for(var i in templates)
		{
			var li=' <li><a href="javascript:void(0)" onclick="chooseTemplate('+templates[i].tpid+',\''+selectID+'\',\''+classificationID+'\',\''+accountID+'\',\''+shopID+'\')">'
				+templates[i].tpname+'</a></li>';
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

/**
 * 选择模板
 * @param tpid 模板id
 * @param selectID
 * @param classificationID 分类id
 * @param accountID 账户id
 * @param shopID 商家id
 */
function chooseTemplate(tpid,selectID,classificationID,accountID,shopID)
{
	TemplateManager.getTemplateById(tpid,function(template){
		$("#"+selectID+" .choosed-item span").text(template.tpname);
		$("#"+classificationID+" .choosed-item span").text(template.classification.cname);
		$("#"+classificationID+" .choosed-item img").attr("src","upload/"+template.classification.cicon.uid+"/2/"+template.classification.cicon.iname);
		$("#"+classificationID+" input").val(template.classification.cid);
		$("#"+accountID+" .choosed-item span").text(template.account.aname);
		$("#"+accountID+" .choosed-item img").attr("src","upload/"+template.account.aicon.uid+"/1/"+template.account.aicon.iname);
		$("#"+accountID+" input").val(template.account.aid);
		$("#"+shopID+" .choosed-item span").text(template.shop.sname);
		$("#"+shopID+" .choosed-item img").attr("src","upload/"+template.shop.sicon.uid+"/3/"+template.shop.sicon.iname);
		$("#"+shopID+" input").val(template.shop.sid);
	});
}

/**
 * 重置上传模态框，清除照片预览和进度条
 */
function resetUploadPhoto()
{
	$("#uploaded-record-photo-panel").hide("normal");
    $("#upload-record-photo-progress .progress-bar").css("width","0%");
    $("#upload-record-photo-progress .progress-bar").text("");
}

/**
 * 加载记录照片
 * @param pid
 */
function loadRecordPhoto(pid)
{
	UserManager.getUser(function(user){
		var uid=user.uid;
		//如果调用的是收支记录空照片，用户则为系统空用户
		if(pid==SYS_RECORD_PHOTO_NULL)
			uid=SYS_NULL_ID;
		PhotoManager.getPhotoById(pid,function(photo){
			$(".record-photo img").attr("src","upload/"+uid+"/"+PHOTO_NO+"/"+photo.filename);
			//必须等图片加载完成后才能用图片宽度去设置输入区域的宽度，只能绑定一次
			$(".record-photo img").one("load",function(){
				$(".info-left").width(ADD_DIV_WIDTH-RECORD_PHOTO_MARGIN-$(this).width());
			});			
		});
	});
}