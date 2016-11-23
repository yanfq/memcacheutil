package com.hundsun.u3c.memcacheutil;

import com.hundsun.u3c.util.Tools;

public class Testor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		CacheManager.initCacheClients();
		
//		for(int i=0;i<100;i++)
//		{
//			new Thread(new Runnable(){
//				public void run()
//				{
//					CacheManager.set("192.168.52.111_xxyy", "xxyy_test", "hello,world again!");
//					System.out.println(CacheManager.getValueByJobId("192.168.52.111_xxyy", "xxyy_test"));
//				}
//			}).start();
//
//		}
//		
//		try
//		{
//			Thread.sleep(100*1000);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
//		CacheManager.set("192.168.84.47_xxyy", "xxyy_test","hello,world,Again!");
//		
//		System.out.println(CacheManager.getValueByJobId("192.168.84.47_xxyy", "xxyy_test"));
//		
//		CacheManager.stopAllClients();
		
//		CacheManager.setContext(true);
//		CacheManager.initSingleCacheClient();
//		CacheManager.set("test4context", "hello,context!");
		System.out.println(CacheManager.getValueByJobId("192.168.84.152_b6d02752-c738-4c99-b4c7-8c32865841c7", "192.168.84.152_b6d02752-c738-4c99-b4c7-8c32865841c7.IVR"));
		
//		System.out.println(Tools.getSelfUrl());
	}

}
