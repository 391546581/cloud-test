//package com.blue.rest.feign;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Auther: wangcs
// * @Date: 2019/6/26 09:45
// * @Description:
// */
//
//@Slf4j
//@RestController
//@RequestMapping("/api/payment")
//public class TestController implements TestApi {
//
//    @RequestMapping(value = "/payTest",method = RequestMethod.POST)
//    public Object payTest(@RequestParam("req")  String payReq) {
//        log.info("接收支付订单：{}", JSONObject.toJSONString(payReq));
//        return "接收支付订单";
//    }
//
//}
