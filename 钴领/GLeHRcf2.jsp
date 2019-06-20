<jsp:useBean id="Base" class="weaver.general.Base64two" scope="page" />
<jsp:useBean id="Util" class="weaver.general.Util" scope="page" />
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
	
	String HRsoft2000 = request.getParameter("Appname");		
	String MainFrame_Encrypt = request.getParameter("Prgname");
	
		
	String OAuUsername = request.getParameter("username");
	String safeString = request.getParameter("safe");
	
	byte [] OAuUsername2 = OAuUsername.getBytes();
	byte [] safe = safeString.getBytes();
	
	
	
	String xxxxx = Base.encode(OAuUsername2);
	String yyyyy = Base.encode(safe);
	
	String url = "http://192.35.3.2/scripts/mgrqispi.dll?Appname="+HRsoft2000+"&Prgname="+MainFrame_Encrypt+
	"&ARGUMENTS="+"-A"+xxxxx+","+"-A"+yyyyy;
	out.println(url);
	
	//response.sendRedirect(url);
	

 %>




