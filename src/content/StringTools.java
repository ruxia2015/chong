package content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Pattern;

public class StringTools
{
    
    /**
     * 字符非空验证
     * 
     * @param str
     * @return
     */
    public static boolean isEmptyOrNull(String str)
    {
        if (null == str || "".equals(str.trim()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 返回字节数组的字符串形式
     * 例如：[12,23,127,-44]
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getBytesString(byte[] data)
    {
    	if(data==null)
    	{
    		return null;
    	}
    	if(data.length==0)
    	{
    		return "[]";
    	}
    	StringBuilder result = new StringBuilder("[");
    	for(int i=0; i<data.length; i++)
    	{
    		byte b = data[i];
    		if(i==0)
    		{
    			result.append(b);
    			continue;
    		}
    		result.append(","+b);
    	}    	
    	result.append("]");
    	return result.toString();
    }
    
    /**
     * 返回字节数组的16进制字符串形式
     * 例如：[0x12,0xff]
     * @param data
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String bytesToHexString(byte[] data)
    {
    	if(data==null)
    	{
    		return null;
    	}
    	if(data.length==0)
    	{
    		return "[]";
    	}
    	StringBuilder result = new StringBuilder("[");
    	for(int i=0; i<data.length; i++)
    	{
    		byte b = data[i];
    		if(i==0)
    		{
    			result.append(byteToHexString(b));
    			continue;
    		}
    		result.append(","+byteToHexString(b));
    	}    	
    	result.append("]");
    	return result.toString();
    }
    
    /**
     * 返回字节的16进制字符串形式
     * 例如：0x12
     * @param b
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String byteToHexString(byte b)
    {
    	int i = b & 0xff;
    	int high = i>>>4;
    	int lower = i & 0x0f;
    	
    	return "0x"+getChar(high)+getChar(lower);
    	
    }
    
    private static String getChar(int b)
    {
    	if(b<10)
    	{
    		return b+"";
    	}
    	if(b==10)
    	{
    		return "A";
    	}
    	if(b==11)
    	{
    		return "B";
    	}
    	if(b==12)
    	{
    		return "C";
    	}
    	if(b==13)
    	{
    		return "D";
    	}
    	if(b==14)
    	{
    		return "E";
    	}
    	if(b==15)
    	{
    		return "F";
    	}
    	return null;
    }
    
    /**
     * 判断对象是否为空
     * <功能详细描述>
     * @param obj
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmptyOrNull(Object obj)
    {
        if (null == obj)
        {
            return true;
        }
        if (obj instanceof String)
        {
            return isEmptyOrNull((String) obj);
        }
        return false;
    }
    
    /**
     * 是否是数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Boolean boo = false;
        if (null != str && !"".equals(str))
        {
            boo = pattern.matcher(str).matches();
        }
        return boo;
    }
    
     /**
     * 生成与原值不同的随机验证码(纯数字)
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String BuildRandomNumber(int number, String oldVerifyCode)
    {
    	if(number <= 0)
    	{
    		return "";    		
    	}
    
        String newVerifyCody = "";
        Random random = new Random();
        for (int i = 0; i < number; i++)
        {
            newVerifyCody += String.valueOf(random.nextInt(10));
        }
        
        if(isEmptyOrNull(oldVerifyCode))
        {
        	return newVerifyCody;
        }
        
        while(oldVerifyCode.equals(newVerifyCody))
        {
        	newVerifyCody = "";
            for (int i = 0; i != number; i++)
            {
                newVerifyCody += String.valueOf(random.nextInt(10));
            }
        }
        
        return newVerifyCody;
    }

    /**
     * 获取字符串长度
     * <一句话功能简述>
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static int getLength(String str)
    {
        int length = 0;
        if (StringTools.isEmptyOrNull(str))
        {
            return length;
        }
        str = str.replaceAll("[^\\x00-\\xff]", "**");
        length = str.length();
        return length;
    }
    
    /**
     * 将流转换为字符串
     * <功能详细描述>
     * @param is
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String convertStreamToString(InputStream is)
    {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;
        
        try
        {
            while ((size = is.read(bytes)) > 0)
            {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }
        
    public static void main(String[] args)
    {
//            String t1="2014-03-10 23:59:59";
//            String t2="2014-03-11 00:00:00";
//            Date d1=null;
//            Date d2=null;
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-ddhh:mm:ss");
//            try{
//            d1=sdf.parse(t1);
//            d2=sdf.parse(t2);
//            }
//            catch(Exception pe)
//            {
//            System.out.println(pe.getMessage());
//            }
//
//            long dd1=d1.getTime();
//            System.out.println(dd1);
//            long dd2=d2.getTime();
//            
//            System.out.println("时间差是："+(dd2-dd1)+"（小时）");
    	byte[] src = {16,32};
    	System.out.println(bytesToHexString(src));

    }
        
        
}
