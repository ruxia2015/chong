package base;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil
{
    public static String parseMthod(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actionPath = uri.replace(contextPath + "/", "");
        
        if (!actionPath.trim().equals(""))
        {
            String[] paths = actionPath.split("/");
            int arrLen = paths.length;
            if (arrLen >= 3 && paths[arrLen - 2].equals("m"))
            {
                String methodNAme = paths[arrLen - 1];
                if(methodNAme.contains(".")){
                    methodNAme = methodNAme.substring(0,methodNAme.indexOf("."));
                }
                
                return methodNAme;
            }
        }
        
        return "";
        
    }
}
