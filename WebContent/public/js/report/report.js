var loadFiles=["earnSpendReport.html","accountReport.html","assetsReport.html","shopReport.html","classificationReport.html"];
var showTypes=["stats-chart-column","stats-chart-pie","stats-table","stats-chart-pie-in","stats-chart-pie-out"];

$(document).ready(function(){
	setAutoHeight("#report-content", 150);
	setTabs("report", loadFiles, "#report-content .showInfo", "#report-list-group");
});

function changeShowType(type)
{
	checkSession(function(){
		$("#choose-show-type li").removeClass("active");
		$("."+type+"-li").addClass("active");
		for(var i=0;i<showTypes.length;i++)
		{
			if(showTypes[i]!=type)
				$("."+showTypes[i]).hide();
			else
				$("."+showTypes[i]).show();
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

/**
 * 得到饼图数据并设置收支盈余表格数据
 * @param proportionReportData
 * @param tbodySelector
 * @returns {___anonymous4829_4883}
 */
function getPieDataAndSetTable(proportionReportData,tbodySelector)
{
	var pieInData=new Array();
	var pieOutData=new Array();
	$(tbodySelector).empty();
	var inAll=0;
	var outAll=0;
	for(var i in proportionReportData.categories)
	{
		pieInData[i]=[proportionReportData.categories[i],proportionReportData.inflows[i]];
		pieOutData[i]=[proportionReportData.categories[i],proportionReportData.outflows[i]];
		inAll+=proportionReportData.inflows[i];
		outAll+=proportionReportData.outflows[i];
		var tr='<tr>'+
						'<td>'+proportionReportData.categories[i]+'</td>'+
						'<td>'+proportionReportData.inflows[i]+'</td>'+
						'<td>'+proportionReportData.outflows[i]+'</td>'+
						'<td>'+(proportionReportData.inflows[i]-proportionReportData.outflows[i])+'</td>'+
					'</tr>';
		$(tbodySelector).append(tr);
	}
	var total='<tr class="warning">'+
						'<td>Total</td>'+
						'<td><strong>'+inAll+'</strong></td>'+
						'<td><strong>'+outAll+'</strong></td>'+
						'<td><strong>'+(inAll-outAll)+'</strong></td>'+
					'</tr>';
	$(tbodySelector).append(total);
	var pieData=
		{
			"inData":pieInData,
			"outData":pieOutData
		};
	return pieData;
}