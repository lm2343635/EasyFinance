//setting加载文件
var loadFiles=
	[
	 	"user.html",
	 	"security.html",
	 	"classificationSetting.html",
	 	"accountSetting.html",
	 	"shopSetting.html",
	 	"templateSetting.html",
	 	"accountBookSetting.html"	 
	 ];

$(document).ready(function(){
	setTabs("setting", loadFiles, "#set-content .showInfo", "#set-list-group");
	setAutoHeight("#set-content", 150);
	$(".switch").bootstrapSwitch();
});

/**
 * 加载图标和图标页码
 * @param modalID 模态框id
 * @param uid 用户id
 * @param type 图标类型
 * @param row 行数
 * @param col 列数
 * @param paginationID 页码id
 * @param paginationLi 页码<li>的id标识符
 * @param tableID 显示图标的表格id
 * @param imgWidth 图标宽度
 */
function loadIcons(modalID,uid,type,row,col,paginationID,paginationLi,tableID,imgWidth)
{
	$("#"+modalID).on("show.bs.modal", function (e){
		$("#"+paginationID).empty();
		selectIconPage(1,uid,type,row,col,paginationID,paginationLi,tableID,imgWidth);
		var pageSize=row*col;
		SetManager.getIconSize(uid,type,function(size){	
			var pageCount= parseInt(size/pageSize);
			if(size%pageSize!=0)
				pageCount++;
			for(var i=1;i<=pageCount;i++)
			{
				var li='<li ';
				if(i==1)
					li+='class="active"';
				li+=' id="'+paginationLi+'-'+i+'">';
				li+='<a href="javascript:void(0)" onclick="selectIconPage(';
				li+=i+','+uid+','+type+','+row+','+col+',\''+paginationID+'\',\''+paginationLi+'\',\''+tableID+'\','+imgWidth+')">';
				li+=i+'</a></li>';
				$("#"+paginationID).append(li);
			}
		});
	});
}

/**
 * 按页码选择图标
 * @param pageNumber 页码
 * @param uid 用户id
 * @param type 图标类型
 * @param row 行数
 * @param col 列数
 * @param paginationID 页码id
 * @param paginationLi 页码<li>的id标识符
 * @param tableID 显示图标的表格id
 * @param imgWidth 图标宽度
 */
function selectIconPage(pageNumber,uid,type,row,col,paginationID,paginationLi,tableID,imgWidth)
{
	$("#"+paginationID+" li").removeClass("active");
	$("#"+paginationLi+"-"+pageNumber).addClass("active");
	SetManager.getIcon(uid,type,pageNumber-1,row*col,function(icons){
		$("#"+tableID+" tbody").empty();
		var tr='<tr>';
		for(var i=0;i<icons.length;i++)
		{		
			tr+='<td><img width="'+imgWidth+'px" id="icon'+icons[i].iid+'" onclick="selectIcon('+icons[i].iid
				+')" class="img-thumbnail img-select" src="upload/'+uid+'/'+type+'/'+icons[i].iname+'"></td>';
			if((i+1)%col==0||i==icons.length-1)
			{
				tr+='</tr>';
				$("#"+tableID+" tbody").append(tr);
				tr='<tr>';
			}
		}
	});
}

/**
 * 选择图标
 * @param iid 图标id
 */
function selectIcon(iid)
{
	$(".img-select").removeClass("img-selected");
	$("#icon"+iid).addClass("img-selected");
	$("#iid").val(iid);
}

/**
 * 绑定提交按钮事件
 * @param submitButtonID 提交按钮id
 * @param inputs 输入表单
 * @param wrong 错误信息id
 * @param submit 提交函数
 */
function bindSubmit(submitButtonID,inputs,wrong,submit)
{
	$("#"+submitButtonID).click(function(){
		var vals=[];
		for(var i in inputs)
			vals[i]=$("#"+inputs[i]).val();
		var iid=$("#iid").val();		
		if(validate(vals, iid))
		{
			hidePopoverTip("#"+wrong);
			submit(vals,iid);
		}
		else
			showPopoverTip("#"+wrong, 2000);
	});
	//绑定返回按钮点击事件
	$(".add-success button").click(function(){
		for(var i in inputs)
			$("#"+inputs[i]).val("");
		$("#iid").val("");		
		$(".img-select").removeClass("img-selected");
		$(".add-form").show("normal");
		$(".modal-footer").show("normal");
		$(".add-success").hide("normal");		
	});
}

/**
 * 验证表单中是否存在空项目
 * @param vals
 * @param iid
 * @returns {Boolean}
 */
function validate(vals,iid)
{
	if(iid==null||iid=="")
		return false;
	for(var i=0;i<vals.length;i++)
		if(vals[i]==null||vals[i]=="")
			return false;
	return true;
}