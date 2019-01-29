package com.wang.util.weather;

import weather.ArrayOfString;
import weather.WeatherWebService;
import weather.WeatherWebServiceSoap;

import java.util.List;

/**
 * 查询天气工具类
 * Created by wangyanwei on 2019/1/8.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class WeatherUtil {

    public static void main(String[] args) {
        /*String(0) 到 String(4)：省份，城市，城市代码，城市图片名称，最后更新时间。
        String(5) 到 String(11)：当天的 气温，概况，风向和风力，天气趋势开始图片名称(以下称：图标一)，
                                 天气趋势结束图片名称(以下称：图标二)，现在的天气实况，天气和生活指数。
        String(12) 到 String(16)：第二天的 气温，概况，风向和风力，图标一，图标二。
        String(17) 到 String(21)：第三天的 气温，概况，风向和风力，图标一，图标二。
        String(22) 被查询的城市或地区的介绍 */
        WeatherWebService service = new WeatherWebService();
        WeatherWebServiceSoap soap = service.getWeatherWebServiceSoap();
        ArrayOfString weatherByCityName = soap.getWeatherbyCityName("上海");
        List<String> weatherByCityNameList = weatherByCityName.getString();
        for(String s:weatherByCityNameList){
            System.out.println(s);
        }


    }

}
