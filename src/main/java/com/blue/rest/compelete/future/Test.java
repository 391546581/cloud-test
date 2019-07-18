package com.blue.rest.compelete.future;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.blue.rest.compelete.future.Util.*;
import static com.blue.rest.compelete.future.Util.pid;
import static java.util.concurrent.CompletableFuture.*;
/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:31
 * @Description:
 */
public class Test extends AbstractPackageService{
    @Override
    protected Collection<String> getRepos() {
        repos.addAll(Arrays.asList("1","2","3","4"));
        return repos;
//        Set emptySet = Collections.EMPTY_SET;
//        emptySet.addAll(Arrays.asList("1","2","3","4"));
//        return emptySet;
    }

    @Override
    protected void startPick(String pid) {
        final CompletableFuture[] queries = new CompletableFuture[repos.size()];
        final Iterator<String> iter = repos.iterator();
        for (int i = 0; i < queries.length; i++) {
            queries[i] = this.startPickFromOneRepo(iter.next(), pid);
        }

        allOf(queries).whenComplete(this::completeAllocate);
    }

    public static void main(String[] args) {
        Test a = new Test();
        boolean pack = a.pack(oid, pid);
        logger.log("allocated:"+pack);
    }
}
