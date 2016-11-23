package com.yfq.memcacheutil;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeoutException;

public class CacheClientManager {
	private static int EXPIRE_TIME = 0;
	private static Logger log = LoggerFactory.getLogger("publicLog");
	public static void add(MemcachedClient client,String key,Object value)
	{
		try
		{
			client.setWithNoReply(key, EXPIRE_TIME, value);
		}
		catch (MemcachedException e) {
			log.error("�������key:"+key+",value:"+value+"�쳣��:"+e);
		}
		catch (InterruptedException e) {
			log.error("�������key:"+key+",value:"+value+"�쳣��:"+e);
		}
	}
	
	public static void add(MemcachedClient client,String key,String value)
	{
		try
		{
			client.setWithNoReply(key, EXPIRE_TIME, value);
		}
		catch (MemcachedException e) {
			log.error("�������key:"+key+",value:"+value+"�쳣��:"+e);
		}
		catch (InterruptedException e) {
			log.error("�������key:"+key+",value:"+value+"�쳣��:"+e);
		}
	}
	
	public static Map getMap(MemcachedClient client,String key)
	{
		Map retn = null;
		try
		{
			retn = client.get(key);
		}
		catch (MemcachedException e) {
    		log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		catch (TimeoutException e) {
			log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		catch (InterruptedException e) {
			log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		return retn;
	}
	
	public static String get(MemcachedClient client,String key)
	{
		String retn = null;
		try
		{
			retn = client.get(key);
		}
		catch (MemcachedException e) {
    		log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		catch (TimeoutException e) {
			log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		catch (InterruptedException e) {
			log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		return retn;
	}
	
	public static byte[] getBytes(MemcachedClient client,String key)
	{
		byte[] retn = null;
		try
		{
			retn = client.get(key);
		}
		catch (MemcachedException e) {
    		log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		catch (TimeoutException e) {
			log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		catch (InterruptedException e) {
			log.error("��ȡ����key:"+key+"�쳣��:"+e);
		}
		return retn;
	}
	
	public static void delete(MemcachedClient client,String key)
	{
		try
		{
			client.deleteWithNoReply(key);
		}
		catch (MemcachedException e) {
			log.error("ɾ������key:"+key+"�쳣��:"+e);
		}
		catch (InterruptedException e) {
			log.error("ɾ������key:"+key+"�쳣��:"+e);
		}	
	}
	
	public static void shutdown(MemcachedClient client)
	{
		try
		{
			client.shutdown();
		}
		catch(Exception e)
		{
			log.error("shutdown�쳣��:"+e);
		}
	}
}
