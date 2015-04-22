/*
 * 文 件 名:  ReadEOFException.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年5月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.exception;

/**
 * 读取文件尾异常
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年5月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReadEOFException extends RuntimeException
{
	private static final long serialVersionUID = 6592462441035387239L;

	
	public ReadEOFException(String message)
	{
		super(message);
	}

	
	public ReadEOFException(Throwable cause)
	{
		super(cause);
	}

	
	public ReadEOFException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
