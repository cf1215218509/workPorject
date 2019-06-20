<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page
	import="java.sql.Connection,java.sql.DriverManager,java.sql.PreparedStatement,java.sql.ResultSet,weaver.general.*"%>
<jsp:useBean id="mysmt" class="weaver.conn.RecordSet" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" />
<jsp:useBean id="rss" class="weaver.conn.RecordSet" />
<jsp:useBean id="rssr" class="weaver.conn.RecordSet" />
<jsp:useBean id="rr" class="weaver.conn.RecordSet" />
<jsp:useBean id="ModeRight" class="weaver.formmode.setup.ModeRightInfo" />


<%
	String ryid = Util.null2String(request.getParameter("id"));//id
	String[] arr = ryid.split(",");
	String mx1 = "";
	String data = "";
	for (int i = 0; i < arr.length; i++) {
		String idd = arr[i];
		String sql = "select a.id,loginid,sex,departmentid,jobtitle,mobile,maritalstatus,homeaddress,certificatenum,educationlevel,year(getdate())-year(birthday) age,b.field2,b.field4,b.field7,c.yjfl from hrmresource a left join cus_fielddata b on a.id = b.id left join uf_rcejfl c on c.id = b.field7 where a.id = "
				+ idd;

		boolean result = false;
		mysmt.executeSql(sql);
		if (mysmt.next()) {
			String id = mysmt.getString("id");
			String loginid = mysmt.getString("loginid");
			String sex = mysmt.getString("sex");
			String departmentid = mysmt.getString("departmentid");
			String jobtitle = mysmt.getString("jobtitle");
			String mobile = mysmt.getString("mobile");
			String maritalstatus = mysmt.getString("maritalstatus");
			String homeaddress = mysmt.getString("homeaddress");
			String certificatenum = mysmt.getString("certificatenum");
			String educationlevel = mysmt.getString("educationlevel");
			String nl = mysmt.getString("age");
			String field2 = mysmt.getString("field2");
			String field4 = mysmt.getString("field4");
			String field7 = mysmt.getString("field7");
			String yjfl = mysmt.getString("yjfl");

			sql = "select count(*) sl from uf_rcrkdjb where xm = " + id;
			rss.executeSql(sql);
			if (rss.next()) {
				int sl = rss.getInt("sl");
				if (sl > 0) {
					sql = "update uf_rcrkdjb set gh = '" + loginid + "',xb = '" + sex + "',xszbm = '"
							+ departmentid + "',szgw = '" + jobtitle + "',lxdh = '" + mobile + "',hyzt = '"
							+ maritalstatus + "',jzdz = '" + homeaddress + "',sfzhm = '" + certificatenum
							+ "',xl = '" + educationlevel + "',nl = '" + nl + "',byyx = '" + field2
							+ "',rzrq = '" + field4 + "',rcfl = '" + field7 + "',lb = '" + yjfl
							+ "' where xm = " + id;
					result = rssr.executeSql(sql);
				} else {
					sql = "insert into uf_rcrkdjb (xm,gh,xb,xszbm,szgw,lxdh,hyzt,jzdz,sfzhm,xl,nl,byyx,rzrq,rcfl,lb) values ('"
							+ id + "','" + loginid + "','" + sex + "','" + departmentid + "','" + jobtitle
							+ "','" + mobile + "','" + maritalstatus + "','" + homeaddress + "','"
							+ certificatenum + "','" + educationlevel + "','" + nl + "','" + field2 + "','"
							+ field4 + "','" + field7 + "','" + yjfl + "')";

					result = rs.executeSql(sql);

				}

			}
			sql = "select id from uf_rcrkdjb where xm = " + id;
			rr.executeSql(sql);
			if (rr.next()) {
				int billid = rr.getInt(1);
				int cd = Integer.parseInt(id);
				ModeRight.rebuildModeDataShareByEdit(cd,7,billid);
				
			}

		}
		mx1 = "{\"result\":\"" + result + "\"}";
		data = "[" + mx1 + "]";

	}
	out.print(data);
%>