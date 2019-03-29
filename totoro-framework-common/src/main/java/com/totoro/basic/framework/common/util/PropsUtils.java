package com.totoro.basic.framework.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件
 *
 * @author Maleah
 * @create 2017-11-10 16:45
 **/
public class PropsUtils {

    private static Properties prop;

    private static Properties init() {
        Properties propLocal = new Properties();
        try {
            InputStream in1 = PropsUtils.class.getResourceAsStream("/application.properties");
            propLocal.load(in1);
            String profiles = propLocal.getProperty("profiles.active");
            Properties prop1 = new Properties();
            InputStream in2 = PropsUtils.class.getResourceAsStream("/application-"+profiles+".properties");
            prop1.load(in2);
            return prop1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static String getProperty(String key){
        if(prop==null){
            prop = init();
        }
        return prop.getProperty(key);
    }

    public static String getProperty(String key,String defaultValue){
        if(prop==null){
            prop = init();
        }
        if(StringUtils.isBlank(prop.getProperty(key)))
            return defaultValue;
        return prop.getProperty(key);
    }

    public static void main(String[] args){
        System.out.println(getProperty("kafka.bootstrap.servers"));
    }

}
