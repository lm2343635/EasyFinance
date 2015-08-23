$(document).ready(function(){
	loadClssificationSelects("select-classification");
	loadAccountSelects("select-account");
	loadShopSelects("select-shop");
	addFormDate(".earn-select-time",0);
	loadEarnSpendReportData(getThisMonthStart(),getThisMonthEnd(),0);
	
	$("#earn-report-sbt").click(function(){
		var start=$("#earn-start-time").val();
		var end=$("#earn-end-time").val();
		if(start==null||start==""||end==null||end=="")
			showPopoverTip("#time-empty", 3000);
		else
		{
			var type=parseInt($("#type").val());
			var starts=start.split("-");
			var ends=end.split("-");
			var validate=true;
			switch (type) 
			{
			case 0:
				if(starts[0]!=ends[0]||starts[1]!=ends[1]||starts[2]>=ends[2])
				{
					showPopoverTip("#unlawful-data-date", 7000);
					validate=false;
				}			
				break;
			case 1:
				if(starts[0]!=ends[0]||starts[1]>=ends[1])
				{
					showPopoverTip("#unlawful-data-month", 7000);
					validate=false;
				}					
				break;
			case 2:
				if(starts[0]>=ends[0])
				{
					showPopoverTip("#unlawful-data-year", 7000);
					validate=false;
				}
				break;
			default:
				validate=false;
				break;
			}
			if(validate)
				loadEarnSpendReportData(start, end, type);
		}
	});
});	

/**
 * 记载收入支出数据
 * @param start 起始时间
 * @param end 结束时间
 * @param type 显示时间类型
 */
function loadEarnSpendReportData(start,end,type)
{
	ReportManager.getEarnSpendReportData(0,0,0,type,start,end,function(data){
		//加载表格
		var allSpend=0;
		var allEarn=0;
		$("#earn-report-table tbody").empty();
		for(var i in data.categories)
		{
			var tr='	<tr>'+
							'<td>'+data.categories[i]+'</td>'+
							'<td>'+data.spendData[i]+'</td>'+
							'<td>'+data.earnData[i]+'</td>'+
						'</tr>';
			allSpend=parseInt(data.spendData[i]);
			allEarn=parseInt(data.earnData[i]);
			$("#earn-report-table tbody").append(tr);
		}
		var tr='	<tr class="success">'+
						'<td><strong>Total</strong></td>'+
						'<td><strong>'+allSpend+'</strong></td>'+
						'<td><strong>'+allEarn+'</strong></td>'+
					'</tr>';
		$("#earn-report-table tbody").append(tr);
		//加载统计图
		$('#earn-report-graphics').highcharts({
		    title: {
		        text: data.title,
		        x: -20 //center
		    },
		    colors:["#00CCFF","#FF3300"],
		    subtitle: {
		        text: data.subtitle,
		        x: -20
		    },
		    xAxis: {
		        categories: data.categories
		    },
		    yAxis: {
		        title: {
		            text: 'Money($)'
		        },
		        plotLines: [{
		            value: 0,
		            width: 1,
		            color: '#808080'
		        }]
		    },
		    legend: {
		        layout: 'vertical',
		        align: 'right',
		        verticalAlign: 'middle',
		        borderWidth: 0
		    },
		    series: [
		    {
		        name: 'Earn',
		        data: data.earnData
		    },
		    {
		    	name: 'Spend',
		        data: data.spendData
		    }
		     ],
		    credits:{
		         enabled:false
		    }
		});		
	});	
}

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