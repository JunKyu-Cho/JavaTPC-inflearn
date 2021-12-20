package Project04_PDF;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Project01_A {

	public static void main(String[] args) {
		// iText API (PDS)
		String[] title = new String[] {"����", "����", "���ǻ�", "�̹���URL"};
		String[][] rows = new String[][] {
			{"������Ģ�� ����", "��ó�� ���θ�", "�س���", "A.jpg"},
			{"Java�� ����", "���ü�", "��������", "B.jpg"},
			{"������ ���α׷���", "â����", "��������", "C.jpg"}
		};
		
		Document doc = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(new File("book.pdf")));
			doc.open();
			
			
			BaseFont bf = BaseFont.createFont("MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);	// �ѱ� ��Ʈ�� ���� ����
			Font FontTitle = new Font(bf, 12);
			Font FontRows = new Font(bf, 10);
			
			PdfPTable table = new PdfPTable(title.length);
			table.setWidthPercentage(100);

			float[] ColWidth = new float[] {20f, 15f, 15f, 30f};
			table.setWidths(ColWidth);
			
			for(String header : title) {
				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(10);
				cell.setGrayFill(0.9f);
				cell.setPhrase(new Phrase(header, FontTitle));
								
				table.addCell(cell);
			}
			table.completeRow();
			
			for(String[] row : rows) {
				for(String data : row) {
					Phrase phrase = new Phrase(data, FontRows);
					PdfPCell cell = new PdfPCell(phrase);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingTop(20);
					cell.setPaddingBottom(20);
					cell.setPaddingRight(30);
					cell.setPaddingLeft(30);
					
					table.addCell(cell);
				}
				table.completeRow();
			}  
			
			PdfPCell cell4 = new PdfPCell(new Phrase("Cell 5"));
			cell4.setColspan(2);
			PdfPCell cell5 = new PdfPCell(new Phrase("Cell 6"));
			cell5.setColspan(2);
			
			table.addCell(cell4);
			table.addCell(cell5);
			
			doc.addTitle("PDF Table Test");
			doc.add(table);
			System.out.println("PDF ���� ���� �Ϸ�");		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doc.close();
		}
	}

}
