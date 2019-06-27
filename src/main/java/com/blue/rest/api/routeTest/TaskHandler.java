package com.blue.rest.api.routeTest;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TaskHandler {
    TestEnum taskType() default TestEnum.Type1;
}
