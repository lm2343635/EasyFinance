$(document).ready(function() {
	addFormDate(".shop-select-time",0);
	loadShopReportData(getThisMonthStart(), getThisMonthEnd());
	
//	$("#shop-report-sbt").click(function(){
//		var start=$("#shop-start-time").val();
//		var end=$("#shop-end-time").val();
//		if(start==null||start==""||end==null||end=="")
//			showPopoverTip("#shop-time-empty", 3000);
//		else
//			loadShopReportData(start, end);
//	});
});

/**
 * 加载商家报表数据
 * @param start
 * @param end
 */
function loadShopReportData(start,end)
{
	ReportManager.getShopReportData(start,end,function(data){
		var pieData=getPieDataAndSetTable(data, "#shop-report-table tbody");

		$("#shop-stats-chart-pie-out").highcharts({
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
		
		$("#shop-stats-chart-pie-in").highcharts({
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
		
		$("#shop-report-column").highcharts({
		    chart: {
		        type: 'column'
		    },
		    colors:["#00CCFF","#FF3300"],
		    title: {
		        text: 'Column Chart of Shop'
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