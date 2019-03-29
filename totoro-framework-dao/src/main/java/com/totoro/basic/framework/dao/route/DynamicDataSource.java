package com.totoro.basic.framework.dao.route;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 切换数据源
 *
 * @author Maleah
 * @create 2017-09-27 10:32
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDBType();
    }

}

