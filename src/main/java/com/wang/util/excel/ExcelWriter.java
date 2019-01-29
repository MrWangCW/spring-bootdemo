package com.wang.util.excel;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 导出Excel
 */
public class ExcelWriter {

    /**
     * 导出<br>上限60000
     * @param w                HSSFWorkbook or XSSFWorkbook
     * @param headers          表头
     * @param dataList         数据List
     * @param fillDataCallback 数据填充回调
     * @param <T>              T
     * @return HSSFWorkbook
     * @see org.apache.poi.hssf.usermodel.HSSFWorkbook
     */
    public static <T> Workbook writer(Class<? extends Workbook> w, int beginRow, String[] headers, List<T> dataList, FillDataCallback<T> fillDataCallback) {
        final Workbook workbook;
        try {
            final Constructor constructor = w.getConstructor();
            workbook = (Workbook) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
        //HSSFWorkbook workbook = new HSSFWorkbook();
        final Sheet sheet = workbook.createSheet();
        final Row headerRow = sheet.createRow(beginRow);
        //fill headers
        Stream.iterate(0, i -> i + 1).limit(headers.length).forEach(i -> headerRow.createCell(i).setCellValue(new HSSFRichTextString(headers[i])));
        //fill data
        dataList.stream().limit(60000).forEach(d -> {
            final Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            fillDataCallback.call(row, d);
        });
        return workbook;
    }




}
