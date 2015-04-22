/*
 * 文 件 名:  HeartbeatRespHandler.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月23日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.handler;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.log4j.Logger;

import route.performance.core.Connection;
import route.performance.core.MessageHandler;


/**
 * 心跳响应消息处理类
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HeartbeatRespHandler implements MessageHandler
{
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 解析心跳响应
	 * 心跳响应无消息体
	 * @param mesg
	 * @param params
	 */
	@Override
	public void hand(ByteBuffer mesg, Map<String, Object> params)
	{
		Connection conn = (Connection)params.get(PARAM_CONNECTION);
//		logger.debug(conn.getKey()+"收到心跳响...");
		if(conn.isDisConnected())
		{
			conn.stateDisconnToConn();
		}
		conn.updateHeartTime();
	}

}
