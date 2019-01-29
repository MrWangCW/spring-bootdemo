package com.wang.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wang.util.DateUtils;
import com.wang.util.excel.ExcelReader;
import com.wang.util.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by wangyanwei on 2018/12/19.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Controller
@RequestMapping("down")
public class DownExcelController {

    /**
     * 导出Excel
     * @param
     * @return
     */
    @RequestMapping(value = "downExcel", produces = "application/json;charset=UTF-8")
    public ModelAndView boonOrderDownExcel() {

        List<Map<String, String>> contentMapList = Lists.newArrayList();


        List<Map<String, String>> varList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> oneList = ExcelReader.readExcel("D:\\report\\111.xlsx", varList, "one");

        varList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> twoList = ExcelReader.readExcel("D:\\report\\222.xlsx", varList, "two");
        varList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> threeList = ExcelReader.readExcel("D:\\report\\333.xlsx", varList, "three");


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
            contentMapList.add(oneMap);
        }

        System.out.println(JSON.toJSON(contentMapList));


        /*List<String> titleList = LoadExcelMap.getTitleList(new String[]{"one0", "one1", "one2", "one3", "one4", "one5", "one6", "one7", "one8", "one9", "one10", "one11", "one12", "one13", "one14", "one15", "one16", "one17", "one18", "one19", "one20", "one21"
                 ,"two0","two1","two2","two3","two4","two5","two6","two7","two8","two9","two10","two11","two12","two13","two14","two15","two16","two17","two18","two19"
                ,"three0","three1","three2","three3","three4","three5","three6","three7","three8","three9","three10","three11","three12","three13","three14","three15","three16","three17"});
        //封装excel的map
        Map<String, Object> map = LoadExcelMap.LoadExcelMap("加权平均成绩", titleList, contentMapList);
        LoadExcel loadExcel = new LoadExcel();
        ModelAndView mv = new ModelAndView(loadExcel, map);
        return mv;*/
        return null;
    }

    @RequestMapping("modelExport.mvc")
    public void modelExport(HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("券下发模板.xls", "UTF-8"));
        List<Map> dataList = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("realname", "张三");
        data1.put("userId", "12345678");
        data1.put("amount", 500.0);
        dataList.add(data1);
        Map<String, Object> data2 = new HashMap<>();
        data2.put("realname", "李四");
        data2.put("userId", "12341234");
        data2.put("amount", 300.0);
        dataList.add(data2);
        final Workbook workbook = ExcelWriter.writer(HSSFWorkbook.class, 1, new String[]{"接收人姓名", "接收人高颜值id", "金额"}, dataList, (row, data) -> {
            row.createCell(0).setCellValue((String) data.get("realname"));
            row.createCell(1).setCellValue((String) data.get("userId"));
            row.createCell(2).setCellValue((Double) data.get("amount"));
        });
        final Row firstRow = workbook.getSheetAt(0).createRow(0);
        firstRow.createCell(0).setCellValue("福利名称");
        String date = DateUtils.formatDate(DateUtils.getSetoffMonth(new Date(),12),"yyyy-MM-dd");
        firstRow.createCell(1).setCellValue(date);
        workbook.write(response.getOutputStream());
        Thread thread=new Thread(()->
                System.out.println(123)
        );
    }


    public void exportExcel(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException
    {
        HSSFWorkbook wb = new HSSFWorkbook();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/x-download");

        String fileName = "交易记录.xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        HSSFSheet sheet = wb.createSheet("会员交易记录");
        sheet.setDefaultRowHeight((short) (2 * 256));
        sheet.setColumnWidth(0, 50 * 160);
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        HSSFRow row = sheet.createRow((int) 0);
        sheet.createRow((int) 1);
        sheet.createRow((int) 2);
        sheet.createRow((int) 3);
        sheet.createRow((int) 10);
        sheet.createRow((int) 11);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("编号 ");
        cell.setCellStyle(style);
        cell.setCellValue("会员姓名");
        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("会员手机号");
        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("学校 ");
        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("院系 ");
        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("交易日期 ");
        cell = row.createCell(8);
        cell.setCellStyle(style);
        cell.setCellValue("消费类型");
        cell = row.createCell(9);
        cell.setCellStyle(style);
        cell.setCellValue("产品名称");
        cell = row.createCell(10);
        cell.setCellStyle(style);
        cell.setCellValue("消费金额 ");
        cell = row.createCell(11);
        cell.setCellStyle(style);
        cell.setCellValue("状态");


        try
        {
            OutputStream out = resp.getOutputStream();
            wb.write(out);
            out.close();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
