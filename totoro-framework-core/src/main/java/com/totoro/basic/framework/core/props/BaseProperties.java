package com.totoro.basic.framework.core.props;

import com.totoro.basic.framework.core.consts.SysErrorConsts;
import com.totoro.basic.framework.core.exception.SysException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 配置文件信息
 *
 * @author Maleah
 * @create 2017-09-26 15:30
 **/
public class BaseProperties extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

    private static ConfigurableConversionService conversionService = new DefaultConversionService();

    private static AtomicBoolean loadFlog = new AtomicBoolean(true);

    private static final String APP_PROPERTIES = "applicationConfigurationProperties";

    private static final String CLASS_PATH_RESOURCE = "class path resource";


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        if (loadFlog.get()) {
            loadData(props);
        }
    }

    public static void loadData(Properties props)
            throws BeansException {
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }


    public static Object getProperty(String key) {
        return ctxPropertiesMap.get(key);
    }

    public static String getString(String key) {
        return (String) ctxPropertiesMap.get(key);
    }

    public static Map<String, Object> getAll() {
        return ctxPropertiesMap;
    }

    public static boolean containsProperty(String key) {
        return ctxPropertiesMap.containsKey(key);
    }

    public static boolean setProperty(String key, String value) {
        if (key == null || value == null) {
            return false;
        }

        ctxPropertiesMap.put(key, value);

        return true;

    }

    public static String getProperty(String key, String defaultValue) {
        Object value = ctxPropertiesMap.get(key);
        return value == null ? defaultValue : (String) value;
    }

    public static <T> T getProperty(String key, Class<T> targetType) {
        Object value = ctxPropertiesMap.get(key);
        if(value == null){
            return null;
        }
        return conversionService.convert(value, targetType);
    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        Object value = ctxPropertiesMap.get(key);
        if(value == null){
            return defaultValue;
        }
        return conversionService.convert(value, targetType);
    }

    public static void loadData(Environment event) {
        if (loadFlog.get()) {
            ConfigurableEnvironment environment = (ConfigurableEnvironment)event;

            Iterator<PropertySource<?>> iter = environment.getPropertySources().iterator();
            while (iter.hasNext()) {
                PropertySource<?> ps = iter.next();
                String name = ps.getName();
                if(name != null){
                    if (APP_PROPERTIES.equals(name)) {
                        EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) ps;
                        Map<String, Object> data = new HashMap<String, Object>();
                        for (String key : eps.getPropertyNames()) {
                            data.put(key, eps.getProperty(key));
                        }
                        ctxPropertiesMap = data;
                    }else if(name.startsWith(CLASS_PATH_RESOURCE)){
                        try {
                            String propertiesName = name.substring(name.indexOf("[") + 1, name.lastIndexOf("]"));
                            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertiesName);
                            loadData(properties);
                        } catch (IOException e) {
                            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
                        }
                    }
                }
            }
            loadFlog.set(false);
        }
    }
}
