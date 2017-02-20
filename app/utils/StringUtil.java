/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类，存放通用的字符串操作
 * @author kortide
 */
public abstract class StringUtil {

    public static boolean isEmpty(String s){
    	if(s == null || s.isEmpty()){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 删除标签操作
     * @param content
     * @return
     */
    public static String removeTag(String content) {
        if (content == null) {
            return "";
        }
        content = content.replaceAll("</?[^>]+>", "");   //剔出了<html>的标签
        content = content.replace("&nbsp;", "");
        content = content.replace(".", "");
        content = content.replace("\"", "‘");
        content = content.replace("'", "‘");
        content = content.replaceAll("\\s*|\t|\r|\n", "");//去除字符串中的空格,回车,换行符,制表符
        return content;
    }

    /**
     * 替换实体符号&为&amp;
     * @param str
     * @return
     */
    public static String strReplace(String str) {
        if(str==null) return "";
        return str.replace("&", "&amp;");
    }
    
    // 从字节数组到十六进制字符串转换
    public static String Bytes2HexString(byte[] b) {
     byte[] buff = new byte[2 * b.length];
     for (int i = 0; i < b.length; i++) {
      buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
      buff[2 * i + 1] = hex[b[i] & 0x0f];
     }
     return new String(buff);
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
     byte[] b = new byte[hexstr.length() / 2];
     int j = 0;
     for (int i = 0; i < b.length; i++) {
      char c0 = hexstr.charAt(j++);
      char c1 = hexstr.charAt(j++);
      b[i] = (byte) ((parse(c0) << 4) | parse(c1));
     }
     return b;
    }
    
 // 整数到字节数组转换
    public static byte[] int2bytes(int n) {
     byte[] ab = new byte[4];
     ab[0] = (byte) (0xff & n);
     ab[1] = (byte) ((0xff00 & n) >> 8);
     ab[2] = (byte) ((0xff0000 & n) >> 16);
     ab[3] = (byte) ((0xff000000 & n) >> 24);
     return ab;
    }

    // 字节数组到整数的转换
    public static int bytes2int(byte b[]) {
     int s = 0;
     s = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
       | (b[3] & 0xff);
     return s;
    }

    // 字节转换到字符
    public static char byte2char(byte b) {
     return (char) b;
    }

    private final static byte[] hex = "0123456789ABCDEF".getBytes();

    private static int parse(char c) {
     if (c >= 'a')
      return (c - 'a' + 10) & 0x0f;
     if (c >= 'A')
      return (c - 'A' + 10) & 0x0f;
     return (c - '0') & 0x0f;
    }
    
    public static double distanceBetween(double latitude1, double longitude1,double latitude2, double longitude2) {
		double distance = 0.0;
		double deltaLat = Math.toRadians(latitude2 - latitude1);
		double deltaLon = Math.toRadians(longitude2 - longitude1);
		latitude1 = Math.toRadians(latitude1);
		latitude2 = Math.toRadians(latitude2);
		longitude1 = Math.toRadians(longitude1);
		longitude2 = Math.toRadians(longitude2);

		double earthRadius = 6371 * 1000;
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		distance = earthRadius * c;
		return distance;
	}
    
    public static <T> List<T> removalDup(List<T> input){
         List<T> results = new ArrayList<T>();
          for (int i=0;i<input.size();i++){
              if(!results.contains(input.get(i))) results.add(input.get(i));
          }
          return results;
    }
    
    public static void main(String[] args){
    	System.out.print(StringUtil.distanceBetween(31.210015,121.608694, 31.211957, 121.607527));
    }
}
