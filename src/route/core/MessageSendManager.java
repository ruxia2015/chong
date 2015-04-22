/*
 * 文 件 名:  MessageSendManager.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月24日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.core;

import org.apache.log4j.Logger;

import route.exception.IlegalParamException;
import content.MessageUtil;
import content.StringTools;

/**
 * 向OneNet推送消息的管理类
 * 
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MessageSendManager
{
	private static Logger logger = Logger.getLogger(MessageSendManager.class);
		
	/**
	 * 发送消息
	 * 
	 * @param destId 目的设备id
	 * @param message json字符串消息
	 * @return [参数说明]
	 * 
	 * @return boolean [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean sendMessage(String destId, String message,Connection conn)
	{
//		if(StringTools.isEmptyOrNull(destId))
//		{
//			logger.error("推送消息出错：目的设备id不能为空");
//			throw new IlegalParamException("推送消息出错：参数destId为空");
//		}
		if(StringTools.isEmptyOrNull(message))
		{
			logger.error("推送消息出错：消息不能为空");
			throw new IlegalParamException("推送消息出错：参数message为空");
		}
		message = MessageUtil.updateMessage(message);
		if(!conn.isConnected())
		{
			logger.debug("客户端["+conn.getKey()+"]还未连接上。");
			return false;
		}
		ResponseMessage rm = new ResponseMessage(destId, message);
		try
		{
			byte[] sendData = IOParser.generateDataReq(rm.getDestId(), rm.getMessage());	
			conn.sendMessage(sendData);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	
}
