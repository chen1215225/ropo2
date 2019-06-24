package com.delai.bees.tops.service.helper;

import com.delai.bees.tops.entity.BitSignalConfig;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static final String SHEET_NAME = "Bit Signal Template";
    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validate(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }

    public static List<BitSignalConfig> read(Workbook wb) throws ParseException {
        Sheet sheet = wb.getSheet(SHEET_NAME);
        if (sheet == null) {
            throw new RuntimeException("导入失败，未找到文件未找到SHEET["+SHEET_NAME+"]");
        }
        Iterator<Row> it = sheet.iterator();
        List<BitSignalConfig> arr = new ArrayList<>();
        while (it.hasNext()){
            Row row = it.next();
            if (row.getRowNum() < 1) {
                continue;
            }
            BitSignalConfig record = convert(row);
            arr.add(record);
        }
        return arr;
    }

    private static BitSignalConfig convert(Row row) {
        return BitSignalConfig.builder()
                .address(row.getCell(1).getStringCellValue())
                .key(row.getCell(2).getStringCellValue())
                .remark(row.getCell(3).getStringCellValue())
                .position(Integer.valueOf(row.getCell(5).getStringCellValue()))
                .build();
    }
}
