//全局系统空ID
var SYS_NULL_ID=1;
//收支记录照片空ID
var SYS_RECORD_PHOTO_NULL=2;
//账本图标分类编号
var ACCOUNT_BOOK_NO=0;
//账户图标分类编号
var ACCOUNT_NO=1;
//分类图标分类编号
var CLASSIFICATION_NO=2;
//商家图标分类编号
var SHOP_NO=3;
//图片分类编号
var PHOTO_NO=4;

//“年-月-日”格式
var YEAR_MONTH_DATE_FORMAT="yyyy-MM-dd";
//“年-月”格式
var YEAR_MONTH_FORMAT="yyyy-MM";
//“年”格式
var YEAR_FORMAT="yyyy";
//“年-月-日 时:分”格式
var DATE_HOUR_MINUTE_FORMAT="yyyy-MM-dd hh:mm";
//“年-月-日 时:分:秒”格式
var DATE_HOUR_MINUTE_SECOND_FORMAT="yyyy-MM-dd hh:mm:ss";

/**
 * 检查用户session是否过期
 */
function checkSession(afterCheck)
{
	UserManager.checkSession(function(exsit){
		if(!exsit)
			location.href="sessionError.html";
		else
		{
			$("body").show();
			afterCheck();
		}	
	});
}

/**
 * 判断是否为数字
 * @param num
 * @returns
 */
function isNum(num)
{
     var reNum =/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/;
     return (reNum.test(num));
}

/**
 * 判断是否为整数
 * @param num
 * @returns
 */
function isInteger(num)
{
    var reNum =/^-?[1-9]\d*$/;
    return (reNum.test(num));
}

/**
 * 判断是否为电子邮件格式
 * @param email
 * @returns
 */
