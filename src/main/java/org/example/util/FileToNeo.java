package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileToNeo {

    public static void ReadFileToNeo(String url) throws IOException {
        InputStream is = new FileInputStream(url);
        DataFormatter formatter = new DataFormatter();
        Workbook wb = WorkbookFactory.create(new File(url));
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            String question = null;
            String answer = null;
            String pic_url = null;
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
                switch (cellRef.getCol()) {
                    case 0:
                        question = cell.getStringCellValue();
                        break;
                    case 1:
                        answer = cell.getStringCellValue();
                        break;
                    case 2:
                        pic_url = cell.getStringCellValue();
                        break;
                }
            }
            System.out.println(question + "\n" + answer + "\n" + pic_url + "\n" + "*******************\n");
        }
    }
}
