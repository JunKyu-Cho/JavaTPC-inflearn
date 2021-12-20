package Project04_PDF;

import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Project04_B {

	public static void main(String[] args) {
		Document doc = new Document();
		try {
			FileOutputStream fos = new FileOutputStream("ParagraphTest.pdf");
			PdfWriter.getInstance(doc, fos);
			doc.open();

			String content = "Show Me The Money!";
			Paragraph pra1 = new Paragraph(32);
			pra1.setSpacingBefore(50);
			pra1.setSpacingAfter(50);
			
			for(int i = 0; i < 20; i++) {
				Chunk chunk = new Chunk(content);
				pra1.add(chunk);
			}
			doc.add(pra1);
			
			Paragraph pra2 = new Paragraph();
			for(int i = 0; i < 10; i++) {
				pra2.add(new Chunk(content));
			}
			doc.add(pra2);
			
			doc.close();
								
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
