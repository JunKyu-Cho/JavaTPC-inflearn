package Project03_Excel;

import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class Project03_A {

	public static void main(String[] args)
	{		
		// 엑셀 데이터 읽어오기
		String fName = "bookList.xls";
		List<ExcelClass> data = new ArrayList<ExcelClass>();
		
		try(FileInputStream fis = new FileInputStream(fName)) {
			HSSFWorkbook workboos = new HSSFWorkbook(fis); 	// Excel 데이터
			HSSFSheet sheet = workboos.getSheetAt(0);		// Sheet 데이터
			Iterator<Row> rows = sheet.rowIterator();		// Row 데이터 
			
			rows.next();									// 첫줄 건너뛰기 (Field Name)
			while(rows.hasNext()) {							// Row 존재 여부 확인
				HSSFRow row = (HSSFRow)rows.next();
				Iterator<Cell> cells = row.cellIterator();
				
				String[] imsi = new String[(int)row.getLastCellNum()];
				int i = 0;
				while(cells.hasNext())
				{
					HSSFCell cell = (HSSFCell)cells.next();
					imsi[i++] = cell.toString();					
				}
				
				//ExcelClass excel = new ExcelClass(imsi[0], imsi[1], imsi[2], imsi[3], imsi[4]);
				ExcelClass excel = new ExcelClass(imsi);
				data.add(excel);
			}
			
			showExcelData(data);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public static void showExcelData(List<ExcelClass> data) {
		for(ExcelClass excel : data) {
			System.out.println(excel);
		}
	}
	
}
