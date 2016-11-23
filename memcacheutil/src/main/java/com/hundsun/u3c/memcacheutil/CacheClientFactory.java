package com.hundsun.u3c.memcacheutil;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hundsun.u3c.util.Tools;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class CacheClientFactory {
	
	private static Logger log = LoggerFactory.getLogger("publicLog");
	
	private static Map<String,MemcachedClient> clients = new HashMap<String,MemcachedClient>();
	
	public static void init(String[] hosts)
	{
//		log.error("*****************hosts:"+hosts);
		for(int i=0;i<hosts.length;i++)
		{
			String ip = hosts[i].split(":")[0];
			MemcachedClient client = CacheClientFactory.clients.get(ip);
			if(client==null){
				client=getClient(hosts[i]);
				clients.put(ip, client);
			}
		}
	}
	
	public static MemcachedClient init(String host)
	{
		String localAddr = Tools.getLocalAddr();
		String localIp = "127.0.0.1";
		String givenSelfIp = Tools.getSelfUrl();
		String ip = host.split(":")[0];
		MemcachedClient client = null;
		if(localAddr.equals(ip))
		{
			client = getClient(localIp+":"+host.split(":")[1]);
			clients.put(localIp, client);
		}
		else if(host.equals(givenSelfIp))
		{
			client = getClient(host);
			clients.put(localIp,client);
		}
		else
		{
			client = getClient(host);
			clients.put(ip,client);
		}
		return client;
	}
	
	public static Collection<MemcachedClient> getAllClients()
	{
		return clients.values();
	}
	public static MemcachedClient getClientByHost(String host)
	{
		if(clients.isEmpty())
		{
			log.error("*****************The memcached clients have not inited yet!*****************");
			return null;
		}else{
			return clients.get(host);
		}
		
	}
	
	public static MemcachedClient getLocalClient()
	{
		return getClientByHost("127.0.0.1");
	}
	
	private static MemcachedClient getClient(String host)
	{
		MemcachedClient memcachedClient = null;
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(host));
		try
		{
//			builder.setConnectionPoolSize(50);
			memcachedClient = builder.build();
//			memcachedClient.setConnectTimeout(2000);
//			memcachedClient.setOpTimeout(2000);
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return memcachedClient;
	}
	
	public static void stopAll()
	{
		Collection<MemcachedClient> clients = getAllClients();
		Iterator<MemcachedClient> it = clients.iterator();
		while(it.hasNext())
		{
			CacheClientManager.shutdown(it.next());
		}
	}

}
