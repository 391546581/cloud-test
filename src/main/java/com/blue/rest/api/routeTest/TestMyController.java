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

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: wangcs
 * @Date: 2019/6/26 09:55
 * @Description:
 */

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestMyController {

    @Autowired
    com.blue.rest.feign.TestApi TestApi;

    /**
     * http://127.0.0.1/api/test/payConfirm?req=Type2
     *
     */
    @RequestMapping(value="/payConfirm", method= RequestMethod.POST)
    @ApiOperation("payConfirm" )
    public String payConfirm(HttpServletRequest request,@RequestParam(value = "req",required = false) String req) {
        System.out.println(1);
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
