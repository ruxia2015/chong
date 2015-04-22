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
package route.handler;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import route.RouterConstant;
import route.core.Connection;
import route.core.MessageHandler;
import route.core.RouterRequest;
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
	
	//包装消息体
	public static BlockingQueue<String> queues = new LinkedBlockingQueue<String>();
	
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
		logger.debug(conn.getKey()+"收到数据报文："+request);
		queues.add(request.toString());
	}

    public static BlockingQueue<String> getQueues()
    {
        return queues;
    }

    public static void setQueues(BlockingQueue<String> queues)
    {
        DataReceiveHandler.queues = queues;
    }
}
