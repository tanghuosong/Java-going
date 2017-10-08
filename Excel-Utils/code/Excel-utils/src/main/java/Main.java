import com.ths.excel.ExcelUtils;
import com.ths.excel.IExcelUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.*;

/**
 * created by ths 2017/8/25
 * description:
 **/
public class Main <T>{


    public static void main(String [] args){

        IExcelUtils excelUtils = new ExcelUtils();

        String sheetName = "测试";
        String[] strings = {"编号","名称","性别","年龄","地址","出生日期"};

        String[] headers = {"id","name","sex","age","address","date"};
        Map<String ,String> map = new HashMap<>();

//        testExport(excelUtils, sheetName, strings);
        List<User> list = new ArrayList<>();
        for(int i =0 ;i <10; i++){
            User user = new User(1L,"ths","nan",22,"安徽","aaaa","bbbb");
            list.add(user);
        }


        // 获得某个实体的所有属性数组
        Field[] fields = User.class.getDeclaredFields();

        Main main = new Main();
        main.ModelToObjectArray(list,  fields);


        // 遍历 实体属性，并取值


//            for(int j = 0; j<fieldsLength ; j++){
//                // 判断 字段名同 头名称是否对应，对应则加入object[]中，保证顺序的正确性
//
//                if(fields[i].equals(fields1[j].getName())){
////                    System.out.println("fields"+j+"----------------"+fields1[i].getName());
//                    try {
////                        System.out.println(fields1[i].get(user));
//                        objects[i] = fields1[i].get(user);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }





//        readExcel();
//        try {
//            User user = new User();
//            InputStream inputStream = new FileInputStream("d:/test.xlsx");
//            for(Map<String,Object> map1 : excelUtils.readExcel(fields,inputStream)){
//                BeanUtils.populate(user,map1);
//                System.out.println(user);
//            }
//
//        }catch (IOException e){
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        readWorkbook("d:/test.xlsx",filds);
    }

    public List<Object[]> ModelToObjectArray(List<T> list,  Field[] fields) {
        List<Object[]> arrayList = new ArrayList<>();
        // 获得属性数量
        int fieldsLength = fields.length;
        // 创建接收数组
        Object[] objects = new Object[fieldsLength];

        for(int i=0; i< list.size();i++){
            for(int j = 0; j<fieldsLength;j++ ) {
                try {
                    System.out.print(fields[j].get(list.get(i)));
                    objects[j] = fields[j].get(list.get(i));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
            arrayList.add(objects);
        }
        return arrayList;
    }

    public static void createExcelFromModelList(List<Object> list,String sheetName,String[] headers,OutputStream outputStream){
        // 列数
        int colLength = headers.length;
        // 行数
        int rowLength = list.size();

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
//                        String value = contents.get(rowNum-1)[colNum].toString();
//                        if(value != null || !"null".equals(value)){
//                            headerRow.createCell(colNum).setCellValue(value);
//                        }



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



    public static void readExcel() {
        try {
            XSSFWorkbook xssfWorkbook  = new XSSFWorkbook(new FileInputStream("d:/test.xlsx"));

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()){
                Row row =  rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()){
                        case BLANK:
                            System.out.print("BLANK"+"=====");
                            break;
                        case NUMERIC:
                            System.out.print("NUMERIC"+cell.getNumericCellValue()+"=====");
                            break;
                        case STRING:
                            System.out.print("STRING"+cell.getStringCellValue()+"=====");
                            break;
                        case BOOLEAN:
                            System.out.print("BOOLEAN"+cell.getBooleanCellValue()+"=====");
                            break;
                        case _NONE:
                            System.out.print("_NONE"+"=====None");
                            break;
                        case FORMULA:
                            System.out.print("FORMULA：错误"+"=====");
                            break;
                        case ERROR:
                            System.out.print("ERROR"+"=====");
                            break;
                    }
//                    System.out.print(""+cell.getRowIndex()+"----"+cell.getColumnIndex()+"----"+cell.getStringCellValue());
//                    System.out.print(cell.getStringCellValue()+"\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
         * 通过对单元格遍历的形式来获取信息 ，这里要判断单元格的类型才可以取出值
         */
    public static void readWorkbook(String path,String[] filds) {
        InputStream inp = null;
        Workbook workbook = null;
        try {
            inp = new FileInputStream(path);
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
                                map.put(filds[cell.getColumnIndex()],"");
                                break;
                            case BOOLEAN:
                                map.put(filds[cell.getColumnIndex()],cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                map.put(filds[cell.getColumnIndex()],cell.getErrorCellValue());
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
                                        map.put(filds[cell.getColumnIndex()],cell.getBooleanCellValue());
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
                                map.put(filds[cell.getColumnIndex()],formatNumericCell(cell.getNumericCellValue(), cell));
                                break;
                            case STRING:
                                map.put(filds[cell.getColumnIndex()],cell.getStringCellValue());
                                break;
                        }
                    }
                    User user = new User();
                    try {
                        BeanUtils.populate(user,map);
                        System.out.println(user);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
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

    private static void testExport(IExcelUtils excelUtils, String sheetName, String[] strings) {
        List<Object[]> objects = new ArrayList<>();
        for(int i= 0; i< 10; i++){
            Object[] objects1 = new Object[strings.length];
            for(int j = 0; j<strings.length; j++){
                objects1[j] = "T"+j;
            }
            objects.add(objects1);
        }
        try {
            File file  = File.createTempFile(".excel",".xlsx");
            OutputStream outputStream = new FileOutputStream(file);
            excelUtils.createExcel(sheetName, strings, objects,outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
