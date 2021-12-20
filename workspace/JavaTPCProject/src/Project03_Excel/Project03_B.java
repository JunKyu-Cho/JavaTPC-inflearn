package Project03_Excel;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

public class Project03_B {

	public static void main(String[] args) {
		// ������ �̹��� ����
		try {
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet("Sample Sheet");
			
			InputStream is = new FileInputStream("PIC.jpg");
			byte[] bytes = IOUtils.toByteArray(is);
			
			int pictureId = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			
			// Cell ��ġ ����
			anchor.setCol1(1);
			anchor.setRow1(2);
			anchor.setCol2(2);
			anchor.setRow2(3);
			
			Picture pic = drawing.createPicture(anchor, pictureId);	// anchor ��ġ�� �̹��� �׸�
			
			Cell cell = sheet.createRow(2).createCell(1);
			int w = 200 * 128, h = 240 * 20;
			
			sheet.setColumnWidth(1, w);			// Column ���� ����
			cell.getRow().setHeight((short)h);	// Row ���� ����
			
			FileOutputStream fileout = new FileOutputStream("myFile.xls");			
			wb.write(fileout);
			fileout.close();
			
			System.out.println("�̹��� ���� �Ϸ�");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
