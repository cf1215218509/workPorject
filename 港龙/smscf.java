package weaver.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class smscf implements SmsService{

	@Override
	public boolean sendSMS(String smsId, String phoneno, String msg) {
		String url = "http://web.xchs.com/mt";
		String result = "";
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
        sb.append("<MtPacket>");  
        sb.append("<cpid>10111</cpid>"); //用户id，从平台获取，必填
        sb.append("<userpass>6344C3883F28418E468C5E9D0427C836</userpass>"); //接口验证码，从平台获取，必填
        sb.append("<port>917</port>"); //扩展码，从平台获取， 可以在原有基础上记性扩展，不超过10位，必填
        sb.append("<mid>0</mid>");  //交易流水号 ，由平台生成，不用填写
        sb.append("<cpmid>+"+smsId+"+</cpmid>"); //合作方流水号，必填
        sb.append("<flag>0</flag>"); //是否需要回推状态报告   0：不需要    1：需要  （若不填，默认为不回推状态报告）
        sb.append("<mobile>+"+phoneno+"+</mobile>"); //手机号码，多个手机号码时用“,”(逗号为英文状态)隔开，必填
        sb.append("<msg>+"+msg+"+</msg>");//短信内容 ，必填
        sb.append("</MtPacket>");
       // System.out.println(sb.toString());
        HttpClient httpClient = new HttpClient();
    	PostMethod post = new PostMethod(url);
		try { 
        	       	
        	post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        	post.setRequestHeader("content-type","text/xml");     	
        	
        	httpClient.setConnectionTimeout(200*1000);
        	
        	post.setRequestBody(sb.toString());
        	
        	int stat = httpClient.executeMethod(post);
        	System.out.println(stat);
        	if(stat==HttpStatus.SC_OK){
        		result = post.getResponseBodyAsString();
        	}
        	      
         //   System.out.println("返回状态xml为："+result);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	post.releaseConnection();
        }
		return true;
	}

}
