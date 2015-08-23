$(document).ready(function(){
	//加载用户信息
	UserManager.getUser(function(user){
		$("#info-uid").attr("placeholder",user.uid);
		$("#info-uname").attr("placeholder",user.uname);
		$("#info-email").attr("placeholder",user.email);
		$("#info-password").attr("placeholder","********");
	    var photoUid=user.uid;
	    if(user.photo.pid==SYS_NULL_ID)
	    	photoUid=SYS_NULL_ID;
		$("#uc-info-phto img").attr("src","upload/"+photoUid+"/"+PHOTO_NO+"/"+user.photo.filename);
	});
});