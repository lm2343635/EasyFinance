$(document).ready(function(){
	//加载用户信息
	UserManager.getUser(function(user){
		$("#header-nickname").text(user.uname);
	    $("#user-info button").text(user.email);
	    var photoUid=user.uid;
	    if(user.photo.pid==SYS_NULL_ID)
	    	photoUid=SYS_NULL_ID;
	    $("#user-info img").attr("src","upload/"+photoUid+"/"+PHOTO_NO+"/"+user.photo.filename);
	    $("#circle-user-photo").attr("src","upload/"+photoUid+"/"+PHOTO_NO+"/"+user.photo.filename);
	});
	
	//绘制本周支出类别饼图
	ReportManager.getSpendClassificationGraphData(function(spend){
		var data=new Array();
		var colors=createRandomColors(spend.outflows.length);
		var count=0;
		var graphNull=true;
		for(var i in spend.outflows)
		{
			if(spend.outflows[i]!=0)
			{
				data[count]=
				{
					value:spend.outflows[i],
					color:colors[i],
					text:spend.categories[i]
				};
				count++;
				graphNull=false;
			}
		}
		if(graphNull)
		{
			$("#spend-classification-graph").remove();
			$("#spend-classification-graph-no-data").show();
		}
		else
			new Chart($("#spend-classification-graph").get(0).getContext("2d")).Pie(data);
	});
	
	//绘制本周收支趋势图
	ReportManager.getTrendGroupData(function(group){
		var graphNull=true;
		for(var i in group.earnData)
		{	
			if(group.earnData[i]!=0||group.spendData[i]!=0)
			{
				graphNull=false;
				break;
			}
		}
		if(graphNull)
		{
			$("#spend-earn-trend-graph").remove();
			$("#spend-earn-trend-graph-no-data").show();
		}
		else
		{
			var data = {
					labels : group.categories,
					datasets : [
						{
							fillColor : "rgba(0,255,255,0.5)",
							strokeColor : "rgba(0,204,255,1)",
							pointColor : "rgba(0,153,255,1)",
							pointStrokeColor : "#fff",
							data : group.earnData
						},
						{
							fillColor : "rgba(255,204,0,0.5)",
							strokeColor : "rgba(255,153,0,1)",
							pointColor : "rgba(255,51,0,1)",
							pointStrokeColor : "#fff",
							data : group.spendData
						}
					]
				};
			new Chart($("#spend-earn-trend-graph").get(0).getContext("2d")).Line(data);
		}
	});
	
	//设置账户信息
	AccountManager.getAccountCenterInfo(function(info) {
		$("#total-assets").text(info[0]);
		$("#total-liabilities").text(info[1]);
		$("#net-assets").text(info[2]);
		$("#finance-net-assets").text(info[2]);
	});
	
	//设置收支信息
	AccountBookManager.getAccountBookEarnSpendInfo(function(info){
		var symbol=["this-week-earn","this-week-spend","this-month-earn","this-month-spend","this-year-earn","this-year-spend"];
		for(var i in symbol)
			$("#"+symbol[i]).text(info[i]);
		$("#finance-this-month-spend").text(info[3]);
	});
	
	//绑定上传用户照片的点击事件
	$("#user-info img").click(function(){
		$("#upload-photo-modal").modal("show");
	});

	var otherFunctionsID=["album","export","import","diary","exchange-rate","synchronize"];
	var otherFunctionsPages=["album.html","export.html","import.html","diary.html","exchangeRate.html","synchronize.html"];
	//绑定其他功能的点击事件
	//绑定汇率查询点击时间
	$("#exchange-rate").click(function(){
		$("#load-other-functions").load("otherFunctions/exchangeRate.html");
	});
	
	//绑定账本相册点击时间
	$("#album").click(function(){
		UserManager.getUser(function(user){
			var uid=user.uid;
			//得到当前账本下的照片
			PhotoManager.getAccountBookAlbum(function(photos){
				var srcs=new Array();
				for(var i in photos)
					srcs[i]="upload/"+uid+"/"+PHOTO_NO+"/"+photos[i].filename;
				showPhotos(srcs);
			});
		});
	});

	//上传控件初始化，主要是设置上传参数，以及事件处理方法(回调函数)
	$("#upload-photo").fileupload({
        autoUpload: true,//是否自动上传
        url: "PhotoServlet?task=uploadUserPhoto",
        dataType: "json",
        acceptFileTypes: /^image\/(gif|jpeg|png)$/,
        done: function (e,data) {//设置文件上传完毕事件的回调函数
            $("#uploaded-photo").attr("src","upload/"+data.result.uid+"/"+PHOTO_NO+"/"+data.result.filename);
            $("#uploaded-photo-panel").show("normal");
            UserManager.refreshSession();
        },
		progressall: function (e, data) {
		    var progress=parseInt(data.loaded/data.total*100, 10);
		    $("#upload-photo-progress .progress-bar").css("width",progress+"%");
		    $("#upload-photo-progress .progress-bar").text(progress+"%");
		}
    });
	
	//关闭上传照片模态框后重置上传模态框，设置照片
	$("#upload-photo-modal").on("hidden.bs.modal",function(e){
		//设置照片
		$("#user-info img").attr("src",$("#uploaded-photo").attr("src"));
		 $("#circle-user-photo").attr("src",$("#uploaded-photo").attr("src"));
		//重置上传模态框
		resetUploadPhoto();	
	});
	
	//绑定重置上传模态框按钮点击事件
	$("#upload-photo-reset").click(function(){
		resetUploadPhoto();
	});
});

/**
 * 重置上传模态框，清除照片预览和进度条
 */
function resetUploadPhoto()
{
	$("#uploaded-photo-panel").hide("normal");
    $("#upload-photo-progress .progress-bar").css("width","0%");
    $("#upload-photo-progress .progress-bar").text("");
}