/*
 * 文 件 名:  Bootstrap.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年5月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

import route.core.Connection;
import route.core.ConnectionManager;
import route.core.MessageSendManager;
import route.core.Receiver;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年5月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Bootstrap
{
	private static Logger logger = Logger.getLogger(Bootstrap.class);

	/** <一句话功能简述>
	 * <功能详细描述>
	 * @param args [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void main(String[] args)
	{		
		//测试时修改下设备id
		String deviceId = "15107";
		Socket s = connect();		
		Connection conn = new Connection(deviceId,s);
		Receiver receiver = new Receiver(conn);
		conn.setReceiver(receiver);
		try
		{
			//发送连接请求
			ConnectionManager.sendConnReq(conn, RouterConstant.USER_ID, RouterConstant.AUTH_INFO);
			
			//启动接收线程
			receiver.startup();
			
			//启动发送心跳
			ConnectionManager.addConnection(conn);
			
			//发送数据
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
		
		
			//监听标准输入的退出指令
			while((line=br.readLine())!=null)
			{
				System.out.println(line);
				if("exit".equals(line))
				{
					receiver.shutdown();
					break;
				}
				//10055是OneNetAgent的设备id
				MessageSendManager.sendMessage("", line,conn);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static Socket connect()
	{
		Socket socket = null;
		try
		{
			//新建立连接，并将状态设置为正在重连
			socket = new Socket(RouterConstant.SERVER_IP,RouterConstant.PORT);
			socket.setKeepAlive(true);			
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		return socket;
	}

}
