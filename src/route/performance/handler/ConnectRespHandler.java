/*
 * 文 件 名:  ConnectRespHandler.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月22日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.handler;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.log4j.Logger;

import route.RouterConstant;
import route.performance.core.Connection;
import route.performance.core.ConnectionManager;
import route.performance.core.MessageHandler;


/**
 * 连接响应消息处理类
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConnectRespHandler implements MessageHandler
{
	private static int connCount = 0;
	private static Object connLock = new Object();
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 解析连接响应
	 * @param mesg
	 * @param params
	 */
	@Override
	public void hand(ByteBuffer mesg, Map<String, Object> params)
	{
		if(mesg.remaining()<2)
		{
			logger.error("连接响应消息格式错误。返回字节数个数["+mesg.remaining()+"]<2");
			return;
		}
		Connection conn = (Connection)params.get(PARAM_CONNECTION);
		
		//读取返回码
		byte flag = mesg.get(1);
		if(RouterConstant.CONN_RESP_SUCCESS!=flag)
		{
			conn.stateConningToDisConn();
			logger.error(conn.getKey()+"连接不成功，响应码为："+flag);
			logger.error("响应码含义：0：连接成功；1：验证失败-协议不匹配；2：验证失败-设备ID鉴权失败；"
					+ "3：验证失败-服务器失败；4：验证失败-用户名密码鉴权失败；5：验证失败-未授权；");
			conn.getReceiver().shutdown();
			return;
		}
		//若连接成功，设置连接状态
		conn.setStateConnected();
		ConnectionManager.addConnection(conn);
		synchronized(connLock)
		{
			connCount ++;
			logger.debug(conn.getKey()+"长连接建立成功,已成功连接 "+connCount+" 个终端");
		}
		
	}

}
