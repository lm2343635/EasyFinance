//定义所有货币
var ALL="All Currency";
//全局搜索剩余数量
var lostSearchCount;
//全局图表数据存储变量
var chartData;

$(document).ready(function(){
	//显示模态框
	$("#exchange-rate-modal").modal("show");
	
	$("#exchange-rate-modal").on("hidden.bs.modal",function(){
		$(this).parent().empty();
	});
	
	//初始化时间选择器
	addFormDate(".exchange-select-time",0);
	
	//加载货币种类
	ExchangeRateManager.getCurrencyCode(function(currencies){
		for(var i=currencies.length-1;i>=0;i--)
		{
			var li='<li><a href="javascript:void(0)" onclick="chooseCurrencyFrom(\''+currencies[i]
				+'\')"><img class="img-rounded select-img" height="20px" src="public/images/currency/'+currencies[i]+'.png"/>'+currencies[i]+'</a></li>';
			$("#currency-from ul").prepend(li);
			li='<li><a href="javascript:void(0)" onclick="chooseCurrencyTo(\''+currencies[i]
				+'\')"><img class="img-rounded select-img" height="20px" src="public/images/currency/'+currencies[i]+'.png"/>'+currencies[i]+'</a></li>';
			$("#currency-to ul").prepend(li);
		}
	});

	//绑定搜索按钮点击提交时间
	$("#exchange-rate-sbt").click(function(){
		$("#loading-small").show();
		var from=$("#currency-from-val").val();
		var to=$("#currency-to-val").val();
		if(from==to)
			$("#exchange-rate-two-same").show("normal");
		else
		{
			$("#exchange-rate-tips").hide("normal");
			$("#exchange-rate-table tbody").empty();
			$("#exchange-rate-table").show("normal");
			$("#exchange-rate-chooser").hide("normal");
			$("#exchange-rate-back").show("normal");
			$("#exchange-rate-two-same").hide("normal");
			ExchangeRateManager.getCurrencyCode(function(currencies){
				lostSearchCount=currencies.length;
				if(from==ALL)
				{
					for(var i in currencies)
						if(currencies[i]!=to)
							search(currencies[i], to);
				}
				else if(to==ALL)
				{
					for(var i in currencies)
						if(from!=currencies[i])
							search(from, currencies[i]);
				}
				else
				{
					lostSearchCount=2;
					search(from, to);
				}
			});

		}
		$(this).hide();
	});
	
	//绑定返回按钮事件
	$("#exchage-rate-back").click(function(){		
		if($("#exchange-rate-table").is(":visible"))
		{
			chooseCurrencyFrom("All Currency");
			chooseCurrencyTo("All Currency")
			$("#exchange-rate-tips").show("normal");
			$("#exchange-rate-chooser").show("normal");
			$("#exchange-rate-table").hide("normal");
			$("#exchange-rate-table tbody").empty();
			$("#exchange-rate-sbt").show();
		}
		else if($("#exchange-rate-chart").is(":visible"))
		{
			$(".modal-content").removeAttr("style")
			$(".modal-body").removeAttr("style");
			$("#chart-full-screen-btn").removeClass("glyphicon-resize-small").addClass("glyphicon-resize-full");
			$("#exchange-rate-chart .chart").css("height","400px").css("width","868px");
			$(".time-selector").css("width","868px");
			$("#exchange-rate-modal .modal-dialog").removeClass("modal-lg");
			$("#exchange-rate-chart").hide("normal");
			$("#exchange-rate-table").show("normal");
			$("#chart-full-screen-btn").hide();
		}
	});
	
	//绑定汇率历史查询中所有按钮的点击事件
	$(".btn-rate-history").click(function(){
		var id=$(this).attr("id");
		$(this).attr("disabled","disabled");
		var htmlContent=$(this).html();
		$(this).html('Loading');
		var start,end=getNowDate();
		switch (id) 
		{
		//查询指定时间段的汇率
		case "exchange-history-sbt":
			start=$("#exchange-start-time").val();
			end=$("#exchange-end-time").val();
			break;
		//查询近十年的汇率
		case "last-decade-history":
			start=nextDay(-10*365);
			break;
		//查询近一年的汇率
		case "last-year-history":
			start=nextDay(-365);
			break;
		//查询进一月的汇率
		case "last-month-history":
			start=nextDay(-30);
			break;
		default:
			break;
		}
		if(id!="exchange-history-sbt")
		{
			$("#exchange-start-time").val(start);
			$("#exchange-end-time").val(end);
		}
		loadRateHistoryChart($("#from").val(),$("#to").val(),start,end,function(){
			$("#"+id).html(htmlContent);
			$("#"+id).removeAttr("disabled");
		});
	});
	
	//绑定图表全屏按钮点击事件
	$("#chart-full-screen-btn").click(function(){
		var screenHeight=getScreenHeight();
		var screenWidth=getScreenWidth();
		if($(this).hasClass("glyphicon-resize-full"))
		{
			$(".modal-content").animate(
					{
						top:-30+"px",
						width:screenWidth+"px",
						left:(-(screenWidth-900)/2)+"px",
						height:screenHeight+"px",
					});
			$(".modal-body").css("height",(screenHeight-65-56)+"px");
			$("#exchange-rate-chart .chart").css("height",(screenHeight-65-56-2*15-65)+"px")
				.css("width",(screenWidth-2*15)+"px");
			$(".time-selector").css("width",(screenWidth-2*15)+"px");
			if($("#loading-large").is(":visible"))
				$("#loading-large").css("top",((screenHeight-65-56-2*15-65-100)/2)+"px")
					.css("left",((screenWidth-2*15-100)/2)+"px");
			else
				setRateHistoryChart(chartData);
			$(this).removeClass("glyphicon-resize-full").addClass("glyphicon-resize-small");
		}
		else
		{
			$(".modal-content").animate(
					{
						top:"0px",
						width:"900px",
						left:"0px",
						height:"638px",
					});
			$(".modal-body").css("height","515px").css("width","898px");
			$("#exchange-rate-chart .chart").css("height","400px").css("width","868px");
			$(".time-selector").css("width","868px");
			if($("#loading-large").is(":visible"))
				$("#loading-large").css("top","150px").css("left","374px");
			else
				setRateHistoryChart(chartData);
			$(this).removeClass("glyphicon-resize-small").addClass("glyphicon-resize-full");
		}
	});
	
});

