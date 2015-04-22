package annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnontationUtil
{
    
    public static Annotation getFieldAnnotation(Field f, Class cls)
    {        
        Annotation annotation = f.getAnnotation(cls);
        return annotation;        
    }
    
    
}
