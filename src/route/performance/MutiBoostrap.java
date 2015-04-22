/*
 * 文 件 名:  MutiBoostrap.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年5月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

import route.RouterConstant;
import route.performance.core.Connection;
import route.performance.core.ConnectionManager;
import route.performance.core.Receiver;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年5月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MutiBoostrap
{
	private static Logger logger = Logger.getLogger(MutiBoostrap.class);
	

	/** <一句话功能简述>
	 * <功能详细描述>
	 * @param args [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void main(String[] args) throws Exception
	{
		/*
		 * 51990 <==> {"SN":"86999900000014000000SN-900019999","MAC":"MAC-900019999"}
		 * 31991 <==> {"SN":"86999900000014000000SN-90000","MAC":"MAC-90000"}
		 */
		int deviceId = 31991;
		for(int i=0; i<4000; i++)
		{
			String userId = "20000";
			String authInfo = "{\"SN\":\"86999900000014000000SN-9000"+i+"\",\"MAC\":\"MAC-9000"+i+"\"}";
			login(deviceId+"",userId,authInfo);
			deviceId++;
		}
				
		//控制发送数据
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
	
	
		//监听标准输入的退出指令
		while((line=br.readLine())!=null)
		{			
			if("exit".equals(line))
			{
				ConnectionManager.shutdown();
				break;
			}
		}

	}
	
	public static void login(String deviceId,String userId,String authInfo)
	{
		//测试时修改下设备id
		Socket s = connect();		
		Connection conn = new Connection(deviceId,s);
		Receiver receiver = new Receiver(conn);
		conn.setReceiver(receiver);
		try
		{
			//发送连接请求
			ConnectionManager.sendConnReq(conn, userId, authInfo);
			//启动接收线程
			receiver.startup();
			//启动发送心跳
//			ConnectionManager.addConnection(conn);			
			
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
