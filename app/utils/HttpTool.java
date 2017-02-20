package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import org.json.JSONObject;


import play.Logger;


public class HttpTool {	
	/**
	 * 打开指定url，并读取返回数据
	 * @param url
	 * @return
	 */
	public static String getHttpInputStream(String url){
		long start = System.currentTimeMillis();
		String points = null;
		InputStream is = null;
		
		//根据拼凑的URL，打开连接
		URL getUrl;
		
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection   
        HttpURLConnection connection = null;
        
		try {
			getUrl = new URL(url);
			connection = (HttpURLConnection) getUrl.openConnection();
			//进行连接
			connection.connect();   
			is = connection.getInputStream();
			
			byte[] b;
			
			b = new byte[is.available()];
			is.read(b);
			points = new String(b,"utf-8");
			
		} catch (IOException e) {
			Logger.error("发起HTTP请求异常！！！ 异常信息："+e.getMessage());
			return null;
		}finally{
			if(null != connection){
				 // 断开连接   
			    connection.disconnect(); 
			}   
			if(null != is){
				 // 断开连接   
				try {
					is.close();
				} catch (IOException e) {
					Logger.error("关闭InputStream异常！！！ 异常信息："+e.getMessage());
				}
			}   
		}
        
		Logger.debug("本次调用WX服务器耗时：" + (double)(System.currentTimeMillis()-start) / 1000 + "(s)");
        // 取得输入流，并使用Reader读取   
        return points;
	}
	
//	public static void gethttps(String url){
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpPost post = new HttpPost(url.trim());
//		
//		StringEntity se = new StringEntity(holder.toString());
//		se.setContentType("application/json");
//		post.setEntity(se);
//		//设置连接超时时间(单位毫秒)
//		HttpConnectionParams.setConnectionTimeout(post.getParams(), 10000);
//		HttpResponse resp = client.execute(post);
//		HttpEntity entity = resp.getEntity();
//		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
//		StringBuffer sb = new StringBuffer();
//		String result = br.readLine();
//		
//		while (result != null) {
//			sb.append(result);
//			result = br.readLine();
//		}
//		JSONObject jsonObject = new JSONObject(sb.toString());
//		JSONObject jsonObject1 = jsonObject.getJSONObject("location");
//		String latitude = jsonObject1.get("lat").toString();
//		String longitude = jsonObject1.get("lng").toString();
//		String accuracy = jsonObject.get("accuracy").toString();
//	}
	
	public static void main(String[] args){
		String r = HttpTool.getHttpInputStream("https://api.mch.weixin.qq.com/pay/unifiedorder");
		System.out.println(r);
		JSONObject json = JSONObject.fromObject(r);
		
		System.out.println(json.get("openid")+"--"+json.get("access_token"));
	}
}