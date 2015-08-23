$(document).ready(function(){
	addFormDate(".classification-select-time",0);
	loadClassificationReportData(getThisMonthStart(), getThisMonthEnd());
	
	$("#classification-report-sbt").click(function(){
		var start=$("#classification-start-time").val();
		var end=$("#classification-end-time").val();
		if(start==null||start==""||end==null||end=="")
			showPopoverTip("#classification-time-empty", 3000);
		else
			loadClassificationReportData(start, end);
	});
});

/**
 * 加载分类报表数据
 * @param start 起始时间
 * @param end 结束时间
 */
function loadClassificationReportData(start,end)
{
	ReportManager.getClassificationReportData(start,end,function(data){
		var pieData=getPieDataAndSetTable(data, "#classification-report-table tbody");
		
		$("#classification-stats-chart-pie-out").highcharts({
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false
		    },
		    title: {
		        text: "Pie chart of Spend"
		    },
		    subtitle: {
		        text: data.subtitle
		    },
		    credits:{
		         enabled:false 
		    },
		    tooltip: {
			    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		    },
		    plotOptions: {
		        pie: {
		            allowPointSelect: true,
		            cursor: 'pointer',
		            dataLabels: {
		                enabled: true,
		                color: '#000000',
		                connectorColor: '#000000',
		                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		            }
		        }
		    },
		    series: [{
		        type: 'pie',
		        name: 'Browser share',
		        data: pieData.outData
		    }]
		});		
		
		$("#classification-stats-chart-pie-in").highcharts({
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false
		    },
		    title: {
		        text: "Pie chart of Earn"
		    },
		    subtitle: {
		        text: data.subtitle
		    },
		    credits:{
		         enabled:false 
		    },
		    tooltip: {
			    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		    },
		    plotOptions: {
		        pie: {
		            allowPointSelect: true,
		            cursor: 'pointer',
		            dataLabels: {
		                enabled: true,
		                color: '#000000',
		                connectorColor: '#000000',
		                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		            }
		        }
		    },
		    series: [{
		        type: 'pie',
		        name: 'Browser share',
		        data: pieData.inData
		    }]
		});		
		
		$("#classification-report-column").highcharts({
		    chart: {
		        type: 'column'
		    },
		    colors:["#00CCFF","#FF3300"],
		    title: {
		        text: 'Column Chart of Classification'
		    },
		    credits:{
		         enabled:false 
		    },
		    subtitle: {
		        text: data.subtitle
		    },
		    xAxis: {
		        categories: data.categories
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: 'Money($)'
		        }
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		            '<td style="padding:0"><b>{point.y:.1f} dollars</b></td></tr>',
		        footerFormat: '</table>',
		        shared: true,
		        useHTML: true
		    },
		    plotOptions: {
		        column: {
		            pointPadding: 0.2,
		            borderWidth: 0
		        }
		    },
		    series: [{
		        name: 'Earn',
		        data: data.inflows
		    }, {
		        name: 'Spend',
		        data: data.outflows
		    }
		]
		});
	});
}