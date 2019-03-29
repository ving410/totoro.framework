package com.totoro.basic.framework.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.totoro.basic.framework.core.consts.SysErrorConsts;
import com.totoro.basic.framework.core.exception.SysException;
import com.totoro.basic.framework.core.props.BaseProperties;
import com.totoro.basic.framework.dao.route.DataSourceContextHolder;
import com.totoro.basic.framework.dao.route.DynamicDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据源配置，支持多数据源
 *
 * @author Maleah
 * @create 2017-09-27 11:43
 **/
@Configuration
public class DataSourceConfig implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    private Map<Object, Object> targetDataSources = new LinkedHashMap<>();
    private DataSource defaultTargetDataSource;

    @Bean
    public DataSource dynamicDataSource() {
        DynamicDataSource ds = new DynamicDataSource();
        ds.setTargetDataSources(targetDataSources);
        ds.setDefaultTargetDataSource(defaultTargetDataSource);
        return ds;
    }
    @Override
    public void setEnvironment(Environment env) {
        try {
            BaseProperties.loadData(env);
            // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
            propertyResolver = new RelaxedPropertyResolver(env, "dataSource.");
        } catch (Exception ex) {
            throw new SysException(90000, ex.getMessage(), ex);
        }

        loadDataConfig(propertyResolver);
    }

    private void loadDataConfig(RelaxedPropertyResolver propertyResolver){
        try {
            // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
            String dsNames = propertyResolver.getProperty("names");
            String[] ds = dsNames.split(",");
            for (int i = 0; i < ds.length; i++) {// 多个数据源
                String dsPrefix = ds[i];
                Map<String, Object> dsParam = propertyResolver.getSubProperties(dsPrefix + ".");
                DataSource dataSource = buildDataSource(dsParam);
                DataSourceContextHolder.dataSourceIds.add(dsPrefix);
                targetDataSources.put(dsPrefix,dataSource);
                if(i==0){
                    defaultTargetDataSource = dataSource;
                    continue;
                }
            }
        } catch (Exception ex) {
            throw new SysException(5000, ex.getMessage(), ex);
        }
    }



    private DataSource buildDataSource(Map<String, Object> ds) {
        DruidDataSource druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setDriverClassName((String) ds.get("driverClassName"));
            druidDataSource.setUrl((String) ds.get("url"));
            druidDataSource.setUsername((String) ds.get("username"));
            druidDataSource.setPassword((String) ds.get("password"));
            druidDataSource.setFilters("stat");
            String initialSize = (String) ds.get("initialSize");
            if (initialSize != null) {
                druidDataSource.setInitialSize(Integer.valueOf(initialSize));
            }

            String maxActive = (String) ds.get("maxActive");
            if (maxActive != null) {
                druidDataSource.setMaxActive(Integer.valueOf(maxActive));
            }

            String minIdle = (String) ds.get("minIdle");
            if (minIdle != null) {
                druidDataSource.setMinIdle(Integer.valueOf(minIdle));
            }

            String maxWait = (String) ds.get("maxWait");
            if (maxWait != null) {
                druidDataSource.setMaxWait(Long.valueOf(maxWait));
            }

            String timeBetweenEvictionRunsMillis = (String) ds.get("timeBetweenEvictionRunsMillis");
            if (timeBetweenEvictionRunsMillis != null) {
                druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(timeBetweenEvictionRunsMillis));
            }

            String minEvictableIdleTimeMillis = (String) ds.get("minEvictableIdleTimeMillis");
            if (minEvictableIdleTimeMillis != null) {
                druidDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(minEvictableIdleTimeMillis));
            }

            druidDataSource.setTestWhileIdle(BaseProperties.getProperty("jdbc.testWhileIdle", Boolean.class, true));
            druidDataSource.setTestOnBorrow(BaseProperties.getProperty("jdbc.testOnBorrow", Boolean.class, false));
            druidDataSource.setTestOnReturn(BaseProperties.getProperty("jdbc.testOnReturn", Boolean.class, false));

            druidDataSource.setPoolPreparedStatements(true);

            String maxPoolPreparedStatementPerConnectionSize = (String) ds.get("maxPoolPreparedStatementPerConnectionSize");
            if(maxPoolPreparedStatementPerConnectionSize != null){
                druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(maxPoolPreparedStatementPerConnectionSize));
            }

            druidDataSource.setValidationQuery("select 1");
            // 超过时间限制是否回收
            druidDataSource.setRemoveAbandoned(BaseProperties.getProperty("jdbc.removeAbandoned", Boolean.class, false));
            // 超时时间；单位为秒。180秒=3分钟
            druidDataSource.setRemoveAbandonedTimeout(BaseProperties.getProperty("jdbc.removeAbandonedTimeout", Integer.class, 180));
            // 关闭abanded连接时输出错误日志
            druidDataSource.setLogAbandoned(false);

            druidDataSource.init();

        } catch (Exception ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, ex.getMessage(), ex);
        }

        return druidDataSource;
    }

}
