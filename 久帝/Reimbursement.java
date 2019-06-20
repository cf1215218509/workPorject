package weaver.workflow.action.dalsj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 费用报销流程传送到sap
 * @author Administrator
 *
 */
public class Reimbursement extends BaseBean implements Action {

	@Override
	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		
        String requestid = requestInfo.getRequestid();
        String workflowid = requestInfo.getWorkflowid();
        String f1="1";
        String formid = "";
        String sql = "";
		String jsonStr = "";
		sql = "select formid*-1 formid from workflow_base where id = "
                + workflowid;
        rs.execute(sql);
        if (rs.next()) {
            formid = Util.null2String(rs.getString("formid"), "");
        }
        
        
        //主表信息
        String mainsql = "select *,convert(varchar(10),GETDATE(),112) as rq,currencyCode as currencyCode1 " +
        		"  from formtable_main_" + formid + "  where requestid ="+ requestid;
        rs.execute(mainsql);
        writeLog("主表信息sql:"+mainsql);
        if (rs.next()) {
        	String mainid= Util.null2String(rs.getString("id"));
        	String oaFormNo= Util.null2String(rs.getString("oaFormNo"));
        	String sapMsgGUID= "";
        	String docDate= Util.null2String(rs.getString("docDate"));
        	
        	String postingDate= Util.null2String(rs.getString("rq"));
        	 
        	
        	String numberOforiginalDocument= Util.null2String(rs.getString("numberOforiginalDocument"));
        	String fiscalPeriod= "";
        	if(null!=docDate&&!"".equals(docDate)&&docDate.length()>0){
        		fiscalPeriod=docDate.split("-")[1];
//        		docDate=docDate.replace("-", "");
        		docDate=Utillsjnew.DateToStr(Utillsjnew.StrToDate(docDate));
        	}
        	String companyCode= Util.null2String(rs.getString("companyCode"));
        	String currencyCode= Util.null2String(rs.getString("currencyCode1"));
        	String requesterID= Util.null2String(rs.getString("requesterID"));
        	String requester= Util.null2String(rs.getString("requester"));
        	String docHeaderText= Util.null2String(rs.getString("docHeaderText"));
        	docHeaderText =Utillsjnew.splitAndFilterString(docHeaderText,10000);
        	
        	jsonStr="{" ;
        	jsonStr+="\"paymentRequestForm\": {";
        	jsonStr+="\"oaFormNo\": \""+oaFormNo+"\",";
        	jsonStr+="\"sapMsgGUID\": \""+sapMsgGUID+"\",";
        	jsonStr+="\"docDate\": \""+docDate+"\",";
        	jsonStr+="\"postingDate\": \""+postingDate+"\",";
        	jsonStr+="\"numberOforiginalDocument\": \""+numberOforiginalDocument+"\",";
        	jsonStr+="\"fiscalPeriod\": \""+fiscalPeriod+"\",";
        	jsonStr+="\"companyCode\": \""+companyCode+"\",";
        	jsonStr+="\"currencyCode\": \""+currencyCode+"\",";
        	jsonStr+="\"requesterID\": \""+requesterID+"\",";
        	jsonStr+="\"requester\": \""+requester+"\",";
        	jsonStr+="\"docHeaderText\": \""+docHeaderText+"\",";
        	
        	//第二层
        	jsonStr+="\"formItem\": [";
        	String sql1=  "select * "+
        			  " from  formtable_main_" + formid + "_dt1 where mainid="+mainid;
        	
        	
        	RecordSet rs1 = new RecordSet();
        	rs1.execute(sql1);
        	writeLog("子表信息sql:"+sql1);
            while (rs1.next()) {
            	String	f2="1";
            	f1="0";
            	String costCenter= Util.null2String(rs1.getString("costCenter"));
            	String ItemNo = Util.null2String(rs1.getString("ItemNo"));//项目成本
            	String ItemNo1 = Util.null2String(rs1.getString("ItemNo1"));//项次
            	String docItemShortText = Util.null2String(rs1.getString("docItemShortText"));
            	String reimbursementAmount = Util.null2String(rs1.getString("reimbursementAmount"));
            	String taxAmount = Util.null2String(rs1.getString("taxAmount"));
            	String docItemLongText = Util.null2String(rs1.getString("docItemLongText"));
            	docItemLongText =Utillsjnew.splitAndFilterString(docItemLongText,10000);
            	
            	
            	
            	jsonStr+="{" ;
            	jsonStr+="\"ItemNo\":\""+ItemNo+"\",";
            	jsonStr+="\"docItemShortText\":\""+docItemShortText+"\",";
            	jsonStr+="\"reimbursementAmount\":\""+reimbursementAmount+"\",";
            	//明显表项次金额相加
                RecordSet rss = new RecordSet();                                
                sql = "select b.itemNo1,b.taxAmount, c.dysjxch ,  sum(c.je) as je "
                		+ "from formtable_main_" + formid + " a inner join "
                			+ "formtable_main_" + formid + "_dt1 b on a.id = b.mainid "
                				+ "inner join formtable_main_" + formid + "_dt2 c on b.id = c.mainid "
                					+ "where c.dysjxch = b.itemNo1 and  a.requestid = " + requestid + " "
                							+ "group by  c.dysjxch ,c.xc,b.taxAmount,b.itemNo1";                
                rss.execute(sql);
                while(rss.next()){                	
                	String je = Util.null2String(rss.getString("je"));  
                	jsonStr+="\"taxAmount\":\""+je+"\",";
                }
            	jsonStr+="\"taxAmount\":\""+taxAmount+"\",";
            	jsonStr+="\"costCenter\": \""+costCenter+"\",";
            	
            	//start modify by lsj 2019-01-17
            	if(workflowid.equals("3160")){
            		//招待费没有税金明细
            		jsonStr+="\"docItemLongText\":\""+docItemLongText+"\"";
            	}else{
            		jsonStr+="\"docItemLongText\":\""+docItemLongText+"\",";
                	//税金明细
                	jsonStr+="\"taxItem\": [";
                	String sql2="select  * from formtable_main_" + formid + "_dt2  where mainid="+mainid+ " and dysjxch="+ItemNo1+" order by xc" ;
    		      	RecordSet rs2 = new RecordSet();
    		      	rs2.execute(sql2);
    		      	writeLog("税金明细 子表2信息sql:"+sql2);
    		      	while(rs2.next()){
    		      		f2="0";
    		      		String taxItemNo = Util.null2String(rs2.getString("xc"));//项次
    		      		String taxInvoiceNo = Util.null2String(rs2.getString("fph"));//	发票号
    		      		String taxAmount1 = Util.null2String(rs2.getString("je"));//金额
    		      		jsonStr+="{" ;
    		      		jsonStr+="\"taxItemNo\":\""+taxItemNo+"\",";
    		      		jsonStr+="\"taxInvoiceNo\":\""+taxInvoiceNo+"\",";
    		      	    //明显表项次金额相加
    		            RecordSet rss2 = new RecordSet();   		            
    		            sql = "select b.itemNo1,b.taxAmount, c.dysjxch ,  sum(c.je) as je "
    		            		+ "from formtable_main_" + formid + " a inner join "
    		            			+ "formtable_main_" + formid + "_dt1 b on a.id = b.mainid "
    		            				+ "inner join formtable_main_" + formid + "_dt2 c on b.id = c.mainid "
    		            					+ "where c.dysjxch = b.itemNo1 and  a.requestid = " + requestid + " "
    		            							+ "group by  c.dysjxch ,c.xc,b.taxAmount,b.itemNo1";   		            
    		            rss2.execute(sql);
    		            while(rss2.next()){   		            	
    		            	String je1 = Util.null2String(rss2.getString("je"));
    		            	jsonStr+="\"taxAmount\":\""+je1+"\"";
    		            }
    		      		
    		      		
    		      		jsonStr+="}," ;
    		      	}
    		      	if("0".equals(f2)){
    			      	//去掉最后一个符号“，”
    				      	jsonStr=jsonStr.substring(0, jsonStr.length()-1);
    			    }
    		      	jsonStr+="]" ;
            	}
            	//end modify by lsj 2019-01-17
		      	jsonStr+="}," ;
            }
            if("0".equals(f1)){
		      	//去掉最后一个符号“，”
			      	jsonStr=jsonStr.substring(0, jsonStr.length()-1);
		    }
	    	jsonStr+="]}" ;
        }
        jsonStr+="}" ;
        writeLog("费用报销json格式："+jsonStr);
       String msg= "";
       String u=Utillsjnew.url+"/RESTAdapter/OA/PaymentRequestFIDocuments";
       msg=Utillsjnew.dyjk(jsonStr,u);
       writeLog("msg:"+msg);
       if(msg.indexOf("异常")>-1){
    	   requestInfo.getRequestManager().setMessageid(
                   requestid + "：传输数据");
//           requestInfo.getRequestManager().setMessagecontent(" 测试数据:"+jsonStr +"接口返回信息："+msg);
           requestInfo.getRequestManager().setMessagecontent("接口返回信息："+msg);
           rs.writeLog("退回流程");
           return Action.FAILURE_AND_CONTINUE; 
       }
       JSONObject jsStr = JSONObject.fromObject(msg);
       String flag=jsStr.getString("resultCode");
       String accountingDocNo=jsStr.getString("accountingDocNo");
       String postingDate=jsStr.getString("postingDate");
       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	   SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime;
		try {
			currentTime = formatter.parse(postingDate);
			postingDate = formatter1.format(currentTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
       if(flag.equals("S")){
    	   sql=" update formtable_main_" + formid + " set accountingDocNo="+accountingDocNo+",postingDate='"+postingDate+"' where requestid ="+ requestid;
    	  rs.writeLog("更新费用凭证号和过账日期"+sql);
    	   rs.execute(sql);
          return Action.SUCCESS;
          
       }else{
           requestInfo.getRequestManager().setMessageid(
                   requestid + "：传输数据");
//           requestInfo.getRequestManager().setMessagecontent(" 测试数据:"+jsonStr +"接口返回信息："+msg);
           requestInfo.getRequestManager().setMessagecontent("接口返回信息："+msg);
           rs.writeLog("退回流程");
           return Action.FAILURE_AND_CONTINUE; 
       }
	}
}
