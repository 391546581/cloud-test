package com.blue.rest.compelete.future;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:22
 * @Description:
 */

public class Logger {

    private static final Logger INSTANCE = new Logger();

    private long started;

    private Logger() {
        started = System.nanoTime();
    }

    static Logger getInstance() {
        return INSTANCE;
    }

    Logger start() {
        started = System.nanoTime();
        return this;
    }

    void log(String s, Object... args) {
        if (args==null)
            args = new Object[0];

        final String formatS = "[%4dms] " + s + "%n"; //+ "\t<<<%s>>>%n";
        final int argLength = args.length + 2;


        final Object[] args2 = new Object[argLength];
        args2[0] = (System.nanoTime()-started)/1_000_000;
        System.arraycopy(args, 0, args2, 1, args.length);
        args2[argLength-1] = Thread.currentThread().getName();

        System.out.format(formatS, args2);
    }
}