function isEmailAddress(email)
{
	var patten = new RegExp(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
	return patten.test(email);
}

/**
 * 格式化时间
 * @param format 时间格式
 * @returns
 */
Date.prototype.format =function(format)
{
    var o={
	    "M+" : this.getMonth()+1, //month
		"d+" : this.getDate(),    //day
		"h+" : this.getHours(),   //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		"S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) 
    	format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)
    	if(new RegExp("("+ k +")").test(format))
    		format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};


/**
 * 得到每个月的天数
 * @param year
 * @return
 */
function getMonthDay(year)
{
	var monthDay=[31,28,31,30,31,30,31,31,30,31,30,31];
	if(isLeapYear(year))
		monthDay[1]++;
	return monthDay;
}

/**
 * 判断是否闰年
 * @param year
 * @returns {Boolean}
 */
function isLeapYear(year)
{
	if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
		return true;
	return false;
}

/**
 * 得到客户端当前时间
 * @returns 当前时间
 */
function getNowTime()
{
	var date=new Date();
	return date.format("yyyy-MM-dd hh:mm");
}

/**
 * 得到客户端当前日期
 * @returns 当前日期
 */
function getNowDate()
{
	var date=new Date();
	return date.format("yyyy-MM-dd");
}

function nextDay(next)
{
	var date=new Date();
	date.setDate(date.getDate()+next);
	return date.format(YEAR_MONTH_DATE_FORMAT);
}

/**
 * 得到本月起始日期
 * @returns {String}
 */
function getThisMonthStart()
{
	var nowMonth=new Date().format("yyyy-MM");
	return nowMonth+"-01";
}

/**
 * 得到本月结束日期
 * @returns {String}
 */
function getThisMonthEnd()
{
	var nowMonth=new Date().format("yyyy-MM");
	var year=parseInt(nowMonth.split("-")[0]);
	var month=parseInt(nowMonth.split("-")[1]);
	var monthDay=getMonthDay(year);
	return nowMonth+"-"+monthDay[month-1];
}

/**
 * 得到本年起始日期
 * @returns {String}
 */
function getThisYearStart()
{
	var nowYear=new Date().format("yyyy");
	return nowYear+"-01-01";
}

/**
 * 得到屏幕高度
 * @returns
 */
function getScreenHeight()
{
	return document.documentElement.clientHeight;
}

/**
 * 得到屏幕宽度
 * @returns
 */
function getScreenWidth()
{
	return document.documentElement.clientWidth;
}

/**
 * 自动设置div高度
 * @param selector div选择器
 * @param over 除改div外其他元素高度
 */
function setAutoHeight(selector,over)
{
	$(selector).css("height",(getScreenHeight()-over)+"px");
}

/**
 * 设置日期选择器精确到分
 * @param selector
 */
function addFormDateTime(selector)
{
	if($(".datetimepicker").length>0)
		$(".datetimepicker").remove();
    $(selector).datetimepicker({
	    weekStart: 1,
	    todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
	    showMeridian: 1
	});
    $(selector).val(getNowTime());
}

/**
 * 设置日期选择器精确到天
 * @param selector
 * @param type 类型，精确程度
 */
function addFormDate(selector,type)
{
	if($(".datetimepicker").length>0)
		$(".datetimepicker").remove();
	var fmt;
	if(type==0)
		fmt="yyyy-mm-dd";
	else if(type==1)
		fmt="yyyy-mm";
	else 
		fmt="yyyy";
	$(selector).datetimepicker({
        format: fmt,
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2+type,
		minView: 2+type,
		forceParse: 0,
        showMeridian: 1,
        pickerPosition: "top-right"
    });
}

/**
 * 设置tabs标签页
 * @param dir 要加载的内容文件目录
 * @param loadFiles 要加载的内容文件名数组
 * @param tabSelector 标签页选择器
 * @param linkGroupSelector 链接组选择器
 */
function setTabs(dir,loadFiles,tabSelector,linkGroupSelector)
{
	$(tabSelector+":eq(0)").addClass('show');
	$(tabSelector+":eq(0)").load(dir+"/"+loadFiles[0]);
	$(linkGroupSelector+" a").click(function(){
		var This=this;
		checkSession(function(){
			$(linkGroupSelector+" a").removeClass('active');
			$(tabSelector).removeClass('show');
			$(This).addClass('active');
			var index=$(linkGroupSelector+" a").index(This);
			$(tabSelector).eq(index).load(dir+"/"+loadFiles[index]);
			$(tabSelector).eq(index).addClass('show');
		});
	});
}

/**
 * 定时显示弹出框提示
 * @param selector 选择器
 * @param time 定时时长
 */
function showPopoverTip(selector,time)
{
	$(selector).show();
	$(selector+" span").popover("show");
	setTimeout(function(){
		hidePopoverTip(selector);
	}, time);
}

/**
 * 关闭弹出框提示
 * @param selector 选择器
 */
function hidePopoverTip(selector)
{
	$(selector).hide();
	$(selector+" span").popover("hide");
}

/**
 * 闪烁提醒
 * @param selector 选择器
 * @param time 闪烁时间，单位毫秒
 * @param frequency 闪烁频率，单位毫秒
 */
function flickerTip(selector,time,frequency)
{
	var flicker=setInterval(function(){
		if($(selector).css("visibility")=="hidden")		
			$(selector).css("visibility","inherit");
		else
			$(selector).css("visibility","hidden");
	}, frequency);
	setTimeout(function(){
		clearInterval(flicker);
		$(selector).css("visibility","inherit");
	}, time);
}

//Web安全色基本元素个数
var WebSafeColorsBasicNum=4;
//Web安全色基本元素
var WebSafeColorsBasic=["66","99","CC","FF"];

/**
 * 随机生成Web安全色
 */
function createRandomColor()
{
	var color="#";
	for(var i=0;i<3;i++)
	{
		var random=parseInt(Math.random()*WebSafeColorsBasicNum);
		color+=WebSafeColorsBasic[random];
	}	
	return color;
}

/**
 * 颜色去重
 * @param colors
 * @param color
 * @returns
 */
function haveSameColor(colors,color)
{
	for(var i in colors)
		if(colors[i]==color)
			return true;
	return false;
}

/**
 * 随机生成n个不同的Web安全色
 * @param n 小于等于WebSafeColorsBasicNum的3次方
 */
function createRandomColors(n)
{
	var colors=new Array();
	colors[0]=createRandomColor();
	for(var i=1;i<n;i++)
	{
		var randomColor=createRandomColor();
		while(haveSameColor(colors,randomColor))
			randomColor=createRandomColor();
		colors[i]=randomColor;
	}	
	return colors;	
}