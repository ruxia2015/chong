/*
 * 文 件 名:  MessageHandler.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.core;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * 消息处理接口
 * 处理从OneNet端发送过来的消息
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MessageHandler
{
	String PARAM_CONNECTION = "PARAM_CONNECTION";
	
	/**
	 * 根据消息类型，解析消息并完成相关业务
	 * <功能详细描述>
	 * @param mesg 消息体
	 * @param params 额外的参数
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	void hand(ByteBuffer mesg, Map<String,Object> params);
	
}
