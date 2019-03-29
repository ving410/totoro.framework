package com.totoro.basic.framework.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 加载的bean
 *
 * @author Maleah
 * @create 2017-11-01 18:17
 **/
@Component
@Slf4j
public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.debug("Bean '" + beanName + "' created : " + bean.toString());
        return bean;
    }
}
