//package com.blue.rest.function;
//
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Supplier;
//
//@Slf4j
//@Service
//public class PaymentRedisService {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private static final String LOCK = "payment-lock_";
//    private static final String KEY = "payment-key_";
//
//    public void supply(String key,String value,Supplier<Boolean> supply) {
//        boolean lock = false;
//        final String k = KEY + key;
//        try {
//            lock = redisTemplate.opsForValue().setIfAbsent(k, LOCK+value);
//            redisTemplate.expire(k,180, TimeUnit.SECONDS);
//            log.info("lock payment key: {},  result :{}" ,k, lock);
//            if (lock) {
//                supply.get();
//            } else {
//                return;
//            }
//        }catch (Throwable e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (lock) {
//                redisTemplate.delete(k);
//                log.info("release payment key:{}",k);
//            } else {
//                log.info("lock not found");
//            }
//        }
//    }
//
//    public void supply(String key,Supplier<String> supply) {
//        boolean lock = false;
//        final String k = KEY + key;
//        try {
//            lock = redisTemplate.opsForValue().setIfAbsent(k, null);
//            redisTemplate.expire(k,180, TimeUnit.SECONDS);
//            log.info("lock payment key: {},  result :{}" ,k, lock);
//            if (lock) {
//                supply.get();
//            } else {
//                return;
//            }
//        }catch (Throwable e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (lock) {
//                redisTemplate.delete(k);
//                log.info("release payment key:{}",k);
//            } else {
//                log.info("lock not found");
//            }
//        }
//    }
//
//    //多节点控制一个节点执行
//    @Scheduled(fixedDelay = 2 * 60 * 1000)
//    public void chargeOrderDBTask() {
//        supply("chargeOrderDBTask","chargeOrderDBTask", ()->{
//            List<Integer> c = Lists.newArrayList(1,2,3);
//            for (Integer e : c) {
//                //do....
//                System.out.println("do something...");
//            }
//            return true;
//        });
//    }
//
//    //多节点同时执行,每个节点执行不同任务
//    @Scheduled(fixedDelay = 2 * 60 * 1000)
//    public void chargeOrderDBTask2() {
//        List<Integer> c = Lists.newArrayList(1,2,3);
//        for (Integer e : c) {
//            supply("_Handle_"+e, ()->{
//                System.out.println("do something...");
//                return "Success";
//            });
//        }
//    }
//
//    static Map map = new HashMap();
//    public static void checkSupply(String key,Supplier<String> supply) {
//        boolean lock = false;
//        final String k = KEY + key;
//        try {
//            lock = map.containsKey(key);
//            if(!lock){
//                lock=true;
//                map.put(key,true);
//
//                log.info("lock key: {}" ,k);
//                supply.get();
//            }else{
//                log.info("lock fail ,key: {}" ,k);
//            }
//
//        }catch (Throwable e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (lock) {
//                map.remove(k);
//                log.info("release key:{}",k);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        List<Integer> c = Lists.newArrayList(1,3,5,2,4,2,1,2,3,2,4,2,2,3);
//        for (Integer e : c) {
//            new Thread(()->{
//                checkSupply("_Handle_"+e, ()->{
//                    System.out.println("do something...");
//                    return "Success";
//                });
//            }).start();
//
//        }
//    }
//
//}
