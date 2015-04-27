package com.chong.common.util;

public class StringTools
{
    
    public static boolean isEmptyOrNone(String str){
        if(str==null || str.trim().equals("")){
            return true;
        }
        return false;
    }
}
