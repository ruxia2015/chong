/*
 * 文 件 名:  AgentKeyConstant.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route;

/**
 * 静态配置
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class RouterConstant
{
	//长连接的服务器ip
//	public static String SERVER_IP = "10.189.24.66";	
	public static String SERVER_IP = "hly-edp.hedevice.com";	
	//长连接的服务器端口
	public static int PORT = 29876;
	
	
	//默认编码
	public static String DEFAULT_CHARSET = "UTF-8";
	
	
	//接收线程允许接收到的最大连续错误次数
	public static int RECEIVE_MAX_ERR_COUNT = 10;
	
	
	//业务线程池配置：核心线程数
	public static int WORKERPOOL_CORE_SIZE = 10;
	//业务线程池配置：最大线程数
	public static int WORKERPOOL_MAX_SIZE = 50;
	//业务线程池配置：结束空闲线程时间
	public static long WORKERPOOL_KEEP_ALIVE_SECONDS = 3*60;
	//业务线程池配置：等待任务队列大小
	public static int WORKERPOOL_TASK_QUEUE_SIZE = 1000;
	
	
	//心跳时间间隔
	public static long HEARTBEAT_INTERVAL = 1*60*1000;
	//超时时间间隔
	public static long TIMEOUT_INTERVAL = 3*60*1000;
	
	
	//发送接口设置：发送队列最大值
	public static int SEND_OPTION_QUEUE_SIZE = 50000; 
	//发送接口设置：发送等待时间(秒)
	public static int SEND_OPTION_WAIT_SECONDS = 3;
	
	
//	//OneNetAgent在OneNet的设备ID
//	public static String DEVICE_ID = "1001";	
	//OneNetAgent在OneNet的用户ID
	public static String USER_ID = "20000";
	//OneNetAgent在OneNet的申请的鉴权信息
	public static String AUTH_INFO = "{\"SN\":\"86999900000014000000423456123556\",\"MAC\":\"F8-B1-55-D5-77-7B\"}";	
	
	
	//消息类型：连接请求
	public static byte MESGTYPE_CONN_REQ = 0x10;
	//消息类型：连接响应
	public static byte MESGTYPE_CONN_RESP = 0x20;
	//消息类型：心跳请求
	public static byte MESGTYPE_HEART_REQ = (byte)0xc0;
	//消息类型：心跳响应
	public static byte MESGTYPE_HEART_RESP = (byte)0xd0;
	//消息类型：发送数据
	public static byte MESGTYPE_DATA = 0x30;
	
	
	//连接响应码：成功
	public static byte CONN_RESP_SUCCESS = 0x00;
	//连接响应码：验证失败-协议不匹配
	public static byte CONN_RESP_ERROR_PROTOCOL = 0x01;
	//连接响应码：验证失败-设备ID鉴权失败
	public static byte CONN_RESP_ERROR_DEVICE = 0x02;
	//连接响应码：验证失败-服务器失败
	public static byte CONN_RESP_ERROR_SERVER = 0x03;
	//连接响应码：验证失败-用户名密码鉴权失败
	public static byte CONN_RESP_ERROR_USER = 0x04;
	//连接响应码：验证失败-未授权
	public static byte CONN_RESP_UNAUTH = 0x05;
	
	
	
	
}
