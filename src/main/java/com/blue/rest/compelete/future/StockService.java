package com.blue.rest.compelete.future;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.blue.rest.compelete.future.Util.*;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:22
 * @Description:
 */

public class StockService {
    private Map<String, Integer> stocks = new HashMap<>();

    public int query(String pid) {
        delay(100, 100);
        int q2 = (q-q/4-1) + r.nextInt(q);
        generateStock(q2);
        return q2;
    }

    public Stock pick(String repo, String prd) {
        final Stock stock = new Stock(repo, stocks.get(repo));
        delay(500, 2000);
        return stock;
    }

    private void generateStock(int q) {
        System.out.println("random q:"+q);
        final Iterator<String> iter = repos.iterator();
        if (repos.size() == 1) {
            stocks = ImmutableMap.of(iter.next(), q);
        } else {
            stocks = ImmutableMap.of(
                    iter.next(), q / 5,
                    iter.next(), q / 4,
                    iter.next(), q / 3,
                    iter.next(), (q - q / 5 - q / 4 - q / 3)
            );
        }
        logger.log("stocks: [total=%d, repos=%s]", q, stocks);
    }
}

