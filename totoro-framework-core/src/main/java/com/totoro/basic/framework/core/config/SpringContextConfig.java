package com.totoro.basic.framework.core.config;

import com.totoro.basic.framework.core.context.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * Spring上下文信息
 *
 * @author Maleah
 * @create 2017-09-26 15:43
 **/
@Slf4j
public class SpringContextConfig implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        SpringApplicationContext context = new SpringApplicationContext();
        context.setApplicationContext(event.getApplicationContext());
        log.info("SpringContext load success!");
    }
}
