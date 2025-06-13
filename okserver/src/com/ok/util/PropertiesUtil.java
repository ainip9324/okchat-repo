package com.ok.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    //单例模式
    private static PropertiesUtil propertiesUtil = null;
    //读取配置文件使用
    private Properties properties = null;

    private PropertiesUtil(){
        properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("com/ok/config.properties");

        //讲配置文件加载到properties里面
        try{
            properties.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  PropertiesUtil getPropertiesUtil(){
        if(propertiesUtil == null){
            propertiesUtil = new PropertiesUtil();
        }
        return propertiesUtil;
    }

    public String getValue(String key){
        return properties.getProperty(key);
    }
}
