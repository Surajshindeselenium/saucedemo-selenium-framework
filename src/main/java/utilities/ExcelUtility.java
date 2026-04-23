package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * ExcelUtility - Utility class for reading and fetching data from Excel files
 * Supports reading data from custDetails.xlsx and other Excel files in testData directory
 */
public class ExcelUtility {
    private Workbook workbook;
    private String filePath;

    /**
     * Constructor - Initialize with Excel file path
     * @param filePath - Path to the Excel file
     */
    public ExcelUtility(String filePath) {
        this.filePath = filePath;
        loadWorkbook();
    }

    /**
     * Load the Excel workbook
     */
    private void loadWorkbook() {
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.err.println("Error loading Excel file: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Get a specific sheet by name
     * @param sheetName - Name of the sheet
     * @return Sheet object
     */
    public Sheet getSheet(String sheetName) {
        return workbook.getSheet(sheetName);
    }

    /**
     * Get sheet by index
     * @param sheetIndex - Index of the sheet
     * @return Sheet object
     */
    public Sheet getSheet(int sheetIndex) {
        return workbook.getSheetAt(sheetIndex);
    }

    /**
     * Get number of sheets in workbook
     * @return Count of sheets
     */
    public int getSheetCount() {
        return workbook.getNumberOfSheets();
    }

    /**
     * Get all sheet names
     * @return List of sheet names
     */
    public List<String> getAllSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheetNames.add(workbook.getSheetAt(i).getSheetName());
        }
        return sheetNames;
    }

