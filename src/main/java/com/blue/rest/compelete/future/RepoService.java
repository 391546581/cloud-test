package com.blue.rest.compelete.future;

import static com.blue.rest.compelete.future.Util.*;
/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:22
 * @Description:
 */

public class RepoService {
    public Availability isAvailable(String repo) {
        delay(0, 100);
        final Availability availability = new Availability(repo, r.nextInt(5) != 0);
        logger.log("repo %s %s available", repo, availability.available ? "is" : "NOT");
        return availability;
    }

    public static class Availability {
        String repo;
        boolean available;

        public Availability(String repo, boolean available) {
            this.repo = repo;
            this.available = available;
        }
    }
}
