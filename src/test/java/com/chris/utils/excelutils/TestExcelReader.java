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
    private static String fileToBeRead =
	    "\\\\daytonfile\\users$\\cjmcmill\\My Documents\\GIAT\\Dependencies.xls";

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
	HSSFSheet sheet = workbook.getSheet("Dependecies");
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
	// Create a work book reference
	HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
	// Loop through the worksheets
	for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++)
	{
	    LOGGER.debug("Worksheet ({}): {}", sheetIndex, workbook.getSheetName(sheetIndex));
	}
    }
}
