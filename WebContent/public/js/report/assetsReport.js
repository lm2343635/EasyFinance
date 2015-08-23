$(document).ready(function() {
	addFormDate(".assets-select-time",0);
	loadAssetReportData(getThisMonthStart(), getThisMonthEnd(), 0);
});

function loadAssetReportData(start,end,type)
{
	ReportManager.getAssetsReportData(start,end,type,function(data){
		$('#assets-report-graphics').highcharts({
		    credits:{
		        enabled:false 
		    },
		    title: {
		        text: 'Assets Report ',
		        x: -20 //center
		    },
		    subtitle: {
		        text: '',
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
		        name: 'Net Assets',
		        data: data.netAssets
		    },
		    {
		        name:'Total Assets',
		        data: data.totalAssets
		    },
		    {
		        name:'Total Liabilities',
		        data: data.totalLiabilities
		    }
		     ]
		});		
	});
}