package com.hundsun.u3c.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hundsun.u3c.commonutil.interfaceutil.InterfaceManager;

public class Tools {
	
	//增加是否读配置的开关，默认关闭
	private static boolean isReadConfig = false;
	
	private static Logger log = LoggerFactory.getLogger("publicLog");
	
	private static String localAddr = null;
	
	private static String[] urls = null;
	
	private static String selfUrl = null;
	
	private static final String MEMECACHED_PORT = "8100";
	
	public static String getLocalAddr()
	{
		if(localAddr == null)
		{
			localAddr = com.hundsun.u3c.commonutil.util.Tools.getLocalAddr();
		}
		return localAddr;
	}
	
//	public static String getUrlByKey(String key)
//	{
//		String retn = "";
//		try
//		{
//			XmlParser parser = XmlParserManage.getXmlParser();
//			retn = parser.getNodeValue(key);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return retn;
//	}
	
	public static String[] getUrls()
	{
		if(urls==null||urls.length==0)
		{
			try
			{
//				XmlParser parser = XmlParserManage.getXmlParser();
//
//				String urlStrs = parser.getNodeValue("//web_services/memcached/memcached_urls")
//										.replaceAll("127.0.0.1", getLocalAddr())
//										.replaceAll("localhost", getLocalAddr());
//
//				if(urlStrs==null||"".equals(urlStrs.trim()))
//				{
//					log.error("could not get property-//web_services/memcached/memcached_urls from interface.xml");
//					urls = new String[]{};
//				}
//
//				urls = urlStrs.split(",");

				String [] urlArr = getOriginalUrls();
				if(urlArr == null){
					urls = new  String[]{};
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
				urls = new  String[]{};
			}
		}
		return urls;
	}
	
	public static String getMemcachedUrlByIp(String ip)
	{
//		String[] urls = getUrls();
//		String memcachedUrl = null;
//		for(int i=0;i<urls.length;i++)
//		{
//			if(urls[i].startsWith(ip)) memcachedUrl = urls[i];
//		}
//		return memcachedUrl;
		return ip+":"+MEMECACHED_PORT;
	}
	
	public static String getSelfUrl()
	{
		if(selfUrl == null)
		{


			try
			{
//				XmlParser parser = XmlParserManage.getXmlParser();
//
//				String urls = parser.getNodeValue("//web_services/memcached/memcached_urls")
//										.replaceAll("127.0.0.1", getLocalAddr())
//										.replaceAll("localhost", getLocalAddr());
//
//				if(urls==null||"".equals(urls.trim()))
//				{
//					log.error("could not get property-//web_services/memcached/memcached_urls from interface.xml");
//
//				}
//				else
//				{
//					selfUrl = urls.split(",")[0];
//				}

				String [] urlArr = getOriginalUrls();
				if(urlArr != null){
					selfUrl = urlArr[0];
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return selfUrl;
	}
	
	
	/**
	 * 获取MemcacheUrl
	 * @return
	 */
	public static String [] getOriginalUrls() throws Exception{
		String [] urlArr = InterfaceManager.getMemcache().getUrls();
		if(urlArr ==null || urlArr.length == 0){
			log.error("memcached_urls is null");
			return  null;
		}
		for(String url: urlArr){
			log.error("url============:"+url);
		}
//		String urlArrStr = Arrays.toString(urlArr).replaceAll("127.0.0.1", getLocalAddr())
//				.replaceAll("localhost", getLocalAddr()).replaceAll("\\[" ,"")
//				.replaceAll("\\]","");

//		return urlArrStr.split(",");
		return urlArr;
	}
}
