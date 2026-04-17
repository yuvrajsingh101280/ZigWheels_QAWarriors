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



//this constructor is using to create dynamic sheets
    public ExcelUtils(String filePath) {
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error loading Excel file: " + e.getMessage());
        }
    }



    //    dynamically create sheet name for used car
    public void switchOrCreateSheetForCar(String sheetName)
    {
        Sheet newSheet = workbook.getSheet(sheetName);
        if(newSheet==null)
        {
            newSheet= workbook.createSheet(sheetName);
        }

//        create header row

        Row headerRow = newSheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Popular Model");

        this.sheet = newSheet;
    }




//        dynamically create sheet name for upcoming bikes
    public void switchOrCreateSheetForUpcomingBikes(String sheetName)
    {
        Sheet newSheet = workbook.getSheet(sheetName);
        if(newSheet==null)
        {
            newSheet = workbook.createSheet(sheetName);
        }

//        create header row
        Row headerRow  = newSheet.createRow(0);

        headerRow.createCell(0).setCellValue("Bike Name");
        headerRow.createCell(1).setCellValue("Price");
        headerRow.createCell(2).setCellValue("Expected Launch Date");
    this.sheet = newSheet;


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
