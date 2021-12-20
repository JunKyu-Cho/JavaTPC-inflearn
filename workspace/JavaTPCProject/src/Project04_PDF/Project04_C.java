package Project04_PDF;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class Project04_C {

	public static void main(String[] args) {
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("AddImage.pdf"));
			doc.open();
			
			// �̹��� ���� �̿�
			String fName = "inflearn.png";
			Image image = Image.getInstance(fName);
			doc.add(image);
			
			// �̹��� Url �̿�
			String url = "https://cdn.inflearn.com/assets/images/main/board_girl.png";
			image = Image.getInstance(url);
			doc.add(image);
			
			
			// �̹��� ������ ����
			Image img = Image.getInstance("googlelogo.png");
			doc.add(img);
			
			img.scaleAbsolute(200f,  200f);	// W, H ����
			doc.add(img);
			
			Image imgurl = Image.getInstance("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
			doc.add(imgurl);
			
			imgurl.scalePercent(200f);	// ���� ����
			doc.add(imgurl);
			
			imgurl.scaleToFit(100f, 200f);	// W, H ���� (������ ������� ����)
			doc.add(imgurl);
			
			System.out.println("�����Ϸ�");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			doc.close();
		}

	}

}
