package com.gy.splider.global;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;











import com.gy.splder.util.ConnectAdslNet;
import com.gy.splder.util.DBUtil;
import com.gy.splider.bean.ProxyIpEntity;
import com.gy.splider.storage.IpStorage;

public class Global {
	public final static String WEBURL="https://movie.douban.com/";
	public final static String WEBMOVIE ="subject";
	public final static String WEBACTOR = "celebrity";
	public final static String WEBDIRECTOR = "celebrity";
	public final static String WEBSCREENWIRTER ="celebrity";
	public final static int TOTALNUMBER = 1000;
	public final static Object OBJECT = new Object();
	public final static String USERAGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
	
	//private static String IP = "183.237.25.5";//9999
	
	//private static String IP = "183.131.104.250";//80*
	
	//private static String IP = "159.226.224.180";//3128*
	
	/*public static Connection getProxyConnection(String movieUrl,String paramtype,String paramId){
		IpStorage ipStorage = new IpStorage();
		ProxyIpEntity proxyIpEntity = ipStorage.getProxyIpEntity();
		try{
			return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(proxyIpEntity.getIp(), proxyIpEntity.getPort()).timeout(10000);
		}catch(Exception e){
			ipStorage.deleteProxyIp(proxyIpEntity);
			ProxyIpEntity proxyIpEntity1 = ipStorage.getProxyIpEntity();
			return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(proxyIpEntity1.getIp(), proxyIpEntity1.getPort()).timeout(10000);
		}
		
		//return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy("178.32.87.230", 3128).timeout(10000);
	}*/
	
	public static Document getProxyConnectionDocument(String movieUrl,String paramtype,String paramId) throws IOException{
		IpStorage ipStorage = new IpStorage();
		ProxyIpEntity proxyIpEntity = ipStorage.getProxyIpEntity();
		try{
			return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(proxyIpEntity.getIp(), proxyIpEntity.getPort()).timeout(10000).get();
			//return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).timeout(10000).get();
		}catch(Exception e){
			ipStorage.deleteProxyIp(proxyIpEntity);
			ProxyIpEntity proxyIpEntity1 = ipStorage.getProxyIpEntity();
			return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(proxyIpEntity1.getIp(), proxyIpEntity1.getPort()).timeout(10000).get();
			//return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).timeout(10000).get();
		}
		
		//return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy("178.32.87.230", 3128).timeout(10000);
	}
	
	public static Document postProxyConnectionDocument(String movieUrl,String paramtype,String paramId) throws IOException{
		IpStorage ipStorage = new IpStorage();
		ProxyIpEntity proxyIpEntity = ipStorage.getProxyIpEntity();
		try{
			return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(proxyIpEntity.getIp(), proxyIpEntity.getPort()).timeout(10000).post();
		}catch(Exception e){
			ipStorage.deleteProxyIp(proxyIpEntity);
			ProxyIpEntity proxyIpEntity1 = ipStorage.getProxyIpEntity();
			return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(proxyIpEntity1.getIp(), proxyIpEntity1.getPort()).timeout(10000).post();
		}
		
		//return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy("178.32.87.230", 3128).timeout(10000);
	}
	
	
	/*public static Document getProxyConnection(String movieUrl,String paramtype,String paramId,int methodtype) throws IOException{
		//return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).proxy(IP, 9999).timeout(10000);
		try {  
            return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).timeout(20000).get();  
        } catch (Exception e) {  
            e.printStackTrace();//拨号一下  
            try{
            	ConnectAdslNet.reconnectAdsl("宽带","admin","admin");
            	return Jsoup.connect(movieUrl + "/" + paramtype + "/"+ paramId).timeout(20000).get();
            }catch(Exception e1){
            	e1.printStackTrace();
            }
        }
		return null;  
	}*/
	
	
	
}
