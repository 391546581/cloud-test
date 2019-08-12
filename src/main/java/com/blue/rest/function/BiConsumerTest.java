package com.blue.rest.function;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;

@Slf4j
public class BiConsumerTest implements BiConsumer<String,String>{

    private static final String LOCK = "LOCK_";
    private static final String KEY = "KEY_";

    public void accept(String key,String value){
        final String k = KEY + value;
        System.out.println("accept..." + k);
    }


    public void exec(BiConsumer<String,String> consumer) {
        System.out.println("consume...");
        consumer.accept("abc","def");

    }

    public static void main(String[] args) {
        BiConsumerTest t = new BiConsumerTest();
        t.exec((x,y)->{
            x +="1";
            y +="1";
            System.out.println(x);
            System.out.println(y);
        });
    }

}
