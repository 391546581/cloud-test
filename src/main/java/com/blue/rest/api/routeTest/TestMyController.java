package com.blue.rest.api.routeTest;

import com.alibaba.fastjson.JSONObject;
import com.blue.rest.feign.TestApi;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangcs
 * @Date: 2019/6/26 09:55
 * @Description:
 */

@RestController
@RequestMapping("/api/order/test")
@Slf4j
public class TestMyController {

    @Autowired
    com.blue.rest.feign.TestApi TestApi;

    /**
     * 支付确认
     */
    @RequestMapping(value="/payConfirm", method= RequestMethod.POST)
    @ApiOperation("payConfirm" )
    public String payConfirm(String req) {
        String resultObject = TestApi.payTest(req);
        log.info(JSONObject.toJSONString(resultObject));
        return resultObject;
    }

    @RequestMapping(value="/payTest", method= RequestMethod.POST)
    @ApiOperation("payTest" )
    public String test2(@RequestParam("req") String type) {
        TestEnum testEnum = TestEnum.valueOf(type);
        if (TaskHandlerRegister.getTaskHandler(testEnum.name()) == null) {
            throw new RuntimeException("can't find taskHandler,taskType=" + testEnum.name());
        }
        TestMutilService taskHandler = TaskHandlerRegister.getTaskHandler(testEnum.name());
        return taskHandler.doSomething();
    }

}
