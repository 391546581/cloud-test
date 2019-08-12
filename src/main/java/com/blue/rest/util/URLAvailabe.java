package com.blue.rest.util;

import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 14:07
 * @Description:
 */

@Slf4j
public class URLAvailabe {
    private URL url;
    private HttpURLConnection con;
    private int state = -1;
    private static URLAvailabe URLAvailabe;

    public static URLAvailabe build(){
        if(URLAvailabe==null){
            URLAvailabe = new URLAvailabe();
        }
        return URLAvailabe;
    }



    public FutureTask<Boolean> check(final String urlStr) {
        FutureTask<Boolean> task = new FutureTask<>(()-> isConnect(urlStr));
        new Thread(task).start();
        return task;
    }

    /**
     * 功能：检测当前URL是否可连接或是否有效,
     * 描述：最多连接网络 3 次, 如果 3 次都不成功，视为该地址不可用
     */
    public boolean isConnect(final String urlStr) {
        int counts = 0;
        if (urlStr == null || urlStr.length() <= 0) {
            return false;
        }
        while (counts < 3) {
            try {
                url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                state = con.getResponseCode();
                if (state == 200) {
                    return true;
                }
                break;
            }catch (Exception ex) {
                counts++;
                log.error("URL{}不可用，连接第{}次",urlStr,counts);
                continue;
            }
        }
        return false;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Boolean> check = URLAvailabe.build().check("https://www.baidu.com");
        System.out.println(check.get().booleanValue());


//        CompletableFuture.supplyAsync(()->false).thenApply();
    }
}
