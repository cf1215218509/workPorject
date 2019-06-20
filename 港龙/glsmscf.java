package weaver.workflow.action.gl;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import weaver.interfaces.workflow.action.Action;
import weaver.sms.smscf;
import weaver.soa.workflow.request.RequestInfo;

public class glsmscf extends BaseBean implements Action{

	@Override
	public String execute(RequestInfo requestinfo) {
		RecordSet rs = new RecordSet();
		smscf sms = new smscf();
		String requestid = requestinfo.getRequestid();
		String sql = "";
		sql = "select oazh,yddh from formtable_main_48 where requestid = "+requestid;
		
		if(rs.execute(sql)){
			
			String oazh = Util.null2String(rs.getString("oazh"), "");
			String phoneno = Util.null2String(rs.getString("yddh"), "");
			
			String msg = "OA账号已创建，OA账号为："+oazh+"，密码：123456，请登录http://oa.jsgldc.com:8082";
			boolean result = sms.sendSMS("", phoneno, msg);
			
			 if(result==true){
				 
				return Action.SUCCESS;
			 
			 }else{
				 return Action.FAILURE_AND_CONTINUE;
			 }					
		}
		
		return Action.SUCCESS;					
	}	 
	
}
