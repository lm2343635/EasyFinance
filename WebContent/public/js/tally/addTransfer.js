$(document).ready(function(){
	addFormDateTime("#transfer-time");
	loadAccountSelects("choose-in-account");
	loadAccountSelects("choose-out-account");
	
	//新增转账记录提交按钮绑定点击事件
	$("#add-transfer-submit").click(function(){
		checkSession(function name() {
			var tfout=$("#transfer-tfout").val();
			var tfin=$("#transfer-tfin").val();
			var time=$("#transfer-time").val();
			var remark=$("#transfer-remark").val();
			var money=$("#transfer-money").val();
			if(money==null||money==""||!isNum(money)||tfout==SYS_NULL_ID||tfin==SYS_NULL_ID||tfin==tfout)
			{
				if(money==null||money==""||!isNum(money))
					$("#transfer-money").parent().addClass("has-error");		
				if(tfin==SYS_NULL_ID)
					flickerTip("#choose-in-account .choosed-item span", 1800, 300);	
				if(tfout==SYS_NULL_ID)
					flickerTip("#choose-out-account .choosed-item span", 1800, 300);
				if(tfin==tfout&&tfin!=SYS_NULL_ID)
				{
					flickerTip("#choose-in-account .choosed-item span", 1800, 300);	
					flickerTip("#choose-out-account .choosed-item span", 1800, 300);
				}
			}	
			else
			{
				$("#transfer-money").parent().removeClass("has-error");
				TallyManager.addTransfer(tfout,tfin,time,money,remark,function(tfid){
					if(tfid>0)
					{
						$(".add-tally-success").show("normal");
						$(".add-tally-table").hide("normal");
						AccountManager.getAccountById(tfout,function(outAccount){
							$("#tfout-icon-success").attr("src","upload/"+outAccount.aicon.uid+"/1/"+outAccount.aicon.iname);
							$("#tfout-name-success").text(outAccount.aname);
						});
						AccountManager.getAccountById(tfin,function(inAccount){
							$("#tfin-icon-success").attr("src","upload/"+inAccount.aicon.uid+"/1/"+inAccount.aicon.iname);
							$("#tfin-name-success").text(inAccount.aname);
						});
					}
					else
					{
						$(".add-tally-fail").show("normal");
						$(".add-tally-table").hide("normal");
					}
					loadTransfer($("#tf-year").val(), $("#tf-month").val());
				});
			}
		});
	});
	
	//返回按钮绑定点击事件
	$(".back-to-add-transfer").click(function(){
		checkSession(function(){
			$(".add-tally-fail").hide("normal");
			$(".add-tally-success").hide("normal");
			$("#transfer-tfout").val("");
			$("#transfer-tfin").val("");
			$("#transfer-time").val(getNowTime());
			$("#transfer-remark").val("");
			$("#transfer-money").val("");
			var selects=["choose-out-account","choose-in-account"];
			var words=["Select an Account","Select an Account"];
			for(var i in selects)
			{
				$("#"+selects[i]+" .choosed-item img").attr("src","");
				$("#"+selects[i]+" .choosed-item span").text(words[i]);
			}
			$(".add-tally-table").show("normal");
		});
	});
	
});