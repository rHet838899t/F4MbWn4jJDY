// 代码生成时间: 2025-09-29 15:57:45
import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
    import org.apache.poi.ss.util.CellRangeAddress;

    import java.io.*;
    import java.util.List;
    import java.util.ArrayList;

    /**
     * ExcelAutoGenerator class is responsible for generating Excel files.
     */
    public class ExcelAutoGenerator {

        /**
         * Generates an Excel file with the given data.
         *
         * @param dataList The list of data to be written in the Excel file.
         * @param sheetName The name of the sheet in the Excel file.
         * @param fileName The name of the Excel file to be generated.
         * @throws IOException If an I/O error occurs.
         */
        public void generateExcel(List<List<String>> dataList, String sheetName, String fileName) throws IOException {
            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // Create a new sheet
            Sheet sheet = workbook.createSheet(sheetName);

            // Create a list to store row numbers for freezing pane
            List<Integer> mergeList = new ArrayList<>();

            // Iterate over the data and write to the sheet
            int rowNum = 0;
            for (List<String> dataRow : dataList) {
                Row row = sheet.createRow(rowNum++);

                int columnNum = 0;
                for (String cellData : dataRow) {
                    Cell cell = row.createCell(columnNum++);
                    cell.setCellValue(cellData);
                }

                // If it's the first row, freeze the top row
                if (rowNum == 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, Short.MAX_VALUE, 0, dataRow.size() - 1));
                    mergeList.add(0);
                }
            }

            // Set the first row as header and freeze the pane
            if (!mergeList.isEmpty()) {
                sheet.createFreezePane(0, 1, 0, 1);
            }

            // Write the workbook to the file system
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }

            // Close the workbook
            workbook.close();
        }

        /**
         * Reads data from a list and calls the generateExcel method.
         * This is a sample method to demonstrate how to use ExcelAutoGenerator.
         *
         * @param args Command line arguments (not used in this example).
         */
        public static void main(String[] args) {
            ExcelAutoGenerator generator = new ExcelAutoGenerator();

            // Sample data for demonstration purposes
            List<List<String>> sampleData = new ArrayList<>();
            sampleData.add(List.of(