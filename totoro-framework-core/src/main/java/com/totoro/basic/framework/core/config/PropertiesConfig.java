package com.totoro.basic.framework.core.config;

import com.totoro.basic.framework.core.props.BaseProperties;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件信息
 *
 * @author Maleah
 * @create 2017-09-26 15:44
 **/
@Configuration
public class PropertiesConfig  implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        BaseProperties.loadData(event.getEnvironment());
    }
}
