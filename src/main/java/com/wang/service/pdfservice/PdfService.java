package com.wang.service.pdfservice;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by wangyanwei on 2019/9/18.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class PdfService {

    public static void main(String[] args) {
        try {
            getPdfFileText("F:\\pdfReader\\3【西方政治制度】【00316】.pdf", "F:\\pdfReader\\3西方政治制度00316.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取PDF文件
     */
    private static void getPdfFileText(String fileName, String newFilePath) throws IOException {
        PdfReader reader = new PdfReader(fileName);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

        FileWriter fw = null;
        BufferedWriter bw = null;
        File file = new File(newFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);


        TextExtractionStrategy strategy;
        for (int i = 4; i <= reader.getNumberOfPages(); i++) {
//            StringBuilder buff = new StringBuilder();
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            String resultantText = strategy.getResultantText();
//            buff.append(strategy.getResultantText());
//            writeFile(newFilePath, buff);
            resultantText = resultantText.replace("...........", ".");
            resultantText = resultantText.replace("\n", "\r\n");
            bw.write(resultantText);
            bw.newLine();
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    /**
     * 写入文件
     */
    public static void writeFile(String filePath, StringBuilder reader) {
        FileWriter fw;
        BufferedWriter bw;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            bw.write(reader.toString());
            bw.newLine();
            bw.newLine();
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
