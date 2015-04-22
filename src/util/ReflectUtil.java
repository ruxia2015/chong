package util;

/**
 * 反射工具
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xuruxia
 * @version  [版本号, 2015年4月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReflectUtil
{
    /**
     * 反射class
     */
    public static Class reflectClass(String className){
        try{
            Class clazz= Class.forName(className);
            return clazz;
        }catch(Exception e){
            
        }
        return null;
        
    }
    
    
    
}
