package Project03_Excel;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class Project03_C {
	public static void main(String[] args) {
		// ¿¢¼¿ Data Type
		String fName = "cellDataType.xls";
		try(FileInputStream fis = new FileInputStream(fName)) {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			
			while(rows.hasNext()) {
				HSSFRow row = (HSSFRow)rows.next();
				Iterator<Cell> cells = row.cellIterator();
				while(cells.hasNext()) {
					HSSFCell cell = (HSSFCell)cells.next();
					CellType type = cell.getCellType();
					switch(type) {
					
					case STRING:
						System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] = STRING; Value " + cell.getRichStringCellValue().toString());
						break;
						
					case NUMERIC:
						System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] = NUMERIC; Value " + cell.getNumericCellValue());
						break;
						
					case BOOLEAN:
						System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] = BOOLEAN; Value " + cell.getBooleanCellValue());
						break;
						
					case BLANK:
						System.out.println("[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "] = BLANK CELL");
						break;		
					}
				}
				
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
	}
}
