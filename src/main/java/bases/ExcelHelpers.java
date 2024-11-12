package bases;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelHelpers {

    private FileInputStream fis;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private CellStyle cellstyle;
    private Color mycolor;
    private String excelFilePath;
    public Map<String, Integer> columns = new HashMap<>();

    public void setExcelFile(String ExcelPath, String SheetName) throws Exception {
        try {
            File f = new File(ExcelPath);

            if (!f.exists()) {
                f.createNewFile();
                System.out.println("File doesn't exist, so created!");
            }

            fis = new FileInputStream(ExcelPath);

            wb = WorkbookFactory.create(fis);

            sh = wb.getSheet(SheetName);

            //sh = wb.getSheetAt(0); //0 - index of 1st sheet
            if (sh == null) {
                sh = wb.createSheet(SheetName);
            }

            this.excelFilePath = ExcelPath;

            //adding all the column header names to the map 'columns'
            sh.getRow(0).forEach(cell ->{
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCellData(int rownum, int colnum) throws Exception{
        try{
            cell = sh.getRow(rownum).getCell(colnum);
            String CellData = null;
            switch (cell.getCellType()){
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        CellData = String.valueOf(cell.getDateCellValue());
                    }
                    else
                    {
                        CellData = String.valueOf((long)cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        }catch (Exception e){
            return"";
        }
    }

    //Gọi ra hàm này
    public String getCellData(String columnName, int rownum) throws Exception {
        try {

            return getCellData(rownum, columns.get(columnName));

        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

    public void setCellData(String text, int rownum, int colnum) throws Exception {
        try{
            row  = sh.getRow(rownum);
            if(row ==null)
            {
                row = sh.createRow(rownum);
            }
            cell = row.getCell(colnum);

            if (cell == null) {
                cell = row.createCell(colnum);
            }
            cell.setCellValue(text);

            fileOut = new FileOutputStream(excelFilePath);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch(Exception e){
            throw (e);
        }
    }

    public File getLatestFileFromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles(File::isFile);
        if (files == null || files.length == 0) {
            return null;
        }

        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

    public String getLatestFilePathFromDownloads() {
        String downloadDir = System.getProperty("user.home") + "/Downloads";
        File latestFile = getLatestFileFromDir(downloadDir);
        return latestFile != null ? latestFile.getAbsolutePath() : null;
    }

    //phan xu ly nay cua anhntl52
    private static String getCellValue(FormulaEvaluator evaluator, Cell cExpectedCell) {
        String strCellValue = "";
        try {
            switch (cExpectedCell.getCellType()) {
                case BOOLEAN:
                    boolean bCellvaLue = cExpectedCell.getBooleanCellValue();
                    strCellValue = Boolean.toString(bCellvaLue).toLowerCase();
                    break;
                case NUMERIC:
                    Number nCellValue = cExpectedCell.getNumericCellValue();
                    strCellValue = nCellValue.toString();
                    break;
                case STRING:
                    strCellValue = cExpectedCell.getStringCellValue();
                    break;
                case FORMULA:
                    strCellValue = switch (evaluator.evaluateFormulaCell(cExpectedCell)) {
                        case BOOLEAN -> {
                            boolean bCellvaLueResult = cExpectedCell.getBooleanCellValue();
                            yield Boolean.toString(bCellvaLueResult).toLowerCase();
                        }
                        case NUMERIC -> {
                            Number nCellValueResult = cExpectedCell.getNumericCellValue();
                            yield nCellValueResult.toString();
                        }
                        case STRING -> {
                            strCellValue = cExpectedCell.getStringCellValue();
                            yield new String(strCellValue.getBytes(), StandardCharsets.UTF_8);
                        }
                        default ->
                                throw new IllegalStateException("Unexpected value: " + evaluator.evaluateFormulaCell(cExpectedCell));
                    };
            }
        } catch (Exception e) {
            return "";
        }
        return strCellValue;
    }

    public static List<Map<String, String>> getDataXLSX(String xlsxName, String sheetName, String headerName, String filter) {
        // Initiate dictionary to contain returned values
        List<Map<String, String>> dictTestData = new ArrayList<Map<String, String>>();

        try {
            // Initiate column count variable
            int iCol = 0;

            // Initiate file input stream to read excel file
            FileInputStream fis = new FileInputStream(xlsxName);

            // Initiate excel workbook which contains the sheet
            Workbook wb = new XSSFWorkbook(fis);

            // Initiate sheet which contains the expected cell
            Sheet sheet = wb.getSheet(sheetName);

            // Initiate Formula Evaluator to evaluate if the expected cell contains a formular
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

            Row rHeaderRows = sheet.getRow(0);
            Cell cHeaderCell = rHeaderRows.getCell(0);
            List<String> listHeaders = new ArrayList<String>();
            int foundHeaderIndex = -1;
            while (cHeaderCell != null) {
                String header = cHeaderCell.getStringCellValue();
                listHeaders.add(header);
                if (header.equals(headerName)) foundHeaderIndex = iCol;
                iCol++;
                cHeaderCell = rHeaderRows.getCell(iCol);
            }

            int rowCounts = sheet.getPhysicalNumberOfRows();
            for (int rowIndex = 1; rowIndex < rowCounts; rowIndex++) {
                if (foundHeaderIndex != -1) {
                    Row rExpectedRows = sheet.getRow(rowIndex);
                    Cell cExpectedCell = rExpectedRows.getCell(foundHeaderIndex);
                    if (getCellValue(evaluator, cExpectedCell).startsWith(filter)) {
                        // Initiate dictionary to contain returned values
                        Map<String, String> dictTestDataRow = new HashMap<>();
                        iCol = 0;
                        for (String header : listHeaders) {
                            rExpectedRows = sheet.getRow(rowIndex);
                            cExpectedCell = rExpectedRows.getCell(iCol);
                            dictTestDataRow.put(header, getCellValue(evaluator, cExpectedCell));
                            iCol++;
                        }
                        dictTestData.add(dictTestDataRow);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("getDataXLSX:: [%s] [%s] %s", xlsxName, sheetName, e.getMessage()));
            return null;
        }
        return dictTestData;
    }

    /******************************************************
     * Get Test data XLSX
     * @param xlsxName : the xlsx file name
     * @param sheetName: the sheet name
     * @param rowIndex: the row index
     * @return Map<ColumnName, value>
     */
    public static Map<String, String> getDataXLSX(String xlsxName, String sheetName, int rowIndex) {
        // Initiate dictionary to contain returned values
        Map<String, String> dictTestDataRow = new HashMap<>();

        try {
            if (rowIndex == 0) {
                throw new Exception("Row 0 is used as header name, row index must start from 1");
            } else {
                // Initiate file input stream to read excel file
                FileInputStream fis = new FileInputStream(xlsxName);

                // Initiate excel workbook which contains the sheet
                Workbook wb = new XSSFWorkbook(fis);

                // Initiate sheet which contains the expected cell
                Sheet sheet = wb.getSheet(sheetName);

                // Initiate Formula Evaluator to evaluate if the expected cell contains a formular
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

                // Initiate column count variable
                int iCol = 0;
                Row rHeaderRows = sheet.getRow(0);
                Cell cHeaderCell = rHeaderRows.getCell(0);
                String header;
                while (cHeaderCell != null) {
                    header = cHeaderCell.getStringCellValue();
                    // Get value of corresponding cell
                    Row rExpectedRows = sheet.getRow(rowIndex);
                    Cell cExpectedCell = rExpectedRows.getCell(iCol);
                    dictTestDataRow.put(header, getCellValue(evaluator, cExpectedCell));
                    iCol++;
                    cHeaderCell = rHeaderRows.getCell(iCol);
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("getDataXLSX:: [%s] [%s] %s", xlsxName, sheetName, e.getMessage()));
            return null;
        }
        return dictTestDataRow;
    }

    /******************************************************
     * Get Test data XLSX
     * @param xlsxName : the xlsx file name
     * @param sheetName: the sheet name
     * @return List<Map < ColumnName, value>>
     */
    public static List<Map<String, String>> getDataXLSX(String xlsxName, String sheetName, Boolean getFullFile) {
        // Initiate dictionary to contain returned values
        List<Map<String, String>> dictTestData = new ArrayList<Map<String, String>>();
        // Get the first data only
        if (!getFullFile) {
            dictTestData.add(getDataXLSX(xlsxName, sheetName, 1));
            return dictTestData;
        }
        try {
            // Initiate column count variable
            int iCol = 0;

            // Initiate file input stream to read excel file
            FileInputStream fis = new FileInputStream(xlsxName);

            // Initiate excel workbook which contains the sheet
            Workbook wb = new XSSFWorkbook(fis);

            // Initiate sheet which contains the expected cell
            Sheet sheet = wb.getSheet(sheetName);

            // Initiate Formula Evaluator to evaluate if the expected cell contains a formular
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

            Row rHeaderRows = sheet.getRow(0);
            Cell cHeaderCell = rHeaderRows.getCell(0);
            List<String> listHeaders = new ArrayList<String>();
            while (cHeaderCell != null) {
                listHeaders.add(cHeaderCell.getStringCellValue());
                iCol++;
                cHeaderCell = rHeaderRows.getCell(iCol);
            }

            int rowCounts = sheet.getPhysicalNumberOfRows();
            for (int rowIndex = 1; rowIndex < rowCounts; rowIndex++) {
                // Initiate dictionary to contain returned values
                Map<String, String> dictTestDataRow = new HashMap<>();
                iCol = 0;
                for (String header : listHeaders) {
                    Row rExpectedRows = sheet.getRow(rowIndex);
                    Cell cExpectedCell = rExpectedRows.getCell(iCol);
                    dictTestDataRow.put(header, getCellValue(evaluator, cExpectedCell));
                    iCol++;
                }
                dictTestData.add(dictTestDataRow);
            }
        } catch (Exception e) {
            System.out.println(String.format("getDataXLSX:: [%s] [%s] %s", xlsxName, sheetName, e.getMessage()));
            return null;
        }
        return dictTestData;
    }

    public static String getDataXLSX(String filePath, String sheetName, String header, int index) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            // Find the row index based on the header
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found.");
            }

            int columnIndex = -1;
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();
                    if (cellValue.equals(header)) {
                        columnIndex = i;
                        break;
                    }
                }
            }

            if (columnIndex == -1) {
                throw new RuntimeException("Header not found: " + header);
            }

            // Get the value at the specified index in the matching row
            Row dataRow = sheet.getRow(index);
            if (dataRow == null) {
                return ""; // Handle row not found gracefully (empty string)
            }

            Cell dataCell = dataRow.getCell(columnIndex);
            String value = "";
            if (dataCell != null) {
                switch (dataCell.getCellType()) {
                    case STRING:
                        value = dataCell.getStringCellValue();
                        break;
                    case NUMERIC:
                        value = Double.toString(dataCell.getNumericCellValue());
                        break;
                    default:
                        // Handle other cell types (optional)
                        break;
                }
            }

            workbook.close();
            inputStream.close();
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}