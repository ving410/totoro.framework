package com.totoro.basic.framework.core;

import com.totoro.basic.framework.core.config.ApplicationEventListener;
import com.totoro.basic.framework.core.config.PropertiesConfig;
import com.totoro.basic.framework.core.config.SpringContextConfig;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ClassPathResource;

/**
 * 系统入口
 *
 * @author Maleah
 * @create 2017-09-26 15:37
 **/
public class TotoroApplication {
    public static void start(Class<?> startClass, String[] args) {
        ResourceBanner rb = new ResourceBanner(new ClassPathResource("banner.txt"));
        new SpringApplicationBuilder().listeners(
                new SpringContextConfig()
                , new ApplicationEventListener()
                , new PropertiesConfig()).sources(startClass).banner(rb).run(args);
    }
}
