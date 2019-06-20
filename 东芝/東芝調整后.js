
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var requestid = GetQueryString("requestid");

var myurl = "/interface/data/bzdf.jsp?requestid=" + requestid;
alert(myurl);


$('.excelTempDiv tr tr').eq(9).hide();
$('.excelTempDiv tr tr').eq(10).hide();
$('.excelTempDiv tr tr').eq(11).hide();
$('.excelTempDiv tr tr').eq(12).hide();
$('.excelTempDiv tr tr').eq(13).hide();
$('.excelTempDiv tr tr').eq(14).hide();
$('.excelTempDiv tr tr').eq(15).hide();
$('.excelTempDiv tr tr').eq(16).hide();
$('.excelTempDiv tr tr').eq(17).hide();
$('.excelTempDiv tr tr').eq(18).hide();
$('.excelTempDiv tr tr').eq(19).hide();
$('.excelTempDiv tr tr').eq(20).hide();
$('.excelTempDiv tr tr').eq(21).hide();
$('.excelTempDiv tr tr').eq(22).hide();
$('.excelTempDiv tr tr').eq(23).hide();

		
	
jQuery.post(myurl, function(data) {

		var retss = eval("(" + data + ")");
        var ggp;
        var tp;
        var jdh;
		for (var i = 0; i < retss.length; i++) {
			 ggp = retss[i].ggp;
			 tp = retss[i].tp;
			 jdh = retss[i].jdh;
	    
		}

		if (ggp == 1) {

			$('.excelTempDiv tr tr').eq(9).show();
			$('.excelTempDiv tr tr').eq(10).show();
			$('.excelTempDiv tr tr').eq(11).show();
			$('.excelTempDiv tr tr').eq(12).show();
			$('.excelTempDiv tr tr').eq(13).show();
			$('.excelTempDiv tr tr').eq(14).show();
			$('.excelTempDiv tr tr').eq(15).show();
			$('.excelTempDiv tr tr').eq(16).show();
		} else {

			$('.excelTempDiv tr tr').eq(9).hide();
			$('.excelTempDiv tr tr').eq(10).hide();
			$('.excelTempDiv tr tr').eq(11).hide();
			$('.excelTempDiv tr tr').eq(12).hide();
			$('.excelTempDiv tr tr').eq(13).hide();
			$('.excelTempDiv tr tr').eq(14).hide();
			$('.excelTempDiv tr tr').eq(15).hide();
			$('.excelTempDiv tr tr').eq(16).hide();
		}
		
		if (td == 1) {

			$('.excelTempDiv tr tr').eq(17).show();
			$('.excelTempDiv tr tr').eq(18).show();
		} else {

			$('.excelTempDiv tr tr').eq(17).hide();
			$('.excelTempDiv tr tr').eq(18).hide();
		}
		if (jdh == 1) {

			$('.excelTempDiv tr tr').eq(19).show();
			$('.excelTempDiv tr tr').eq(20).show();
			$('.excelTempDiv tr tr').eq(21).show();
			$('.excelTempDiv tr tr').eq(22).show();
			$('.excelTempDiv tr tr').eq(23).show();

		} else {

			$('.excelTempDiv tr tr').eq(19).hide();
			$('.excelTempDiv tr tr').eq(20).hide();
			$('.excelTempDiv tr tr').eq(21).hide();
			$('.excelTempDiv tr tr').eq(22).hide();
			$('.excelTempDiv tr tr').eq(23).hide();

		}
		
	});