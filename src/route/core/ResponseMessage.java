/*
 * 文 件 名:  ResponseMessage.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月24日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.core;

import java.io.Serializable;

/**
 * 向OneNet推送的消息
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResponseMessage implements Serializable
{
	private static final long serialVersionUID = -1173510537397980881L;

	//目的Id
	private String destId;
	
	//json消息字符串
	private String message;

	
	public ResponseMessage(String destId, String message)
	{
		super();
		this.destId = destId;
		this.message = message;
	}


	/**
	 * @return 返回 destId
	 */
	public String getDestId()
	{
		return destId;
	}


	/**
	 * @param 对destId进行赋值
	 */
	public void setDestId(String destId)
	{
		this.destId = destId;
	}


	/**
	 * @return 返回 message
	 */
	public String getMessage()
	{
		return message;
	}


	/**
	 * @param 对message进行赋值
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}


	/**
	 * @return
	 */
	@Override
	public String toString()
	{
		return "ResponseMessage [destId=" + destId + ", message=" + message
				+ "]";
	}
	
	

}
