package utilities;

import java.util.List;
import java.util.Map;

/**
 * TestDataProvider - Helper class for reading test data from custDetails.xlsx
 * Provides easy access to customer and test data
 */
public class TestDataProvider {
    private static final String TEST_DATA_PATH = "src/main/resources/testData/custDetails.xlsx";
    private ExcelUtility excelUtility;

    /**
     * Constructor - Initialize with custDetails.xlsx file
     */
    public TestDataProvider() {
        this.excelUtility = new ExcelUtility(TEST_DATA_PATH);
    }

    /**
     * Get all test data as List of Maps from first sheet
     * Each map represents one row with column headers as keys
     * @return List of Maps containing test data
     */
    public List<Map<String, String>> getAllTestData() {
        return excelUtility.getDataAsMap(0);
    }

    /**
     * Get all test data as List of Maps from specific sheet
     * @param sheetName - Name of the sheet
     * @return List of Maps containing test data
     */
    public List<Map<String, String>> getTestDataBySheet(String sheetName) {
        return excelUtility.getDataAsMap(sheetName);
    }

    /**
     * Get specific row data as Map
     * @param rowNumber - Row number (0-indexed, excluding header)
     * @return Map containing row data with headers as keys
     */
    public Map<String, String> getTestDataByRow(int rowNumber) {
        List<Map<String, String>> allData = getAllTestData();
        if (rowNumber >= 0 && rowNumber < allData.size()) {
            return allData.get(rowNumber);
        }
        return null;
    }

    /**
     * Get cell value from first sheet
     * @param rowNum - Row number (0-indexed)
     * @param colNum - Column number (0-indexed)
     * @return Cell value as String
     */
    public String getCellValue(int rowNum, int colNum) {
        return excelUtility.getCellValue(0, rowNum, colNum);
    }

    /**
     * Get cell value from specific sheet
     * @param sheetName - Sheet name
     * @param rowNum - Row number (0-indexed)
     * @param colNum - Column number (0-indexed)
     * @return Cell value as String
     */
    public String getCellValue(String sheetName, int rowNum, int colNum) {
        return excelUtility.getCellValue(sheetName, rowNum, colNum);
    }

    /**
     * Get entire row as array
     * @param rowNum - Row number (0-indexed)
     * @return Array of cell values
     */
    public String[] getRowData(int rowNum) {
        return excelUtility.getRowData(0, rowNum);
    }

    /**
     * Get entire row as array from specific sheet
     * @param sheetName - Sheet name
     * @param rowNum - Row number (0-indexed)
     * @return Array of cell values
     */
    public String[] getRowData(String sheetName, int rowNum) {
        return excelUtility.getRowData(sheetName, rowNum);
    }

    /**
     * Get total number of rows
     * @return Total row count
     */
    public int getTotalRows() {
        return excelUtility.getRowCount(0);
    }

    /**
     * Get total number of rows from specific sheet
     * @param sheetName - Sheet name
     * @return Total row count
     */
    public int getTotalRows(String sheetName) {
        return excelUtility.getRowCount(sheetName);
    }

    /**
     * Get total number of columns
     * @return Total column count
     */
    public int getTotalColumns() {
        return excelUtility.getColumnCount(0);
    }

    /**
     * Get total number of columns from specific sheet
     * @param sheetName - Sheet name
     * @return Total column count
     */
    public int getTotalColumns(String sheetName) {
        return excelUtility.getColumnCount(sheetName);
    }

    /**
     * Get all sheet names in the workbook
     * @return List of sheet names
     */
    public List<String> getSheetNames() {
        return excelUtility.getAllSheetNames();
    }

    /**
     * Close the workbook and release resources
     */
    public void closeTestData() {
        excelUtility.closeWorkbook();
    }
}
