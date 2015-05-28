package com.chong.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageRankUtil
{
    static final public String[] GOOGLE_PR_DATACENTER_IPS = new String[] {"toolbarqueries.google.com" ,
         //   "64.233.161.100", "64.233.161.101", "64.233.177.17",
           // "64.233.183.91", "64.233.185.19", "64.233.189.44", "66.102.1.103",
          //  "66.102.9.115", "66.249.81.101", "66.249.89.83", "66.249.91.99",
           // "66.249.93.190", "72.14.203.107", "72.14.205.113", "72.14.255.107",
            };
    
    public static int getPR(String domain) { 
        String result = ""; 
        JenkinsHash jenkinsHash = new JenkinsHash();
        long hash = jenkinsHash.hash(("info:" + domain).getBytes()); 
        //Append a 6 in front of the hashing value.
        String url = "http://toolbarqueries.google.com/tbr?client=navclient-auto&hl=en&"
           + "ch=6" + hash + "&ie=UTF-8&oe=UTF-8&features=Rank&q=info:" + domain;

        System.out.println("Sending request to : " + url);

        try {
            URLConnection conn = new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));

            String input;
            while ((input = br.readLine()) != null) { 
                // What Google returned? Example : Rank_1:1:9, PR = 9
                System.out.println(input); 
                result = input.substring(input.lastIndexOf(":") + 1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if ("".equals(result)) {
            return 0;
        } else {
            return Integer.valueOf(result);
        }

      }
    
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        PageRankUtil prService = new PageRankUtil();
        String domain = "http://www.baidu.com";
        if (args.length > 0)
        {
            domain = args[0];
        }
        System.out.println("Checking " + domain);
        System.out.println("Google PageRank: " + prService.getPR(domain));
        System.out.println("Took: " + (System.currentTimeMillis() - start)
                + "ms");
    }
}
