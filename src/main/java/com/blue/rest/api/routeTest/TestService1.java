package com.blue.rest.api.routeTest;

/**
 * @Auther: wangcs
 * @Date: 2019/6/26 09:57
 * @Description:
 */

@TaskHandler(taskType = TestEnum.Type1)
public class TestService1 extends TestMutilService{
    @Override
    public String doSomething() {
        return TestEnum.Type1.name();
    }
}