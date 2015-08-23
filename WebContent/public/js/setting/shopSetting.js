$(document).ready(function(){
	//加载图标
	loadIcons("add-shop", SYS_NULL_ID, SHOP_NO, 4, 8, "offical-shop-icons-ul", "li-offical","offical-shop-icons-table",50);
	
	//绑定提交点击事件
	bindSubmit("add-shop-submit",["sname"], "add-shop-wrong",function(vals,iid){
		ShopManager.addShop(vals[0],iid,function(sid){
			if(sid>0)
			{
				$(".add-success p span").text(vals[0]);
				$(".add-form").hide("normal");
				$(".modal-footer").hide("normal");
				$(".add-success").show("normal");		
				ShopManager.getShopById(sid,function(shop){
					var tr='<tr id="shop-'+shop.sid+'">'+
							  		'<td><img src="upload/'+shop.sicon.uid+'/'+SHOP_NO+'/'+shop.sicon.iname+'"></td>'+
							  		'<td>'+shop.sname+'</td>'+
							  		'<td>$'+shop.sin+'</td>'+
							  		'<td>$'+shop.sout+'</td>'+
							  		'<td>$'+shop.asset+'</td>'+
							  		'<td><span onclick="deleteShop('+shop.sid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
							  '</tr>';
					$("#shop-table tbody").append(tr);					
				});
			}
			else
				$(".add-fail").show("normal");
		});
	});
	
	//加载所有商家
	ShopManager.getShop(function(shops){
		for(var i in shops)
		{
			var tr='<tr id="shop-'+shops[i].sid+'">'+
					  		'<td><img src="upload/'+shops[i].sicon.uid+'/'+SHOP_NO+'/'+shops[i].sicon.iname+'"></td>'+
					  		'<td>'+shops[i].sname+'</td>'+
					  		'<td>$'+shops[i].sin+'</td>'+
					  		'<td>$'+shops[i].sout+'</td>'+
					  		'<td>$'+shops[i].asset+'</td>'+
					  		'<td><span onclick="deleteShop('+shops[i].sid+')" class="glyphicon glyphicon-trash glyphicon-btn-danger"></span></td>'+
					  '</tr>';
			$("#shop-table tbody").append(tr);
		}
	});
});

function deleteShop(sid)
{
	ShopManager.deleteShop(sid,function(){
		$("#shop-"+sid).remove();
	});
}