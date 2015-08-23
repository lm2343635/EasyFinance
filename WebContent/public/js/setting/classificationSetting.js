$(document).ready(function(){
	//加载图标
	loadIcons("add-classification", SYS_NULL_ID, CLASSIFICATION_NO, 4, 7, "offical-classification-icons-ul", "li-offical","offical-classification-icons-table",56);
	
	//绑定提交点击事件
	bindSubmit("add-classification-submit",["cname"], "add-classification-wrong",function(vals,iid){
		ClassificationManager.addClassification(vals[0],iid,function(cid){
			if(cid>0)
			{
				$(".add-success p span").text(vals[0]);
				$(".add-form").hide("normal");
				$(".modal-footer").hide("normal");
				$(".add-success").show("normal");		
				ClassificationManager.getClassificationById(cid,function(classification){
					var tr='<tr id="classification-'+classification.cid+'">'+
							  		'<td><img src="upload/'+classification.cicon.uid+'/'+CLASSIFICATION_NO+'/'+classification.cicon.iname+'"></td>'+
							  		'<td>'+classification.cname+'</td>'+
							  		'<td>$ '+classification.cin+'</td>'+
							  		'<td>$ '+classification.cout+'</td>'+
							  		'<td>$ '+classification.asset+'</td>'+
							  		'<td><span onclick="deleteClassification('+classification.cid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
							  '</tr>';
					$("#classification-table tbody").append(tr);
				});
			}
			else
				$(".add-fail").show("normal");
		});
	});
	
	//加载所有分类
	ClassificationManager.getClassification(function(classifications){
		for(var i in classifications)
		{
			var tr='<tr id="classification-'+classifications[i].cid+'">'+
					  		'<td><img src="upload/'+classifications[i].cicon.uid+'/'+CLASSIFICATION_NO+'/'+classifications[i].cicon.iname+'"></td>'+
					  		'<td>'+classifications[i].cname+'</td>'+
					  		'<td>$ '+classifications[i].cin+'</td>'+
					  		'<td>$ '+classifications[i].cout+'</td>'+
					  		'<td>$ '+classifications[i].asset+'</td>'+
					  		'<td><span onclick="deleteClassification('+classifications[i].cid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
					  '</tr>';
			$("#classification-table tbody").append(tr);
		}
	});

});

/**
 * 删除分类
 * @param cid 分类id
 */
function deleteClassification(cid)
{
	ClassificationManager.deleteClassification(cid,function(){
		$("#classification-"+cid).remove();
	});
}

/*
$("#add-classification").on("show.bs.modal", function (e) {
	selectIconPage(1);
	SetManager.getIconSize(0,2,function(size){	
		var pageCount= parseInt(size/28);
		if(size%28!=0)
			pageCount++;
		for(var i=2;i<=pageCount;i++)
			$("#offical-classification-icons-ul").append("<li id='li-offical-"+i
					+"'><a href='javascript:void(0)'' onclick='selectIconPage("+i+")'>"+i+"</a></li>");
	});
});


function selectIconPage(pageNumber)
{
	$("#offical-classification-icons-ul li").removeClass("active");
	$("#li-offical-"+pageNumber).addClass("active");
	SetManager.getIcon(0,2,pageNumber-1,28,function(icons){
		$("#offical-classification-icons table tbody").empty();
		var tr='<tr>';
		for(var i=0;i<icons.length;i++)
		{		
			tr+='<td><img width="56px" id="icon'+icons[i].iid+'" onclick="selectIcon('+icons[i].iid
				+')" class="img-thumbnail img-select" src="upload/0/2/'+icons[i].iname+'"></td>';
			if((i+1)%7==0||i==icons.length-1)
			{
				tr+='</tr>';
				$("#offical-classification-icons table tbody").append(tr);
				tr='<tr>';
			}
		}
	});
}

*/