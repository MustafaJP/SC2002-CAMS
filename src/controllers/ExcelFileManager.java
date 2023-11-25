package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class handles operations related to reading from and writing to Excel files.
 * It uses Apache POI library to interact with Excel files (XLSX format), allowing for the extraction and
 * manipulation of data contained in these files.
 */
public class ExcelFileManager {
	/**
	 * Reads data from an Excel file and returns it as a list of lists, where each inner list represents a row.
	 *
	 * @param filePath The path of the Excel file to read from.
	 * @return A list of lists, where each inner list contains the data from one row of the Excel file.
	 * @throws IOException If there is an issue in reading the file.
	 */
	public static ArrayList<ArrayList<Object>> readExcel(String filePath) throws IOException {
		File file = new File(filePath);
		try {
			FileInputStream inputStream = new FileInputStream(file);

			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<ArrayList<Object>> fullList = new ArrayList<ArrayList<Object>>();
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook tempWorkbook = new XSSFWorkbook(inputStream);
		try {
			for (Sheet sheet : tempWorkbook) {

				int firstRow = sheet.getFirstRowNum();
				int lastRow = sheet.getLastRowNum();
				for (int index = firstRow + 1; index <= lastRow; index++) {
					Row row = sheet.getRow(index);

					ArrayList<Object> rowWise = new ArrayList<Object>();
					for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
						Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

						extractCellValue(cell, rowWise);

					}
					fullList.add(rowWise);

				}
			}
		} finally {
			tempWorkbook.close();
		}

		return fullList;
	}
	/**
	 * Extracts the value from a cell and adds it to the given list.
	 *
	 * @param cell   The cell to extract the value from.
	 * @param result The list to which the cell value is added.
	 */
	public static void extractCellValue(Cell cell, ArrayList<Object> result) {

		CellType cellType = cell.getCellType().equals(CellType.FORMULA) ? cell.getCachedFormulaResultType()
				: cell.getCellType();
		if (cellType.equals(CellType.STRING)) {

			result.add(cell.getStringCellValue());
		}
		if (cellType.equals(CellType.NUMERIC)) {
			if (DateUtil.isCellDateFormatted(cell)) {

				result.add(cell.getDateCellValue());
			} else {

				result.add(cell.getNumericCellValue());
			}
		}
		if (cellType.equals(CellType.BOOLEAN)) {
			System.out.print(cell.getBooleanCellValue() + " | ");
			result.add(cell.getBooleanCellValue());
		}
	}
	/**
	 * Writes a list of lists to an Excel file, where each inner list is written as a row in the Excel file.
	 *
	 * @param toBeWrittenList The data to write to the Excel file.
	 * @param excelFilePath   The path of the Excel file to write to.
	 */
	public static void writeExcel(ArrayList<ArrayList<Object>> toBeWrittenList, String excelFilePath) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum(); 

			for (int i = rowCount; i > 0; i--) {
				Row row = sheet.getRow(i);
				sheet.removeRow(row);
			}

			rowCount = 1; 

			for (ArrayList<Object> entry : toBeWrittenList) {
				Row row = sheet.createRow(rowCount); 

				int columnCount = 0;

				for (Object field : entry) {
					Cell cell = row.createCell(columnCount);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					} else if (field instanceof Date) {
						cell.setCellValue(dateToString(((Date) field)));
					}
					columnCount++;
				}

				rowCount++; 
			}

			inputStream.close();

			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		} catch (IOException | EncryptedDocumentException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Writes a list of lists to specific columns of an existing Excel file.
	 * 
	 * @param toBeWrittenList The data to write to the Excel file.
	 * @param excelFilePath   The path of the Excel file to write to.
	 * @param cols            A list of integers representing the columns to which the data will be written.
	 */
	public static void writeExcel(ArrayList<ArrayList<Object>> toBeWrittenList, String excelFilePath,
			ArrayList<Integer> cols) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);
			int rownum = 1;
			for (ArrayList<Object> entry : toBeWrittenList) {
				Row row = sheet.getRow(rownum); 

				int columnCount = cols.get(0);

				for (Object field : entry) {

					Cell cell = row.getCell(columnCount);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					} else if (field instanceof Date) {
						cell.setCellValue(dateToString((Date) field));
					}

					columnCount++;
				}
				rownum++;
			}

			inputStream.close();

			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		} catch (IOException | EncryptedDocumentException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Converts a Date object to a string in the format dd/MM/yyyy.
	 *
	 * @param d The Date object to convert.
	 * @return A string representation of the Date in dd/MM/yyyy format.
	 */
	public static String dateToString(Date d) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String dateString = dateFormat.format(d);
		return dateString;
	}

}
