package com.yfq.memcacheutil;

import java.util.Map;

import com.yfq.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hundsun.u3c.commonutil.interfaceutil.InterfaceManager;
import com.hundsun.u3c.commonutil.jobidutil.JobIdManager;

import net.rubyeye.xmemcached.MemcachedClient;

public class CacheManager {
	
	private static Logger log = LoggerFactory.getLogger("publicLog");
	
	private static boolean isContext = false;
	
	private static String cacheIp4context;
	
	public static void initSingleCacheClient()
	{
		if(isContext)
		{
			//String url = Tools.getUrlByKey("//web_services/memcached/memcached_context");
			String url = InterfaceManager.getMemcache().getMemcachedContext();
			if(!"".equals(url))
			{
				cacheIp4context = url.split(":")[0];
				if(cacheIp4context.equals(Tools.getLocalAddr())||cacheIp4context.equals(Tools.getSelfUrl())||url.equals(Tools.getSelfUrl()))	{
					cacheIp4context = "127.0.0.1";
				}
				CacheClientFactory.init(url);
			}
			else
			{
				log.error("memcached_context is null");
			}
		}
	}
	
	public static void setContext(boolean isContext) {
		CacheManager.isContext = isContext;
	}

	public static void initCacheClients()
	{
		CacheClientFactory.init(Tools.getUrls());
	}
	
	public static void set(String key,String value)
	{
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				CacheClientManager.add(client, key, value);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
	}
	
	public static void set(String key,Object map)
	{
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				CacheClientManager.add(client, key, map);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
	}
	
	public static void delete(String key)
	{
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				CacheClientManager.delete(client, key);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
	}
	
	public static Map getMap(String key)
	{
		Map retn = null;
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				retn = CacheClientManager.getMap(client, key);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
		return retn;
	}
	
	public static Object getObject(String key)
	{
		Object retn = null;
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				retn = CacheClientManager.getMap(client, key);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
		return retn;
	}
	
	public static String get(String key)
	{
		String retn = "";
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				retn = CacheClientManager.get(client, key);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
		return retn;
	}
	
	public static byte[] getBytes(String key)
	{
		byte[] retn = null;
		if(isContext)
		{
			if(cacheIp4context!=null)
			{
				MemcachedClient client = CacheClientFactory.getClientByHost(cacheIp4context);
				retn = CacheClientManager.getBytes(client, key);
			}
			else
			{
				log.error("memcached_context is null!");
			}
		}
		return retn;
	}
	public static void set(String jobId,String key,String value)
	{
		
		String cachedIp = JobIdManager.getIpByJobId(jobId);
		log.debug("job id: "+jobId+" key: "+key+" value: "+value);
		log.debug("last data in "+cachedIp);
		log.debug("getUrls "+ Tools.getUrls());
		MemcachedClient client = null;
		client=CacheClientFactory.getClientByHost(cachedIp);
		
		if(client==null){
			String[] urls=null;
			try {
				urls = Tools.getOriginalUrls();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CacheClientFactory.init(urls);
			client=CacheClientFactory.getClientByHost(cachedIp);
		}
		CacheClientManager.add(client, key, value);
	}
	
	private static String getFromLocal(String host,String key)
	{
		MemcachedClient client = CacheClientFactory.getLocalClient();
		if(client==null) client = CacheClientFactory.init(Tools.getMemcachedUrlByIp(host));
		return CacheClientManager.get(client, key);
	}
	
	private static String getByHost(String host,String key)
	{
		MemcachedClient client = CacheClientFactory.getClientByHost(host);
		if(client==null) client = CacheClientFactory.init(Tools.getMemcachedUrlByIp(host));
		return CacheClientManager.get(client, key);
	}
	
	public static String getValueByJobId(String jobId,String key)
	{
		String cachedIp = JobIdManager.getIpByJobId(jobId);
		
		String selfIp = null;
		
		if(Tools.getSelfUrl()!=null&&!"".equals(Tools.getSelfUrl().trim()))
		{
			selfIp = Tools.getSelfUrl().split(":")[0];
		}
		
		log.debug("the data in "+cachedIp);
		log.debug("job id: "+jobId+" key: "+key);
		if(Tools.getLocalAddr().equals(cachedIp)||
				cachedIp.equals(selfIp))
		{
			return getFromLocal(cachedIp,key);
		}
		else
		{
			return getByHost(cachedIp,key);
		}
	}
	
	public static void stopAllClients()
	{
		CacheClientFactory.stopAll();
	}
	
}
