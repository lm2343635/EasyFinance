$(document).ready(function(){
	//格式化时间选择器
	addFormDateTime("#earn-time");
	//加载分类
	loadClssificationSelects("choose-earn-classification");
	//加载商家
	loadShopSelects("choose-earn-shop");
	//加载账户
	loadAccountSelects("choose-earn-account");
	//加载模板
	loadTemplateSelect("choose-earn-template","choose-earn-classification","choose-earn-account","choose-earn-shop");
	 //加载系统收支记录空照片
	loadRecordPhoto(SYS_RECORD_PHOTO_NULL);
	
	//新增收入提交按钮绑定点击事件
	$("#add-earn-submit").click(function(){
		checkSession(function name() {
			var aid=$("#earn-aid").val();
			var cid=$("#earn-cid").val();
			var sid=$("#earn-sid").val();
			var time=$("#earn-time").val();
			var remark=$("#earn-remark").val();
			var money=$("#earn-money").val();
			var pid=$("#pid").val()
			if(money==null||money==""||!isNum(money)||aid==SYS_NULL_ID||cid==SYS_NULL_ID||sid==SYS_NULL_ID)		
			{
				if(money==null||money==""||!isNum(money))
					$("#earn-money").parent().addClass("has-error");
				if(aid==SYS_NULL_ID)
					flickerTip("#choose-earn-account .choosed-item span", 1800, 300);
				if(cid==SYS_NULL_ID)
					flickerTip("#choose-earn-classification .choosed-item span", 1800, 300);
				if(sid==SYS_NULL_ID)
					flickerTip("#choose-earn-shop .choosed-item span", 1800, 300);
			}
			else
			{
				$("#earn-money").parent().removeClass("has-error");
				TallyManager.addEarn(cid,aid,sid,time,money,remark,pid,function(rid){
					if(rid>0)
					{
						$(".add-tally-success").show("normal");
						$(".add-tally-table").hide("normal");
						$("#earn-money-success").text(money);
						$("#earn-time-success").text(time);
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

	//绑定返回新增收入按钮点击事件
	$(".back-to-add-earn").click(function(){
		checkSession(function name() {
			$(".add-tally-fail").hide("normal");
			$(".add-tally-success").hide("normal");
			$("#earn-aid").val(SYS_NULL_ID);
			$("#earn-cid").val(SYS_NULL_ID);
			$("#earn-sid").val(SYS_NULL_ID);
			$("#earn-time").val(getNowTime());
			$("#earn-remark").val("");
			$("#earn-money").val("");
			$("#pid").val(SYS_RECORD_PHOTO_NULL);
			var selects=["choose-earn-classification","choose-earn-account","choose-earn-shop","choose-earn-template"];
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
	$("#earn-photo img").click(function(){
		$("#upload-record-photo-modal").modal("show");
	});
});