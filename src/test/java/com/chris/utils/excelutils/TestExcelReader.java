package com.chris.utils.excelutils;

import static org.junit.Assert.assertFalse;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExcelReader
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestExcelReader.class);
    private static String fileToBeRead = "RequiredTools_20090907_0939.xls";

    // Create a work book reference
    private HSSFWorkbook workbook;

    @Before
    public void initialize() throws Exception
    {
	// Create a work book reference
	workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
    }

    @Test
    public void testRead() throws Exception
    {
	// Refer to the sheet. Put the Name of the sheet to be referred from
	// Alternative you can also refer the sheet by index using
	// getSheetAt(int index)
	HSSFSheet sheet = workbook.getSheetAt(0);
	// Reading the TOP LEFT CELL
	HSSFRow row = sheet.getRow(0);
	// Create a cell at index zero ( Top Left)
	HSSFCell cell = row.getCell(0);
	// Type the content
	LOGGER.info("THE TOP LEFT CELL--> " + cell.getStringCellValue());
	// Cell should not be empty
	assertFalse("Cell", cell.getStringCellValue().isEmpty());
    }

    @Test
    public void outputWorksheetNames() throws Exception
    {
	// Loop through the worksheets
	for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++)
	{
	    LOGGER.debug("Worksheet ({}): {}", sheetIndex, workbook.getSheetName(sheetIndex));
	}
    }

    @Test
    public void outputRowRange() throws Exception
    {
	HSSFSheet currSheet = null;
	String worksheetName;
	// Loop through the worksheets
	for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++)
	{
	    currSheet = workbook.getSheetAt(sheetIndex);
	    worksheetName = currSheet.getSheetName();
	    LOGGER.debug("Worksheet ({}) First Row: {}", worksheetName, currSheet.getFirstRowNum());
	    LOGGER.debug("Worksheet ({}) Last Row: {}", worksheetName, currSheet.getLastRowNum());
	}
    }
}
