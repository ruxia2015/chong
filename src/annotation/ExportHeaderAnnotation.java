package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExportHeaderAnnotation
{
    
    
    String headerName() default "";
    
    //是否是状态字段
    boolean needConvert() default false;
    
    /**
     * 转换的value值，用于如状态的字段,和convertKeys配套使用，只有needConvert为true时才生效
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String[] [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    String[] convertKeys() default { "0", "1" };
    
    /** 转换的value值，用于如状态的字段,和convertKeys配套使用
     * 
     */
    String[] convertValues() default { "否", "是" };
    
    /**
     * 是否忽略
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    boolean isIngore() default false;
    
}
