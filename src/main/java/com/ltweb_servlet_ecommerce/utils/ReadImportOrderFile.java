package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.model.ImportOrderDetailModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadImportOrderFile {
    private static final int COLUMN_INDEX_ID = 1;
    private static final int COLUMN_INDEX_NAME = 2;
    private static final int COLUMN_INDEX_CATEGORY_CODE = 3;
    private static final int COLUMN_INDEX_CATEGORY_NAME = 4;
    private static final int COLUMN_INDEX_SIZE = 5;
    private static final int COLUMN_INDEX_QUANTITY = 6;
    private static final int COLUMN_INDEX_PRICE = 7;

    public static List<ImportOrderDetailModel> readExcel(InputStream inputStream) throws IOException {
        List<ImportOrderDetailModel> listImportOrderDetailModels = new ArrayList<>();

        // Get workbook
        Workbook workbook = new XSSFWorkbook(inputStream);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();

            // Skip header
            if (nextRow.getRowNum() < 13) {
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for model object
            ImportOrderDetailModel model = new ImportOrderDetailModel();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for model object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_ID:
                        if (cellValue instanceof Number) {
                            model.setProductId(((Number) cellValue).intValue());
                        }
                        break;
                    case COLUMN_INDEX_NAME:
                        if (cellValue instanceof String) {
                            model.setProductName((String) cellValue);
                        }
                        break;
                    case COLUMN_INDEX_QUANTITY:
                        if (cellValue instanceof Number) {
                            model.setQuantityImport(((Number) cellValue).intValue());
                        }
                        break;
                    case COLUMN_INDEX_SIZE:
                        if (cellValue instanceof String) {
                            model.setSize((String) cellValue);
                        }
                        break;
                    case COLUMN_INDEX_CATEGORY_CODE:
                        if (cellValue instanceof String) {
                            model.setCategoryCode((String) cellValue);
                        }
                        break;
                    case COLUMN_INDEX_CATEGORY_NAME:
                        if (cellValue instanceof String) {
                            model.setCategoryName((String) cellValue);
                        }
                        break;
                    case COLUMN_INDEX_PRICE:
                        if (cellValue instanceof Number) {
                            model.setPriceImport(((Number) cellValue).doubleValue());
                        }
                        break;
                    default:
                        break;
                }

            }
            listImportOrderDetailModels.add(model);
        }

        workbook.close();
        inputStream.close();

        return listImportOrderDetailModels;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }
}
