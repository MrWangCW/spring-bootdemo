package com.wang.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Excel读取
 */
public class ExcelReader {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * @param workbook         workbook
     * @param readDataCallback 回调
     * @param <T>              读取数据封装
     * @return List<T>
     */
    public static <T> List<T> read(Workbook workbook, ReadDataCallback<T> readDataCallback) {
        return read(workbook, 0, readDataCallback);
    }

    /**
     * Excel读取
     *
     * @param workbook         workbook
     * @param beginRow         起始行(0-sheet.getLastRowNum())
     * @param readDataCallback 回调
     * @param <T>              读取数据封装
     * @return List<T>
     */
    public static <T> List<T> read(Workbook workbook, int beginRow, ReadDataCallback<T> readDataCallback) {
        //读取第一个sheet
        final Sheet sheet = workbook.getSheetAt(0);
        if (beginRow > sheet.getLastRowNum()) {
            return Collections.emptyList();
        }
        return Stream.iterate(beginRow, i -> i + 1).limit(sheet.getLastRowNum() + 1 - beginRow).map(sheet::getRow).map(readDataCallback::call).collect(Collectors.toList());
    }


    /**
     * @param filePath //文件路径
     * @return list
     */
    public static List<Map<String, String>> readExcel(String filePath, List<Map<String, String>> varList, String var) {


        try {
            File file = new File(filePath);

            Workbook workbook;
            FileInputStream in = new FileInputStream(file);
            String fileName = file.getName();
            if(fileName.endsWith(XLS)){
                workbook = new HSSFWorkbook(in);
            }else if(fileName.endsWith(XLSX)){
                workbook = new XSSFWorkbook(in);
            }else{
                throw new RuntimeException();
            }
            //sheet 从0开始
            Sheet sheet = workbook.getSheetAt(0);

            //取得最后一行的行号
            int rowNum = sheet.getLastRowNum() + 1;
            //行循环开始
            for (int i = 0; i < rowNum; i++) {
                //行
                Row row = sheet.getRow(i);
                //每行的最后一个单元格位置
                int cellNum = row.getLastCellNum();
                Map<String, String> rowMap = new HashMap<>(cellNum);
                //列循环开始
                for (int j = 0; j < cellNum; j++) {

                    Cell cell = row.getCell(Short.parseShort(j + ""));

                    String cellValue;
                    if (null != cell) {
                        // 判断excel单元格内容的格式，并对其进行转换
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case FORMULA:
                                cellValue = cell.getNumericCellValue() + "";
                                break;
                            case BLANK:
                                cellValue = "";
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                            default:
                                cellValue = "";
                        }
                    } else {
                        cellValue = "";
                    }
                    rowMap.put(var + j, cellValue);
                }
                varList.add(rowMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return varList;
    }


}
