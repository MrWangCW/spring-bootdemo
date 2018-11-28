package com.wang.util.propertiesutil;

import org.springframework.beans.BeansException;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wangyanwei on 2018/7/4.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class PropertiesUtil {

    private static Map<String, String> propertiesMap = new HashMap<>();

    private static Properties properties;

    private static void processProperties(Properties props) throws BeansException {
        propertiesMap = new HashMap<>(16);
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,注意是否乱码
                propertiesMap.put(keyStr, props.getProperty(keyStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void loadAllProperties(String propertyFileName) {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
            processProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByName(String name) {
        String result = properties.getProperty(name);
        return result == null ? "" : result.trim();
    }

    public static String getPropertyMapByName(String name) {
        String result = propertiesMap.get(name);
        return result == null ? "" : result.trim();
    }

}
