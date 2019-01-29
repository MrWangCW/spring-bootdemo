package com.wang.util.excel;

import com.alibaba.fastjson.JSON;
import java.util.*;

/**
 * Created by wangyanwei on 2018/12/19.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class Test {

    public static void main(String[] args) {
        List<Map<String, String>> varList = new ArrayList<Map<String, String>>();
        /*List<Map<String, String>> oneList = ExcelReader.readExcel("D:\\report\\111.xlsx", varList, "one");
        System.out.println(JSON.toJSON(oneList));
        varList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> twoList = ExcelReader.readExcel("D:\\report\\222.xlsx", varList, "two");
        System.out.println(JSON.toJSON(twoList));*/
        varList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> threeList = ExcelReader.readExcel("D:\\report\\666.xlsx", varList, "three");
        System.out.println(JSON.toJSON(threeList));
        /*List<Map<String, String>> margeList = new ArrayList<Map<String, String>>();

        Map<String,String> oneMap = new HashMap<>();
        Map<String,String> twoMap;
        Map<String,String> threeMap;
        for(int i=0;i< oneList.size() ;i++){
            oneMap = oneList.get(i);
            if(i < 2){
                twoMap = twoList.get(i);
                threeMap = threeList.get(i);
                oneMap.putAll(twoMap);
                oneMap.putAll(threeMap);
            }else{
                String  studentId = oneMap.get("one0");
                for(Map<String, String> aMap:twoList){
                    String aStudentId = oneMap.get("one0");
                    if(studentId.equals(aStudentId)){
                        oneMap.putAll(aMap);
                    }
                }
                for(Map<String, String> bMap:threeList){
                    String bStudentId = oneMap.get("one0");
                    if(studentId.equals(bStudentId)){
                        oneMap.putAll(bMap);
                    }
                }
            }
            margeList.add(oneMap);
        }

        System.out.println(JSON.toJSON(margeList));*/
    }

}
