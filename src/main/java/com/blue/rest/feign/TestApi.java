package com.blue.rest.feign;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: wangcs
 * @Date: 2019/6/26 09:44
 * @Description:
 */
@FeignClient("ZIPKIN-SERVICE")
//@RequestMapping("/api/payment")
public interface TestApi {

    @ApiOperation("payTest" )
    @RequestMapping(value = "/api/test/payTest",method = RequestMethod.POST)
    String payTest(@ApiParam("支付请求参数") @RequestParam("req") String payReq);
}
