package com.totoro.basic.framework.dao.route;

import java.util.ArrayList;
import java.util.List;

/**
 * 切换数据源
 *
 * @author Maleah
 * @create 2017-09-27 10:31
 **/
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSource(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDBType() {
        return ((String) contextHolder.get());
    }

    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
