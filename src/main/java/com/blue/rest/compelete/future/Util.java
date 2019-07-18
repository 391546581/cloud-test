package com.blue.rest.compelete.future;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:21
 * @Description:
 */


public interface Util {
    Logger logger = Logger.getInstance();
    int q = 5;
    Set<String> repos = new HashSet<>();
    String oid = "jianshu";
    String pid = "Samsung S10";

    Random r = new SecureRandom(ByteBuffer.allocate(4).putInt(LocalTime.now().getNano()).array());

    static void delay(int base, int random) {
        try {
            Thread.sleep(base + r.nextInt(random));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
