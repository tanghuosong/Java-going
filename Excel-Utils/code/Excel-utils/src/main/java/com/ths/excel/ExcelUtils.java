package com.ths.excel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.Format;
import java.util.*;

/**
 * created by ths 2017/8/25
 * description:
 **/
public class ExcelUtils implements IExcelUtils{


    @Override
    public void createExcel(String sheetName, String[] headers, List<Object[]> contents, OutputStream outputStream) {

        // 列数
        int colLength = headers.length;
        // 行数
        int rowLength = contents.size();

        try{
            // 创建一个工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 创建一个表格
            XSSFSheet sheet = workbook.createSheet(sheetName);
            // 循环行数
            for(int rowNum = 0; rowNum <= rowLength; rowNum++){
                XSSFRow headerRow = sheet.createRow(rowNum);
                // 循环列数
                for(int colNum = 0;colNum <colLength; colNum++){
                    if(rowNum == 0){
                        headerRow.createCell(colNum).setCellValue(headers[colNum]);
                    }else{
                        String value = contents.get(rowNum-1)[colNum].toString();
                        if(value != null || !"null".equals(value)){
                            headerRow.createCell(colNum).setCellValue(value);
                        }
                    }
                }
            }
            workbook.write(outputStream);
            outputStream.close();
            // 为表格头设置值
        }catch (IOException e){
            e.getMessage();
        }
    }

    @Override
    public List<Map<String,Object>> readExcel(String[] fields, InputStream inp) {
            List<Map<String,Object>> list = new ArrayList<>();
            /*
             * 通过对单元格遍历的形式来获取信息 ，这里要判断单元格的类型才可以取出值
            */
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(inp);
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                // for/in是iterator的简写, 最终会被编译器编译为iterator
                // for(Iterator<Sheet> iterator=workbook.iterator(); iterator.hasNext();) {
                // Sheet sheet = iterator.next();
                for (Sheet sheet : workbook) {
                    for (Row row : sheet) {
                        if(row.getRowNum() == 0){
//                        break;
                            System.out.println(row.getRowNum());
                            continue;
                        }
                        Map<String,Object> map = new HashMap<>();
                        for (Cell cell : row) {
                            switch (cell.getCellTypeEnum()) {
                                case _NONE:
                                    System.out.print("_NONE" + "\t");
                                    break;
                                case BLANK:
                                    map.put(fields[cell.getColumnIndex()],"");
                                    break;
                                case BOOLEAN:
                                    map.put(fields[cell.getColumnIndex()],cell.getBooleanCellValue());
                                    break;
                                case ERROR:
                                    map.put(fields[cell.getColumnIndex()],cell.getErrorCellValue());
                                    break;
                                case FORMULA:
                                    // 会打印出原本单元格的公式
                                    // System.out.print(cell.getCellFormula() + "\t");
                                    // NumberFormat nf = new DecimalFormat("#.#");
                                    // String value = nf.format(cell.getNumericCellValue());
                                    CellValue cellValue = evaluator.evaluate(cell);
                                    switch (cellValue.getCellTypeEnum()) {
                                        case _NONE:
                                            System.out.print("_NONE" + "\t");
                                            break;
                                        case BLANK:
                                            System.out.print("BLANK" + "\t");
                                            break;
                                        case BOOLEAN:
                                            map.put(fields[cell.getColumnIndex()],cell.getBooleanCellValue());
                                            break;
                                        case ERROR:
                                            System.out.print("ERROR(" + cellValue.getErrorValue() + ")" + "\t");
                                            break;
                                        case NUMERIC:
                                            System.out.print(formatNumericCell(cellValue.getNumberValue(), cell) + "\t");
                                            break;
                                        case STRING:
                                            System.out.print(cell.getStringCellValue() + "\t");
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case NUMERIC:
                                    map.put(fields[cell.getColumnIndex()],formatNumericCell(cell.getNumericCellValue(), cell));
                                    break;
                                case STRING:
                                    map.put(fields[cell.getColumnIndex()],cell.getStringCellValue());
                                    break;
                            }
                        }
                        list.add(map);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (EncryptedDocumentException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inp != null) {
                    try {
                        inp.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return list;
    }
        /**
         * 原样返回数值单元格的内容
         */
    public static String formatNumericCell(Double value, Cell cell) {
        if(cell.getCellTypeEnum() != CellType.NUMERIC && cell.getCellTypeEnum() != CellType.FORMULA) {
            return null;
        }
        //isCellDateFormatted判断该单元格是"时间格式"或者该"单元格的公式算出来的是时间格式"
        if(DateUtil.isCellDateFormatted(cell)) {
            //cell.getDateCellValue()碰到单元格是公式,会自动计算出Date结果
//          Date date = DateUtil.getJavaDate(value);
            Date date = cell.getDateCellValue();
            DataFormatter dataFormatter = new DataFormatter();
            Format format = dataFormatter.createFormat(cell);
            return format.format(date);
        } else {
//          String formatStr = cell.getCellStyle().getDataFormatString();
//          if (formatStr.contains("0;")) {
//              formatStr = "0";
//            }else if (formatStr.contains("0.000")) {
//              formatStr = "0.000";
//            }  else if (formatStr.contains("0.00")) {
//              formatStr = "0.00";
//            } else if (formatStr.contains("0.0")) {
//              formatStr = "0.0";
//            } else if (formatStr.equals("General")) {
//              formatStr = "0";
//            } else if (formatStr.contains("GENERAL")) {
//              formatStr = "0";
//            } else if (formatStr.contains("0_")) {
//              formatStr = "0";
//            } else if (formatStr.equals("0")) {
//              formatStr = "0";
//            }
//          DecimalFormat df = new DecimalFormat(formatStr);
//          return df.format(value);
            DataFormatter dataFormatter = new DataFormatter();
            Format format = dataFormatter.createFormat(cell);
            return format.format(value);

        }
    }
}
