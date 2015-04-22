/*
 * 文 件 名:  ConnectionManager.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年5月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.core;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import route.RouterConstant;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年5月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConnectionManager
{
	private static Logger logger = Logger.getLogger(ConnectionManager.class);

	private static BlockingQueue<Connection> routers = new LinkedBlockingQueue<Connection>();
	
	private volatile static boolean running = false;
	
	
	public static void addConnection(Connection conn)
	{
		routers.add(conn);
		if(!running)
		{
			startup();
		}
	}
	
	public static Connection removeConnection(String key)
	{
		Connection remove = null;
		for(Connection conn : routers)
		{
			if(conn.getKey().equals(key))
			{
				remove = conn;
				break;
			}
		}
		if(remove!=null)
		{
			routers.remove(remove);
		}
		return remove;
	}
	
	public static void sendConnReq(Connection conn,String userId,String authInfo)
	{
		byte[] reqData = IOParser.generateConnReq(userId, authInfo);
		try
		{
			conn.sendMessage(reqData);
		} 
		catch (SocketException e)
		{
			conn.stateConningToDisConn();
		}	
	}
	
	public static void sendDataReq(Connection conn,String message)
	{
		byte[] data = IOParser.generateDataReq("", message);
		try
		{
			conn.sendMessage(data);
		} 
		catch (SocketException e)
		{
			conn.stateConningToDisConn();
		}	
	}
	
	public static void sendPingReq(Connection conn)
	{
		byte[] pingData = IOParser.generateHeartReq();
		try
		{
			conn.sendMessage(pingData);
		} 
		catch (SocketException e)
		{
			conn.stateConnToDisconn();
		}
	}
	
	/**
	 * 发送心跳与清理心跳超时的连接
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void startup()
	{
		if(running)
		{
			return;
		}
		running = true;
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				while(running)
				{
					if(routers.size()==0)
					{
						break;
					}
					List<Connection> removeList = new ArrayList<Connection>();
					for(Connection conn : routers)
					{
						if(conn.isConnected())
						{
							if(System.currentTimeMillis()>=conn.getSendTime())
							{
								sendPingReq(conn);
								conn.setSendTime(System.currentTimeMillis() + RouterConstant.HEARTBEAT_INTERVAL);	
								continue;
							}
						}
						
						if(System.currentTimeMillis()>(conn.getReceiveTime()+RouterConstant.TIMEOUT_INTERVAL))
						{
							conn.stateConnToDisconn();
							removeList.add(conn);
						}						
					}
					
					if(removeList.size()>0)
					{
						for(Connection conn : removeList)
						{
							routers.remove(conn);
							conn.close();
							logger.debug("连接"+conn.getKey()+"长期未接收心跳或数据，被移出在线队列。");
						}
					}
					
					try
					{
						Thread.sleep(500);
					} 
					catch (InterruptedException e)
					{
					}
				}				
				running = false;
			}
		};
		t.start();
	}

	/**
	 * 发送数据
	 * <功能详细描述>
	 * @param message [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void startSendMessage(final byte[] message)
	{
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				long count = 0;
				while(running)
				{
					if(routers.size()>0)
					{
						for(Connection conn : routers)
						{
							if(!conn.isConnected())
							{
								continue;
							}
							//10秒发送一次数据
							long sendTime = conn.getSendDataTime()+5000;
							if(System.currentTimeMillis()-sendTime>0)
							{
								try
								{
									conn.sendMessage(message);
									conn.setSendDataTime(System.currentTimeMillis());
									count ++;
								} 
								catch (SocketException e)
								{
									e.printStackTrace();
								}
							}
						}
					}
					try
					{
						Thread.sleep(500);
					} 
					catch (InterruptedException e)
					{
					}
				}
				logger.debug("在线客户端["+routers.size()+"]个，共发送["+count+"]条数据。");
			}
		};
		t.start();
	}
	
	public static void shutdown()
	{
		running = false;
		for(Connection conn : routers)
		{
			conn.getReceiver().shutdown();			
			conn.close();
		}
		
	}
}
