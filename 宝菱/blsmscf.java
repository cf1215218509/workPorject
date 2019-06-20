package weaver.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;

import weaver.general.Base64;
import weaver.general.BaseBean;
import weaver.general.MD5;

public class blsmscf extends BaseBean implements SmsService{

	@Override
	public boolean sendSMS(String smsId, String phoneno, String msg) {
		boolean flag =false;
		Base64 bs64 = new Base64();
		
		String url = "http://112.35.1.155:1992/sms/norsubmit";
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		String mobiles = "15861121835";
		String content = "WELCOME";
		String addSerial = "";
		
		sb.append("{\"ecName\":\"ecName\",");//公司名称
		sb.append("\"apId\":\"apId\",");//接口账号用户名
		sb.append("\"mobiles\":\"" + mobiles + "\",");//收信手机号码
		sb.append("\"content\":\"" + content + "\",");//短信内容
		sb.append("\"sign\":\"sign\",");//签名编码
		sb.append("\"addSerial\":\"" + addSerial + "\",");//扩展码
		
		sb2.append("ecName");
		sb2.append("apId");
		sb2.append("secretKey");
		sb2.append("mobiles");
		sb2.append("content");
		sb2.append("sign");
		sb2.append("addSerial");
		
		
		String arr2 = sb2.toString();
		MD5 md5 = new MD5();
		String enmd5 = md5.getMD5ofStr(arr2);		
		sb.append("\"mac\":\"" + enmd5 + "\"}");//参数校验序列
		
		
		byte [] arr = sb.toString().getBytes();
		byte[] dearr = bs64.encode(arr);
		
		String result = new String(dearr);

        HttpClient httpClient = new HttpClient();
    	PostMethod post = new PostMethod(url);
		try { 
        	       	
        	post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        	post.setRequestHeader("content-type","text/xml");     	
        	post.setRequestBody(sb.toString());
        	
        	int stat = httpClient.executeMethod(post);
        	System.out.println(stat);
        	
        	if(stat==HttpStatus.SC_OK){
        		result = post.getResponseBodyAsString();
        		JSONObject jsonObj = new JSONObject(result);
        		
                String request = (String) jsonObj.get("success");
        		System.out.println(request);
        		if(request.equals("true")){
        			flag=true;			
        		}
        	}
        	      
         
        	
        } catch (Exception e) {
            e.printStackTrace();
            writeLog("短信异常："+e);
        }finally{
        	post.releaseConnection();
        }
		
		return flag;
	}

}
