$(document).ready(function(){
	//格式化时间选择器
	addFormDateTime("#spend-time");
	//加载分类
	loadClssificationSelects("choose-spend-classification");
	//加载商家
	loadShopSelects("choose-spend-shop");
	//加载账户
	loadAccountSelects("choose-spend-account");
	//加载模板
	loadTemplateSelect("choose-spend-template","choose-spend-classification","choose-spend-account","choose-spend-shop");
	 //加载系统收支记录空照片
	loadRecordPhoto(SYS_RECORD_PHOTO_NULL);
	
	//新增支出提交按钮绑定点击事件
	$("#add-spend-submit").click(function(){
		checkSession(function(){
			var aid=$("#spend-aid").val(); 
			var cid=$("#spend-cid").val();
			var sid=$("#spend-sid").val();
			var time=$("#spend-time").val();
			var remark=$("#spend-remark").val();
			var money=$("#spend-money").val();
			var pid=$("#pid").val()
			if(money==null||money==""||!isNum(money)||aid==SYS_NULL_ID||cid==SYS_NULL_ID||sid==SYS_NULL_ID)
			{
				if(money==null||money==""||!isNum(money))
					$("#spend-money").parent().addClass("has-error");
				if(aid==SYS_NULL_ID)
					flickerTip("#choose-spend-account .choosed-item span", 1800, 300);
				if(cid==SYS_NULL_ID)
					flickerTip("#choose-spend-classification .choosed-item span", 1800, 300);
				if(sid==SYS_NULL_ID)
					flickerTip("#choose-spend-shop .choosed-item span", 1800, 300);
			}
			else
			{
				$("#spend-money").parent().removeClass("has-error");
				TallyManager.addSpend(cid,aid,sid,time,money,remark,pid,function(rid){
					if(rid>0)
					{
						$(".add-tally-success").show("normal");
						$(".add-tally-table").hide("normal");
						$("#spend-money-success").text(money);
						$("#spend-time-success").text(time);
					}
					else
					{
						$(".add-tally-fail").show("normal");
						$(".add-tally-table").hide("normal");
					}
					loadRecord($("#year").val(),$("#month").val());
				});
			}
		});	
	});

	//为返回按钮绑定点击事件
	$(".back-to-add-spend").click(function(){
		checkSession(function() {
			$(".add-tally-fail").hide("normal");
			$(".add-tally-success").hide("normal");
			$("#spend-aid").val(SYS_NULL_ID);
			$("#spend-cid").val(SYS_NULL_ID);
			$("#spend-sid").val(SYS_NULL_ID);
			$("#spend-time").val(getNowTime());
			$("#spend-remark").val("");
			$("#spend-money").val("");
			$("#pid").val(SYS_RECORD_PHOTO_NULL);
			var selects=["choose-spend-classification","choose-spend-account","choose-spend-shop","choose-spend-template"];
			var words=["Select a Classification","Select an Account","Select a Shop","Select a Template"];
			for(var i in selects)
			{
				$("#"+selects[i]+" .choosed-item img").attr("src","");
				$("#"+selects[i]+" .choosed-item span").text(words[i]);
			}
			loadRecordPhoto(SYS_RECORD_PHOTO_NULL);
			$(".add-tally-table").show("normal");
		});
	});
	
	//绑定上传照片点击按钮点击时间
	$("#spend-photo img").click(function(){
		$("#upload-record-photo-modal").modal("show");
	});
});