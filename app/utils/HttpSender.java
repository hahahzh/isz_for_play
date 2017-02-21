package utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpSender {

		 static String url = "http://sms.253.com/msg/";// 应用地址
		 static String un = "N8958939";// 账号
		 static String pw = "dG0HkmvaT673d9";// 密码
		 static String rd = 1+"";// 是否需要状态报告，需要1，不需要0
		 static String ex = null;// 扩展码
		 
			
//			static String host = Play.configuration.getProperty("sms.host");
//			static String accountId = Play.configuration.getProperty("sms.accountId");
//			static String password = Play.configuration.getProperty("sms.password");
//			static String serviceId = Play.configuration.getProperty("sms.serviceId");
//			private static final String URL_SMS = Play.configuration.getProperty("sms.host")+"?username="+
//			Play.configuration.getProperty("sms.accountId")+"&password="+
//					Play.configuration.getProperty("sms.password")+"&extend="+
//			Play.configuration.getProperty("sms.extend")+"&Level=1";
			
		 public static String batchSend(String phone, String msg) throws Exception {
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			GetMethod method = new GetMethod();
			try {
				URI base = new URI(url, false);
				method.setURI(new URI(base, "send", false));
				method.setQueryString(new NameValuePair[] { 
						new NameValuePair("un", un),
						new NameValuePair("pw", pw), 
						new NameValuePair("phone", phone),
						new NameValuePair("rd", rd), 
						new NameValuePair("msg", msg),
						new NameValuePair("ex", ex), 
					});
				int result = client.executeMethod(method);
				if (result == HttpStatus.SC_OK) {
					InputStream in = method.getResponseBodyAsStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					return URLDecoder.decode(baos.toString(), "UTF-8");
				} else {
					throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
				}
			} finally {
				method.releaseConnection();
				}
			}
		
		public static void main(String[] args) {

			String url = "http://sms.253.com/msg/";// 应用地址
			String un = "N8958939";// 账号
			String pw = "dG0HkmvaT673d9";// 密码
			String phone = "15000993473";// 手机号码，多个号码使用","分割
			String msg = "【253云通讯】您好，你的验证码是123456";// 短信内容
			String rd = 1+"";// 是否需要状态报告，需要1，不需要0
			String ex = null;// 扩展码

			
			try {
				String returnString = HttpSender.batchSend(phone, msg);
				System.out.println(returnString);
				System.out.println("dsad");
				// TODO 处理返回值,参见HTTP协议文档
			} catch (Exception e) {
				// TODO 处理异常
				e.printStackTrace();
			}
		}
}
