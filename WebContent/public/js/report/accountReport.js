$(document).ready(function(){
	addFormDate(".account-select-time",0);
	loadAccountReportData(getThisMonthStart(), getThisMonthEnd());
	
	$("#account-report-sbt").click(function() {
		var start=$("#account-start-time").val();
		var end=$("#account-end-time").val();
		if(start==null||start==""||end==null||end=="")
			showPopoverTip("#account-time-empty", 3000);
		else
			loadAccountReportData(start, end);
	});
});

/**
 * 加载账户报表数据
 * @param start 起始时间
 * @param end 结束时间
 */
function loadAccountReportData(start,end)
{
	ReportManager.getAccountReportData(start,end,function(data){
		$('#account-report-column').highcharts({
		    chart: {
		        type: 'column'
		    },
		    colors:["#00CCFF","#FF3300"],
		    title: {
		        text: 'Column Chart of Account'
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
		        name: 'Inflows',
		        data: data.inflows
		    }, {
		        name: 'Outflows',
		        data: data.outflows
		    }
		]
		});
		
		var pieData=getPieDataAndSetTable(data, "#account-report-table tbody");
		
		$('#account-stats-chart-pie-in').highcharts({
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false
		    },
		    title: {
		        text: 'Account Inflows'
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
		
		$('#account-stats-chart-pie-out').highcharts({
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false
		    },
		    title: {
		        text: 'Account Outflows'
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
	});
}