package com.example.poi;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * excel工具类
 */
public class ExcelUtils {
    /**
     * 制作导出的excel文件
     *
     * @param sheetName
     * @param headerList
     * @param dataList
     * @return
     */
    public static HSSFWorkbook createHSSExcel(String sheetName, List<String> headerList, List<List<String>> dataList) throws RuntimeException {

        if (CollectionUtils.isEmpty(headerList)) {
            throw new RuntimeException("GaeaExceptionEnum.EXPORT_COLUMN_NAME_EMPTY.getCode()");
        }

        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("GaeaExceptionEnum.EXPORT_COLUMN_NAME_EMPTY.getCode()");
        }

        if (StringUtils.isEmpty(sheetName)) {
            sheetName = "sheet1";
        }

        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，创建单元格，并设置值表头 设置表头居中
        HSSFRow row = sheet.createRow((int) 0);

        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16);//设置字体大小
        font.setBold(true);//粗体显示
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        int headerLength = headerList.size();
        for (int i = 0; i < headerLength; i++) {
            sheet.setColumnWidth(i, 20 * 256);
            HSSFCell cell = row.createCell((short) i);
            cell.setCellValue(headerList.get(i));
            cell.setCellStyle(cellStyle);
        }

        int line = 1;
        for (List<String> data : dataList) {
            int dataLength = data.size();
            if (headerLength != data.size()) {
                throw new RuntimeException("GaeaExceptionEnum.EXPORT_COLUMN_NAME_EMPTY.getCode()");
            }
            // 第四步，创建单元格，并设置值
            row = sheet.createRow(line);
            for (int j = 0; j < dataLength; j++) {
                row.createCell((short) j).setCellValue(data.get(j));
            }
            line++;
        }
        return wb;
    }


    /**
     * "1.0" to "1"
     * 读取数字类字符串转化为整数字符串
     *
     * @return
     */
    public static String parseIntString(String str) {
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");//去掉多余的0
            str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return str;
    }


    public static List<T> parseExcel(Workbook workbook, List<ParseModel> parseModels, T t) {
        if (workbook == null || CollectionUtils.isEmpty(parseModels)) {
            return Collections.emptyList();
        }


        return Collections.emptyList();
    }

    public class ParseModel {
        private Integer index;
        private String paramName;
        private String dataType;
        private Boolean required;
        private String exceptionMsg;
        private Enum dataEnum;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public String getExceptionMsg() {
            return exceptionMsg;
        }

        public void setExceptionMsg(String exceptionMsg) {
            this.exceptionMsg = exceptionMsg;
        }

        public Enum getDataEnum() {
            return dataEnum;
        }

        public void setDataEnum(Enum dataEnum) {
            this.dataEnum = dataEnum;
        }
    }

}
