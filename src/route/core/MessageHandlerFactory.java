/*
 * 文 件 名:  MessageHandlerFactory.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月22日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.core;

import route.RouterConstant;
import route.handler.ConnectRespHandler;
import route.handler.DataReceiveHandler;
import route.handler.HeartbeatRespHandler;

/**
 * 消息处理Handler的工厂类
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MessageHandlerFactory
{
	
	private MessageHandlerFactory()
	{
		
	}
	
	
	
	/**
	 * 根据消息类型，生产不同的消息处理类
	 * <功能详细描述>
	 * @param mesgType
	 * @return [参数说明]
	 * 
	 * @return MessageHandler [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static MessageHandler newInstance(byte mesgType)
	{
		MessageHandler result = null;
		if(RouterConstant.MESGTYPE_CONN_RESP==mesgType)
		{
			result = new ConnectRespHandler();
		}
		else if(RouterConstant.MESGTYPE_DATA==mesgType)
		{
			result = new DataReceiveHandler();	
		}
		else if(RouterConstant.MESGTYPE_HEART_RESP==mesgType)
		{
			result = new HeartbeatRespHandler();
		}
		return result;
	}

}
