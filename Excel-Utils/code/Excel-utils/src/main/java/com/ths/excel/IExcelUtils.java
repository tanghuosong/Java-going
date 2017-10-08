package com.ths.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * created by ths 2017/8/25
 * description:
 **/
public interface IExcelUtils {

    void createExcel(String sheetName, String[] headers, List<Object[]> contents, OutputStream outputStream);

    List<Map<String,Object>>  readExcel(String[] fields, InputStream inputStream);
}
