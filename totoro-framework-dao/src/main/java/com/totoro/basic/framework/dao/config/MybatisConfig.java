package com.totoro.basic.framework.dao.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.totoro.basic.framework.core.props.BaseProperties;

/**
 * mybatis的配置信息
 *
 * @author Maleah
 * @create 2017-11-01 11:33
 **/
@Configuration
public class MybatisConfig {

    @Autowired
    private DataSource dynamicDataSource;
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(){
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setTypeAliasesPackage(BaseProperties.getString("mybatis.typeAliasesPackage"));
        try {
            bean.setMapperLocations(resolver.getResources(BaseProperties.getString("mybatis.mapperLocations")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager  transactionManager = new DataSourceTransactionManager(dynamicDataSource);
        return transactionManager;
    }

}
