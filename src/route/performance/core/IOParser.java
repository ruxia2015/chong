/*
 * 文 件 名:  IOParser.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  pf_wangxiaowei
 * 修改时间:  2014年4月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package route.performance.core;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import route.RouterConstant;
import route.exception.DataException;
import content.StringTools;

/**
 * 对输入输出数据的加工类
 * <功能详细描述>
 * 
 * @author  pf_wangxiaowei
 * @version  [版本号, 2014年4月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IOParser
{
	private static Logger logger = Logger.getLogger(IOParser.class);
	
	/**
	 * 生成连接请求的报文数据
	 * <功能详细描述>
	 * @param userId 用户id
	 * @param authInfo 鉴权信息
	 * @return [参数说明]
	 * 
	 * @return byte[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] generateConnReq(String userId,String authInfo)
	{
		if(StringTools.isEmptyOrNull(authInfo))
		{
			logger.error("生成请求头失败，apiKey="+authInfo);
			return null;
		}
		if(StringTools.isEmptyOrNull(userId))
		{
			logger.error("生成请求头失败，userId为空，userId="+userId);
			return null;
		}
		//消息类型
		byte mesgType = RouterConstant.MESGTYPE_CONN_REQ;
		//选项1：协议描述
		byte[] option1 = {0x00,0x03,'E','D','P'};
		//选项2：协议版本
		byte option2 = 0x01;
		//选项3：连接标志 (采用用户id+鉴权信息)
		byte option3 = (byte)0xc0;
		//选项4：连接时间 (-1的二进制表示为11111111,用无符号表示时是最大)
		byte[] option4 = {(byte)0xff,(byte)0xff};
		//消息体1 设备id为空
		byte[] body1 = {0x00,0x00};
		//消息体2 用户id
		byte[] uids = userId.getBytes();
		byte[] body2Len = reverse(intToByteArrray(uids.length, 2));
		//消息体3 鉴权信息
		byte[] auths = null;
		try
		{
			auths = authInfo.getBytes(RouterConstant.DEFAULT_CHARSET);
		} 
		catch (UnsupportedEncodingException e)
		{
			logger.equals("生成连接请求失败:apiKey不能被"+RouterConstant.DEFAULT_CHARSET+"编码。apiKey="+authInfo);
			throw new DataException("生成连接请求失败:apiKey不能被"+RouterConstant.DEFAULT_CHARSET+"编码。apiKey="+authInfo);
		}
		byte[] body3Len = reverse(intToByteArrray(auths.length, 2));
		
		int totalLen = option1.length+1+1+option4.length+
				body1.length+(uids.length+body2Len.length)+(auths.length+body3Len.length);
		//转换出消息长度的byte数组形式
		byte[] mesgLen = intToOneNetBytes(totalLen);
		
		byte[] res = new byte[totalLen+1+mesgLen.length];
		ByteBuffer result = ByteBuffer.wrap(res);
		result.put(mesgType);
		result.put(mesgLen);
		result.put(option1);
		result.put(option2);
		result.put(option3);
		result.put(option4);
		result.put(body1);
		result.put(body2Len);
		result.put(uids);
		result.put(body3Len);
		result.put(auths);
		
		result.flip();
		
		return res;
	}
	
	/**
	 * 生成心跳请求报文
	 * <功能详细描述>
	 * @return [参数说明]
	 * 
	 * @return byte[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] generateHeartReq()
	{
		byte mesgType = RouterConstant.MESGTYPE_HEART_REQ;
		byte len = 0x00;
		byte[] result = {mesgType,len};
		return result;
	}
	
	/**
	 * 生成发送数据请求
	 * <功能详细描述>
	 * @param destId 目的设备的id
	 * @param mesg 发送消息
	 * @return [参数说明]
	 * 
	 * @return byte[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] generateDataReq(String destId, String mesg)
	{
		byte msgType = RouterConstant.MESGTYPE_DATA;
		
		byte[] addr = destId.getBytes();
		byte[] addrLen = reverse(intToByteArrray(addr.length, 2));
		
		byte[] body = null;
		try
		{
			body = mesg.getBytes(RouterConstant.DEFAULT_CHARSET);
		} 
		catch (UnsupportedEncodingException e)
		{
			logger.equals("生成发送数据请求失败:消息不能被"+RouterConstant.DEFAULT_CHARSET+"编码。mesg="+mesg);
			throw new DataException("生成发送数据请求失败:消息不能被"+RouterConstant.DEFAULT_CHARSET+"编码。mesg="+mesg);
		}
		//消息体+选项长度
		int totalLen = addr.length+addrLen.length+body.length;
		byte[] mesgLen = intToOneNetBytes(totalLen);
		
		byte[] res = new byte[totalLen+mesgLen.length+1];
		ByteBuffer buf = ByteBuffer.wrap(res);
		buf.put(msgType);
		buf.put(mesgLen);
		buf.put(addrLen);
		buf.put(addr);
		buf.put(body);
		
		return res;
	}
	
	/**
	 * 计算长度
	 * 
	 * 长度值的低位部分放在传输的前面字节，高位放在后面。
	 * 每个字节的最高位为延续指示位。延续指示位为1时，标示后面字节也是长度值。
	 * 最多可延续4个字节	
	 * @param buf 准备好就读的buffer
	 * @return [参数说明]
	 * 
	 * @return int [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static int calculateLen(ByteBuffer buf)
	{
		int result = 0;
		int size = buf.remaining();
		for(int i=0; i<size; i++)
		{
			byte b = buf.get();
			int temp = (b & 0x7f)<<(7*i);
			result = result|temp;
		}
		return result;
	}
	
	/**
	 * 获取一个整形数字的byte数组，低位在前
	 * 
	 * <功能详细描述>
	 * @param val
	 * @param len
	 * @return [参数说明]
	 * 
	 * @return byte[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] intToByteArrray(int val,int len)
	{
		if(len>4 && len<=0)
		{
			len = 4;
		}
		byte[] res = new byte[len];
		for(int i=0; i<len; i++)
		{
			byte b = (byte) (val>>>(i*8));
			res[i] = b;
		}
		return res;
	}
	
	/**
	 * 将长度转为OneNet需要的长度数组，长度必须小于256M
	 * 
	 * 长度值的低位部分放在传输的前面字节，高位放在后面。
	 * 每个字节的最高位为延续指示位。延续指示位为1时，标示后面字节也是长度值。
	 * 最多可延续4个字节	
	 * 
	 * @param val
	 * @return [参数说明]
	 * 
	 * @return byte[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] intToOneNetBytes(int val)
	{
		int limit = 0x01<<28;
		if(val >= limit)
		{
			logger.error("需要转换的整数["+val+"]必须小于"+limit);
			throw new DataException("数据太大，不能转换，长度["+val+"B]必须小于"+limit+"B");
		}
		ByteBuffer buf = ByteBuffer.allocate(4);
		//作为一个比较的值，当val>=max时，表示还需一个byte作为高位来表示val.
		int max = 1;
		for(int i=0; i<4; i++)
		{
			//获取低7位字节
			int temp = val>>>(i*7);
			byte b = (byte)(temp & 0x7f);

			max = max<<7;
			/*
			 * val已经用byte数组表示完
			 */
			if(val<max)
			{
				//将b的高位设置为 0
				b = (byte)(b & 0x7f);
				buf.put(b);
				break;
			}
			/*
			 * 继续需要一个byte做高位来表示val
			 */
			//将b的高位设为1
			b = (byte)(b | 0x80);
			buf.put(b);
		}
		buf.flip();
		byte[] res = new byte[buf.remaining()];
		buf.get(res);
		return res;
	}
	
	/**
	 * 将数组反转
	 * <功能详细描述>
	 * @param source
	 * @return [参数说明]
	 * 
	 * @return byte[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static byte[] reverse(byte[] source)
	{
		if(source==null)
		{
			return null;
		}
		byte[] result = new byte[source.length];
		for(int i=0; i<source.length; i++)
		{
			result[i] = source[source.length-i-1];
		}
		return result;
	}
	
	public static void main(String[] args)
	{
//		ByteBuffer buf = ByteBuffer.allocate(4);
//		buf.put((byte)(0xc1-256));
//		buf.put((byte)0x02);
//		buf.flip();
//		System.out.println(calculateLen(buf));
//		byte b = 'P';
//		
//		System.out.println(Integer.toBinaryString(-32767));
//		int re = 1;
//		for(int i=0; i<16; i++)
//		{
//			re = re*2;
//		}
//		System.out.println(Integer.toBinaryString((short)(0xff-256)));
		
//		int a = 0xff7f7fff;
//		byte[] bs = reverse(intToByteArrray(a,4));
//		for(byte b : bs)
//		{
//			System.out.println(b);
//		}
//		int max = 0x0dd9c1ed;		
//		byte[] result = intToOneNetBytes(max);
//		for(byte b : result)
//		{
//			System.out.println(Integer.toHexString(b));
//		}
//		
//		ByteBuffer buf = ByteBuffer.wrap(result);
//		int len = calculateLen(buf);
//		System.out.println(Integer.toHexString(len));
//		System.out.println(Integer.toBinaryString(len));
//		
//		System.out.println((1<<28)/1024/1024);
		
		byte[] reqArr = generateConnReq("101", "{\"a\":1}");
		for(byte b : reqArr)
		{
			System.out.println(Integer.toBinaryString(b));
		}
	}
}
