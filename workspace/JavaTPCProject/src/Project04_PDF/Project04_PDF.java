package Project04_PDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Project03_Excel.ExcelClass;

public class Project04_PDF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadExcel();

	}

	private static void ReadExcel() {
		// 엑셀 데이터 읽어오기
		String fName = "isbn.xls";
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
					
					if(i==5)	break;
				}
				
				//ExcelClass excel = new ExcelClass(imsi[0], imsi[1], imsi[2], imsi[3], imsi[4]);
				ExcelClass excel = new ExcelClass(imsi);
				data.add(excel);
			}
			
			MakePDF(data);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void MakePDF(List<ExcelClass> data) {
		String[] headers = new String[] {"제목", "저자", "출판사", "이미지"};
		Document doc = new Document(PageSize.A4);
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(new File("bookList.pdf")));
			doc.open();
			
			BaseFont bFont = BaseFont.createFont("MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font hFont = new Font(bFont, 12);	// Header
			Font rFont = new Font(bFont, 10);	// Row
			
			PdfPTable table = new PdfPTable(headers.length);
			for(String header : headers) {
				PdfPCell cell = new PdfPCell();
				cell.setGrayFill(0.9f);
				cell.setPhrase(new Phrase(header.toUpperCase(), hFont));
				table.addCell(cell);
			}
			table.completeRow();
			
			for(ExcelClass vo : data) {
				Phrase phrase = new Phrase(vo.getTitle(), rFont);
				table.addCell(new PdfPCell(phrase));
				
				phrase = new Phrase(vo.getAuthor(), rFont);
				table.addCell(new PdfPCell(phrase));
				
				phrase = new Phrase(vo.getCompany(), rFont);
				table.addCell(new PdfPCell(phrase));
				
				Image img = Image.getInstance(vo.getImgurl());
				table.addCell(img);
				
				table.completeRow();
			}
			
			doc.addTitle("PDF Table");
			doc.add(table);
			
			System.out.println("PDF 생성 완료");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doc.close();
		}
		
	}

}
