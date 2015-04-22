package content;

import java.text.ParseException;
import java.util.Date;

public class MessageUtil
{
    
    public static String updateMessage(String message){
        if(!StringTools.isEmptyOrNull(message)){
            message = message.replace("$date$", DateUtil.getDate(new Date(),"yyyyMMdd"));
            message = message.replace("$time$", DateUtil.getDate(new Date(),"yyyyMMddHHmmss"));
        }
        
        return message;
    }
}
