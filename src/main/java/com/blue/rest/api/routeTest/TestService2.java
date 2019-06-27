package com.blue.rest.api.routeTest;

/**
 * @Auther: wangcs
 * @Date: 2019/6/26 09:57
 * @Description:
 */
@TaskHandler(taskType = TestEnum.Type2)
public class TestService2 extends TestMutilService{
    @Override
    public String doSomething() {
        return "2";
    }
}
