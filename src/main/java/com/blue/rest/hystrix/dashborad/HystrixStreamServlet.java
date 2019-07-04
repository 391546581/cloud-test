package com.blue.rest.hystrix.dashborad;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangcs
 * @Date: 2019/7/4 13:48
 * @Description:
 */

//#spring boot2.0以后，不提供 hystrix.stream节点，
// 需要自己添加【可以不加@Service放在启动类，也可以加上@Service或者@Component放在一个单独的文件中，只要能注入spring中为Bean即可】

@Service
public class HystrixStreamServlet {
    /**
     * SpringBoot2.0以后，不提供 hystrix.stream节点，需要自己增加
     *
     * http://localhost:9411/hystrix
     * http://localhost:9411/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A9411%2Fhystrix.stream
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
