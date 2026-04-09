package utilities;




/*
*
* ExcelUtils- Handles reading and writing Excel data
* Design improvement over basic POI:
* -->Uses header based mapping (no index confusion)
* --> supports both read and write(for reporting)
* -->clean and reusable for framework
*
* */


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;
    private String filePath;
    DataFormatter formatter = new DataFormatter();



//    constructor to load Excel file once
    public ExcelUtils(String filePath, String sheetName)
    {
        this.filePath = filePath;
        try(FileInputStream fis = new FileInputStream(filePath))
        {
//            open the workbook
            workbook = new XSSFWorkbook(fis);
//            get the sheet name
            sheet = workbook.getSheet(sheetName);

            if(sheet==null)
            {
                throw new RuntimeException("sheet ' "+sheetName+" ' does not exist in the file :"+filePath);
            }


        } catch (IOException e) {
            throw new RuntimeException("Error in loading Excel file : "+e.getMessage());
        }



    }

//    read row data and return Map<columns, value>
    public Map<String,String> getTestData(int rowIndex){

    Map<String,String > data = new HashMap<>();
//    get the header row
        Row headerRow = sheet.getRow(0);

        if(headerRow==null)
        {
            throw new RuntimeException("Header row is missing in Excel file.");
        }
//        get actual data row

        Row row = sheet.getRow(rowIndex);


        if(row==null)
        {
            throw new RuntimeException("Row "+rowIndex+" is empty or does not exists");

        }

        for(int i =0; i<headerRow.getLastCellNum(); i++)
        {
            String key =  formatter.formatCellValue(headerRow.getCell(i)).trim();

            Cell cell = row.getCell(i);

            String value = formatter.formatCellValue(cell).trim();


            data.put(key,value);




        }


return data;


    }

//get row count
    public int getRowCount(){

        return  sheet.getPhysicalNumberOfRows();

    }


//    get column count
    public  int getColCount(){

        Row headerRow =  sheet.getRow(0);
        if(headerRow==null)
        {
            throw new RuntimeException("Header row is missing");
        }
        return headerRow.getPhysicalNumberOfCells();


    }


//    get cell data(row,column)

    public String getCellData(int rowNum, int colNum)
    {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);


        return (cell==null)?"":formatter.formatCellValue(cell);

    }


//    write results(PASS/FAIL) into Excel
    public void setCellData(int rowIndex, int colIndex, String value)
    {
        try{
            Row row = sheet.getRow(rowIndex);
            if(row == null)
            {
                row = sheet.createRow(rowIndex);
            }
            Cell cell = row.getCell(colIndex);

            if(cell==null)
            {
                cell = row.createCell(colIndex);
            }


            cell.setCellValue(value);

//            write back to file

            try(FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }



        } catch (IOException e) {
            throw new RuntimeException("could not write data to Excel : "+e.getMessage());
        }
    }


//    get Row index

    public int getRowIndex(String testCaseName)
    {

        for(int i = 1; i<getRowCount(); i++)
        {
            if(getCellData(i,0).equalsIgnoreCase(testCaseName))
            {
                return  i;
            }
        }
return -1;

    }


//    close workbook

    public void close(){


        try{
            workbook.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }



    }
}
