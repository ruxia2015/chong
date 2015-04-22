package annotation;


/**
 * sql语句注解
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xuruxia
 * @version  [版本号, 2015年4月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public @interface SQLAnnotation
{

    /**
     * 更新操作时，是否为查询条件
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean update_is_where() default true;
    
    
    /**
     * 作为查询条件的时候，其运算符，默认为等号
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String where_oper() default "=";
    
    
    /**
     * 作为查询条件的时候，对应的字段
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String where_column();
    
    /**
     * 是否为表中的字段，默认为是
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean is_column() default true;
    
    
    
}
