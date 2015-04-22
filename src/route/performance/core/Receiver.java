/*
 * 文 件 名:  Receiver.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import route.RouterConstant;
import route.exception.ReadEOFException;

/**
 * 消息接收处理线程
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Receiver implements Runnable
{
	private Logger logger = Logger.getLogger(this.getClass());
	//线程开启标志
	private static volatile boolean running = false;
	
	private static int recThreadCount = 0;
	private static Object recThreadLock = new Object();
	
	public Connection connection;
	
	public Receiver(Connection connection)
	{
		super();
		this.connection = connection;
	}

	/**
	 * 接收从OneNet来到的消息。并根据消息类型调用对应的Handler解析处理
	 */
	@Override
	public void run()
	{
		//记录连续接收到的错误消息类型个数
		int errCount = 0;
		
		synchronized(recThreadLock)
		{
			recThreadCount++;
			logger.debug("连接"+connection.getKey()+"的消息接收线程启动。共启动消息线程 "+recThreadCount+" 个");
		}
		
		while(running)
		{
			try
			{	
				InputStream is = connection.getInputStream();
				if(is==null)
				{
					//连接中断，此处等待进入休眠状态等待，等待心跳线程与OneNet重连
					Thread.sleep(3000);
					continue;
				}
				
				//读取消息类型				
				byte mesType = this.readByte(is);
				
				/*
				 * 读取报文长度：
				 * 长度值的低位部分放在传输的前面字节，高位放在后面。
				 * 每个字节的最高位为延续指示位。延续指示位为1时，标示后面字节也是长度值。
				 * 最多可延续4个字节
				 */				
				ByteBuffer buff = ByteBuffer.allocate(4);
				
				for(int i=0; i<4; i++)
				{
					byte b = this.readByte(is);
					buff.put(b);
					//如果b<0表示高位为1，有下一个字节
					if(b>=0)
					{
						break;
					}
				}
				buff.flip();
				
				//计算出长度，并根据长度读取完整报文
				int length = IOParser.calculateLen(buff);
				byte[] body = new byte[length];	
				if(length>0)
				{			
					int readLen = 0;//本次读取的字节数
					int position = 0;//已经读取数据的下一个位置
					while((readLen=is.read(body,position,(length-position)))>=0)
					{
						position = position + readLen;
						if(position==length)
						{
							break;
						}
					}
					if(readLen<0)
					{//读取到EOF，socket已close或reset
						throw new ReadEOFException("连接"+connection.getKey()+"的读取数据错误");
					}
				}

				ByteBuffer bodyBuff = ByteBuffer.wrap(body);
				
				//根据消息类型创建特定Handler来解析消息内容
				MessageHandler handler = MessageHandlerFactory.newInstance(mesType);
				if(handler==null)
				{
					logger.error("连接"+connection.getKey()+"的未知的消息类型："+mesType);
					errCount++;
					/*
					 * 判断连续接收到的错误消息类型，是否达到了最大错误，
					 * 如果达到了则证明服务器端传送的消息已混乱，则需要断线重连。		 
					 */
					if(errCount>=RouterConstant.RECEIVE_MAX_ERR_COUNT)
					{
						connection.stateConnToDisconn();
					}
					continue;
				}
				Map<String,Object> params = new HashMap<String,Object>();
				params.put(MessageHandler.PARAM_CONNECTION, connection);
				handler.hand(bodyBuff, params);
				
				connection.updateHeartTime();
				errCount = 0;
			} 
			catch (ReadEOFException e)
			{
				try
				{
					Thread.sleep(10);
				} 
				catch (InterruptedException e1)
				{
				}
			}
			catch (SocketException e)
			{
				logger.error("连接"+connection.getKey()+"接收数据时连接断开，修改状态为断连",e);
				if(connection.isConnected())
				{
					connection.stateConnToDisconn();
				}
				try
				{
					Thread.sleep(3000);
				} 
				catch (InterruptedException e1)
				{
				}
			}
			catch (Exception e)
			{
				logger.error("连接"+connection.getKey()+"的接收消息线程解析出错:",e);
			}
		}
		running = false;
		logger.debug("连接"+connection.getKey()+"的消息接收线程停止。");
	}
	
	/**
	 * 从输入流中读取单个字节
	 * <功能详细描述>
	 * @param is
	 * @return
	 * @throws SocketException [参数说明]
	 * 
	 * @return byte [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	private byte readByte(InputStream is) throws SocketException
	{
		try
		{
			int i = is.read();
			if(i<0)
			{//读取到流尾EOF,socket 被关闭或被reset
				throw new ReadEOFException("读取数据结束.");
			}
			return (byte)i;
		} 
		catch (IOException e)
		{
			throw new SocketException("读取数据错误");
		}
		
	}

	/**
	 * 启动接收线程
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void startup()
	{
		Thread t = new Thread(this);
		running = true;
		t.start();
	}
	
	/**
	 * 停止接收线程
	 * <功能详细描述> [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void shutdown()
	{
		running = false;
	}
	
	/**
	 * 判断消息类型是否合法
	 * <功能详细描述>
	 * @param mesgType
	 * @return [参数说明]
	 * 
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public boolean isFormatMesg(byte mesgType)
	{
		if(mesgType==RouterConstant.MESGTYPE_CONN_RESP)
		{
			return true;
		}
		if(mesgType==RouterConstant.MESGTYPE_HEART_RESP)
		{
			return true;
		}
		if(mesgType==RouterConstant.MESGTYPE_DATA)
		{
			return true;
		}
		return false;
	}

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
