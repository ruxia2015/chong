/*
 * 文 件 名:  DataReceiveHandler.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月24日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.handler;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.log4j.Logger;

import route.RouterConstant;
import route.performance.core.Connection;
import route.performance.core.ConnectionManager;
import route.performance.core.MessageHandler;
import route.performance.core.RouterRequest;

import com.chong.common.util.JacksonUtil;

import content.StringTools;

/**
 * 数据消息处理类
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataReceiveHandler implements MessageHandler
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static int receiveCount = 0;
	private static Object receiveLock = new Object();
	
	private static int sendCount = 0;
	private static Object sendLock = new Object();
	
	
	/**
	 * @param mesg
	 * @param params
	 */
	@Override
	public void hand(ByteBuffer mesg, Map<String, Object> params)
	{
		//读取源地址
		byte[] addrLen = new byte[2];
		mesg.get(addrLen);
		
		int len = ((addrLen[0] & 0xff) << 8) | (addrLen[1] & 0xff);
		if(len<=0)
		{
			logger.error("解析数据请求错误：不正确的源地址长度len="+len);
			return;
		}
		byte[] addr = new byte[len];
		mesg.get(addr);
		String sourceId = new String(addr);
		
		int bodyLen = mesg.remaining();
		byte[] body = new byte[bodyLen];
		mesg.get(body);
		String message = null;
		try
		{
			message = new String(body,RouterConstant.DEFAULT_CHARSET);
		} 
		catch (UnsupportedEncodingException e)
		{
			logger.error("解析数据请求错误：不能通过"+RouterConstant.DEFAULT_CHARSET+"取解码消息字符串 body="+StringTools.getBytesString(body));
			return;
		}
		Connection conn = (Connection)params.get(PARAM_CONNECTION);
		//创建一个RouterRequest提交线程池执行
		final RouterRequest request = new RouterRequest(sourceId,message);
		synchronized(receiveLock)
		{
			receiveCount++;
		}
		logger.debug("收到数据报文 "+receiveCount+" 条");
		Map<String, Object> map = JacksonUtil.jsonToMap(message);
		String msgType = (String)map.get("msgType");
		if("MSG_GET_ROUTER_STATUS_REQ".equals(msgType))
		{
			String megSeq = (String)map.get("msgSeq");
			String resp = "{\"version\":16,\"msgType\":\"MSG_GET_ROUTER_STATUS_RSP\",\"msgSeq\":\""+megSeq+"\""
					+ ",\"errorCode\":0,\"description\":\"success\",\"devStatus\":0,\"runStatus\":0"
					+ ",\"broadbandRate\":\"10%\",\"downbandwidth\":5,\"wifiStatus\":0,\"subDevList\":[]}";
			
			ConnectionManager.sendDataReq(conn, resp);
			synchronized(sendLock)
			{
				sendCount++;
			}
			logger.debug("发送数据报文 "+sendCount+" 条");
		}
	}

    
}
