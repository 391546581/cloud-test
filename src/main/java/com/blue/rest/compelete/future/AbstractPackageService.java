package com.blue.rest.compelete.future;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static com.blue.rest.compelete.future.Util.*;
import static java.util.concurrent.CompletableFuture.*;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:20
 * @Description:
 */

public abstract class AbstractPackageService implements PackageService{
    protected StockService stockService = new StockService();
    protected RepoService repoService = new RepoService();
    protected EventService eventService = new EventService();

    /* stateful variables. */
    protected AtomicInteger queriedQ = new AtomicInteger(0);
    protected CompletableFuture<Boolean> allocated = new CompletableFuture<>();

    @Override
    public boolean pack(String oid, String pid) {
        /* set global repos since it's used by single repo + multi repo demos. */
        repos.clear();
        repos.addAll(getRepos());
        logger.log("initialized repos: %s", repos);

        /* set started time. */
        logger.start();

        /* a listener to monitor if this order's cancellation event was emitted. */
        final CompletableFuture<Boolean> cancelListener =
                runAsync(() -> eventService.listenOrderCancel(oid))
                        .thenApply(reason -> false);

        /* a control to indicate if package was completed. */
        final CompletableFuture<Boolean> terminator =
                allocated.applyToEitherAsync(cancelListener, Function.identity());

        logger.log("pre-checking stock quickly...");
        supplyAsync(() -> stockService.query(pid))
                .whenComplete((stock, e) -> {
                    if (e != null) {
                        e.printStackTrace();
                        allocated.complete(false);
                    } else if (stock < q) {
                        logger.log("not enough stock (%d < %d)", stock, q);
                        allocated.complete(false);
                    } else {
                        logger.log("total stock is enough (%d >= %d). allocating from repos...", stock, q);
                        startPick(pid);
                    }
                });

        try {
            // wait until package was completed.
            return terminator.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** repos used to initialize global repos and generate stocks. */
    protected abstract Collection<String> getRepos();

    /** the entry to kick off pick process. */
    protected abstract void startPick(String pid);

    /** a process to pick up stock from one repo. */
    protected CompletableFuture<Void> startPickFromOneRepo(String repo, String pid) {
        return supplyAsync(() -> repoService.isAvailable(repo))
                .thenCompose(this::pick)
                .thenAccept(this::allocateStock)
                .thenRun(this::isAllocatedFully)
                ;
    }

    /** pick up stock based on repo's availability. */
    protected CompletableFuture<Stock> pick(RepoService.Availability a) {
        if (!a.available) {
            CompletableFuture<Stock> dummy = new CompletableFuture<Stock>();
            dummy.complete(new Stock(a.repo, 0));
            return dummy;
        } else {
            return supplyAsync(() -> stockService.pick(a.repo, pid));
        }
    }

    /** allocate stock. */
    protected void allocateStock(Stock stock) {
        queriedQ.addAndGet(stock.count);
        logger.log("repo %s was allocated %d", stock.repo, stock.count);
    }

    /** check if all stocks are allocated enough. If yes, stop process. */
    protected boolean isAllocatedFully() {
        final int i = queriedQ.get();
        if (i >= q) {
            logger.log("%d >= %d", i, q);
            allocated.complete(true);
        }
        return i >= q;
    }

    /** complete allocation process. */
    protected void completeAllocate(Void v, Throwable e) {
        if (e != null) {
            e.printStackTrace();
        }else if (!allocated.isDone()) {
            allocated.complete(false);
            logger.log("didn't get enough stock.");
        }
    }



}
