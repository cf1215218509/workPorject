function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var idr = GetQueryString("id");
 var myurl = "/interface/data/rckcf.jsp?id=" + idr;

 function addrc(){
	 jQuery.post(myurl, function(data) {
		var retss = eval("(" + data + ")");
        
		for (var i = 0; i < retss.length; i++) {
			var result = retss[i].result;	
			
			if(result=='true'){
				alert("添加成功！");
			}else{
				alert("添加失败！");
			}
	    
		}
		
	 });
    }