/**
 * 选择本位币
 * @param currencyId
 */
function chooseCurrencyFrom(currency)
{
	$("#currency-from-val").val(currency);
	$("#currency-from .choosed-item img").attr("src","public/images/currency/"+currency+".png");
	$("#currency-from .choosed-item span").text(currency);
}

/**
 * 选择原币
 * @param currencyId
 */
function chooseCurrencyTo(currency)
{
	$("#currency-to-val").val(currency);
	$("#currency-to .choosed-item img").attr("src","public/images/currency/"+currency+".png");
	$("#currency-to .choosed-item span").text(currency);
}

/**
 * 查询当前时间汇率
 * @param from 本位币
 * @param to 原币
 */
function search(from,to)
{
	//var url="http://api.k780.com:88/?app=finance.rate&scur="+from+"&tcur="+to+"&appkey=12203&sign=3c1883f5415d883ba2f6f87e05cae68c&format=json";
	ExchangeRateManager.search(from,to,function(result){
		var tr='<tr class="exchange-rate-result">'+
				        '<td><img class="img-rounded select-img" height="20px" src="public/images/currency/'+from+'.png"/>'+from+'</td>'+
				        '<td><img class="img-rounded select-img" height="20px" src="public/images/currency/'+to+'.png"/>'+to+'</td>'+
				        '<td>1 '+from+' = '+result+' '+to+'</td>'+
				        '<td><span class="chart" onclick="showChart(\''+from+'\',\''+to+'\')"><span class="glyphicon glyphicon-stats"></span><span class="glyphicon glyphicon-chevron-right"></span></span></td>'+
			       '</tr>';
		$("#exchange-rate-table tbody").append(tr);
		lostSearchCount--;
		if(lostSearchCount==1)
			$("#loading-small").hide();
	});
}

/**
 * @param from
 * @param to
 */
function showChart(from,to)
{
	$("#exchange-rate-modal .modal-dialog").addClass("modal-lg");
	$("#exchange-rate-table").hide("normal");
	$("#chart-full-screen-btn").show();
	$("#from").val(from);
	$("#to").val(to);
	var start=nextDay(-365);
	var end=getNowDate();
	$("#exchange-start-time").val(start);
	$("#exchange-end-time").val(end);
	loadRateHistoryChart(from, to, start, end);
	$("#exchange-rate-chart").show("normal");
}

function loadRateHistoryChart(from,to,start,end,callback)
{
	ExchangeRateManager.history(from,to,start,end,function(history){
		chartData=
			{
				title:from+" to "+to+" Exchange Rate",
				subtitle:"From "+start+" to "+end,
	             pointStart: Date.UTC(history.start.getYear()+1900, history.start.getMonth(), history.start.getDate()),
	             data: history.data,
	             seriesName: from+" to "+to
			};
		setRateHistoryChart(chartData);
		if(callback!=null)
			callback();
	});
}


function setRateHistoryChart(chartData)
{
	   $("#exchange-rate-chart .chart").highcharts({
	         chart: {
	             zoomType: 'x',
	             spacingRight: 20
	         },
	         title: {
	             text: chartData.title
	         },
	         subtitle: {
	             text: chartData.subtitle
	         },
			    credits:{
			         enabled:false 
			    },
	         xAxis: {
	             type: 'datetime',
	             maxZoom: 14 * 24 * 3600000, // fourteen days
	             title: {
	                 text: null
	             }
	         },
	         yAxis: {
	             title: {
	                 text: 'Exchange Rate'
	             }
	         },
	         tooltip: {
	             shared: true
	         },
	         legend: {
	             enabled: false
	         },
	         plotOptions: {
	             area: {
	                 fillColor: {
	                     linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                     stops: [
	                         [0, Highcharts.getOptions().colors[0]],
	                         [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                     ]
	                 },
	                 lineWidth: 1,
	                 marker: {
	                     enabled: false
	                 },
	                 shadow: false,
	                 states: {
	                     hover: {
	                         lineWidth: 1
	                     }
	                 },
	                 threshold: null
	             }
	         },
	         series: [{
	             type: 'area',
	             name: chartData.seriesName,
	             pointInterval: 24 * 3600 * 1000,
	             pointStart: chartData.pointStart,
	             data: chartData.data
	         }]
	    });
}