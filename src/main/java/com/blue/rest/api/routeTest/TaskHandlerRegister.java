package com.blue.rest.api.routeTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wangcs
 * @Date: 2019/6/26 10:34
 * @Description:
 */

@Component
public class TaskHandlerRegister extends ApplicationObjectSupport {

    private final static Map<String, TestMutilService> TASK_HANDLERS_MAP = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskHandlerRegister.class);

    @Override
    protected void initApplicationContext(ApplicationContext context) throws BeansException {
        super.initApplicationContext(context);
        Map<String, Object> taskBeanMap = context.getBeansWithAnnotation(TaskHandler.class);
        taskBeanMap.keySet().forEach(beanName -> {
            Object bean = taskBeanMap.get(beanName);
            Class clazz = bean.getClass();
            if (bean instanceof TestMutilService && clazz.getAnnotation(TaskHandler.class) != null) {
                TaskHandler taskHandler = (TaskHandler) clazz.getAnnotation(TaskHandler.class);
                TestEnum taskType = taskHandler.taskType();
                if (TASK_HANDLERS_MAP.keySet().contains(taskType)) {
                    throw new RuntimeException("TaskType has Exits. TaskType=" + taskType);
                }
                TASK_HANDLERS_MAP.put(taskHandler.taskType().name(), (TestMutilService) taskBeanMap.get(beanName));
                LOGGER.info("Task Handler Register. taskType={},beanName={}", taskHandler.taskType(), beanName);
            }
        });
    }

    public static TestMutilService getTaskHandler(String taskType) {
        return TASK_HANDLERS_MAP.get(taskType);
    }
}