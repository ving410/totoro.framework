package com.totoro.basic.framework.dao.aspect;

import com.totoro.basic.framework.dao.annotation.TargetDataSource;
import com.totoro.basic.framework.dao.route.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切换数据源
 *
 * @author Maleah
 * @create 2017-09-27 11:36
 **/
@Aspect
@Component
@Slf4j
public class TargetDataSourceAspect {

    @Before(" @annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        String dsId = ds.value();
        if (!DataSourceContextHolder.containsDataSource(dsId)) {
            log.error("数据源[{}]不存在，使用默认数据源 > {}", ds.value(), point.getSignature());
        } else {
            log.debug("Use DataSource : {} > {}", ds.value(), point.getSignature());
            DataSourceContextHolder.setDataSource(ds.value());
        }
    }

    @After(" @annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        log.debug("Revert DataSource : {} > {}", ds.value(), point.getSignature());
        DataSourceContextHolder.clearDataSource();
    }

}
