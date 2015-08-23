$(document).ready(function(){
	SetManager.getSynchronizationHistories(function(histories) {
		$("#synchronization-history-table tbody").empty();
		for(var i in histories)
		{
			var tr='<tr>'+
					'<td>'+histories[i].time.format(DATE_HOUR_MINUTE_SECOND_FORMAT)+'</td>'+
					'<td>'+histories[i].device+'</td>'+
					'<td>'+histories[i].ip+'</td>'+
				'</tr>';
			$("#synchronization-history-table tbody").append(tr);
		}
	})
});


/**
 * 修改密码提交
 */
function modifyPsswordSubmit()
{
	var oldPassword=$("#old-password").val();
	var newPassword=$("#new-password").val();
	var newPasswordRepeat=$("#new-password-repeat").val();
	UserManager.isPasswordRight(oldPassword,function(data){
		if(data==false)
		{
			$("#old-password-wrong").show();
			$("#old-password-wrong-content").popover("show");
		}
		else
		{
			$("#old-password-wrong").hide();
			$("#old-password-wrong-content").popover("hide");
			if(newPasswordRepeat!=newPassword||newPasswordRepeat==null||newPasswordRepeat==""
				||newPassword==null||newPassword=="")
			{
				$("#new-password-wrong").show();
				$("#new-password-not-equal").popover("show");
			}
			else
			{
				$("#new-password-wrong").hide();
				$("#new-password-not-equal").popover("hide");
				UserManager.modifyPassword(newPassword,function(data){
					if(data==true)
					{
						modifyPsswordReset();
						$("#modal-new-password").text(newPassword);
						$("#modify-password-success-modal").modal(
								{
									keyboard: false,
									show:true
								});
					}
					else
					{
						$("#modify-password-fail-modal").modal(
								{
									keyboard: false,
									show:true
								});
					}
				});
			}
		}
	});
}

function modifyPsswordReset()
{
	$("#old-password").val("");
	$("#new-password").val("");
	$("#new-password-repeat").val("");
}