    /**
     * Get cell value as String
     * @param sheetName - Sheet name
     * @param rowNum - Row number (0-indexed)
     * @param colNum - Column number (0-indexed)
     * @return Cell value as String
     */
    public String getCellValue(String sheetName, int rowNum, int colNum) {
        Sheet sheet = getSheet(sheetName);
        if (sheet == null) {
            return null;
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(colNum);
        return getCellValueAsString(cell);
    }

    /**
     * Get cell value as String by sheet index
     * @param sheetIndex - Sheet index
     * @param rowNum - Row number (0-indexed)
     * @param colNum - Column number (0-indexed)
     * @return Cell value as String
     */
    public String getCellValue(int sheetIndex, int rowNum, int colNum) {
        Sheet sheet = getSheet(sheetIndex);
        if (sheet == null) {
            return null;
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(colNum);
        return getCellValueAsString(cell);
    }

    /**
     * Convert cell value to String based on cell type
     * @param cell - Cell object
     * @return Cell value as String
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numValue = cell.getNumericCellValue();
                    // Check if it's an integer
                    if (numValue == (long) numValue) {
                        return String.valueOf((long) numValue);
                    }
                    return String.valueOf(numValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * Get entire row data as array
     * @param sheetName - Sheet name
     * @param rowNum - Row number (0-indexed)
     * @return Array of cell values
     */
    public String[] getRowData(String sheetName, int rowNum) {
        Sheet sheet = getSheet(sheetName);
        if (sheet == null) {
            return new String[0];
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return new String[0];
        }

        String[] rowData = new String[row.getLastCellNum()];
        for (int i = 0; i < row.getLastCellNum(); i++) {
            rowData[i] = getCellValueAsString(row.getCell(i));
        }
        return rowData;
    }

    /**
     * Get entire row data as array by sheet index
     * @param sheetIndex - Sheet index
     * @param rowNum - Row number (0-indexed)
     * @return Array of cell values
     */
    public String[] getRowData(int sheetIndex, int rowNum) {
        Sheet sheet = getSheet(sheetIndex);
        if (sheet == null) {
            return new String[0];
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return new String[0];
        }

        String[] rowData = new String[row.getLastCellNum()];
        for (int i = 0; i < row.getLastCellNum(); i++) {
            rowData[i] = getCellValueAsString(row.getCell(i));
        }
        return rowData;
    }

    /**
     * Get all data from a sheet
     * @param sheetName - Sheet name
     * @return 2D array of cell values
     */
    public String[][] getAllSheetData(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        if (sheet == null) {
            return new String[0][0];
        }

        int rowCount = sheet.getLastRowNum() + 1;
        int colCount = 0;

        // Find maximum column count
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getLastCellNum() > colCount) {
                colCount = row.getLastCellNum();
            }
        }

        String[][] allData = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            allData[i] = getRowData(sheetName, i);
        }
        return allData;
    }

    /**
     * Get all data from a sheet by index
     * @param sheetIndex - Sheet index
     * @return 2D array of cell values
     */
    public String[][] getAllSheetData(int sheetIndex) {
        Sheet sheet = getSheet(sheetIndex);
        if (sheet == null) {
            return new String[0][0];
        }

        int rowCount = sheet.getLastRowNum() + 1;
        int colCount = 0;

        // Find maximum column count
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getLastCellNum() > colCount) {
                colCount = row.getLastCellNum();
            }
        }

        String[][] allData = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            allData[i] = getRowData(sheetIndex, i);
        }
        return allData;
    }

    /**
     * Get data as List of Maps (header-based)
     * Assumes first row contains headers
     * @param sheetName - Sheet name
     * @return List of Maps where each map represents a row
     */
    public List<Map<String, String>> getDataAsMap(String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        Sheet sheet = getSheet(sheetName);
        if (sheet == null) {
            return dataList;
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return dataList;
        }

        // Get headers
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headers.add(getCellValueAsString(headerRow.getCell(i)));
        }

        // Get data rows
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row dataRow = sheet.getRow(rowNum);
            if (dataRow != null) {
                Map<String, String> rowMap = new HashMap<>();
                for (int colNum = 0; colNum < headers.size(); colNum++) {
                    rowMap.put(headers.get(colNum), getCellValueAsString(dataRow.getCell(colNum)));
                }
                dataList.add(rowMap);
            }
        }
        return dataList;
    }

    /**
     * Get data as List of Maps by sheet index
     * Assumes first row contains headers
     * @param sheetIndex - Sheet index
     * @return List of Maps where each map represents a row
     */
    public List<Map<String, String>> getDataAsMap(int sheetIndex) {
        List<Map<String, String>> dataList = new ArrayList<>();
        Sheet sheet = getSheet(sheetIndex);
        if (sheet == null) {
            return dataList;
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return dataList;
        }

        // Get headers
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            headers.add(getCellValueAsString(headerRow.getCell(i)));
        }

        // Get data rows
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row dataRow = sheet.getRow(rowNum);
            if (dataRow != null) {
                Map<String, String> rowMap = new HashMap<>();
                for (int colNum = 0; colNum < headers.size(); colNum++) {
                    rowMap.put(headers.get(colNum), getCellValueAsString(dataRow.getCell(colNum)));
                }
                dataList.add(rowMap);
            }
        }
        return dataList;
    }

    /**
     * Get number of rows in a sheet
     * @param sheetName - Sheet name
     * @return Number of rows
     */
    public int getRowCount(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        if (sheet == null) {
            return 0;
        }
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Get number of rows in a sheet by index
     * @param sheetIndex - Sheet index
     * @return Number of rows
     */
    public int getRowCount(int sheetIndex) {
        Sheet sheet = getSheet(sheetIndex);
        if (sheet == null) {
            return 0;
        }
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Get number of columns in a sheet
     * @param sheetName - Sheet name
     * @return Number of columns
     */
    public int getColumnCount(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        if (sheet == null) {
            return 0;
        }
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * Get number of columns in a sheet by index
     * @param sheetIndex - Sheet index
     * @return Number of columns
     */
    public int getColumnCount(int sheetIndex) {
        Sheet sheet = getSheet(sheetIndex);
        if (sheet == null) {
            return 0;
        }
        return sheet.getRow(0).getLastCellNum();
    }

    /**
     * Close the workbook and free resources
     */
    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing workbook: " + filePath);
            e.printStackTrace();
        }
    }
}

