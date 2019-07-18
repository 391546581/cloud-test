package com.blue.rest.compelete.future;

import static com.blue.rest.compelete.future.Util.*;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:21
 * @Description:
 */

public class EventService {

    public void listenOrderCancel(String order) {
        delay(2400, 300);
        logger.log("cancelled with no reason");
    }
}
