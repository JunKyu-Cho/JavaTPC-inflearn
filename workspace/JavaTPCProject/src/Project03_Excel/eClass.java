package Project03_Excel;

import Project02_Crawling.DownloadBroker;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class eClass {
	private List<ExcelClass> list;
	private HSSFWorkbook wb;
	
	public eClass()	{
		list = new ArrayList<ExcelClass>();
		wb = new HSSFWorkbook();
	}
	
	public void addExcel() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			HSSFSheet sheet = wb.createSheet("Books");				
			HSSFRow row_0 = sheet.createRow(0);
			
			HSSFCell cell_0 = row_0.createCell(0);
			cell_0.setCellValue(new HSSFRichTextString("책 제목"));
			HSSFCell cell_1 = row_0.createCell(1);
			cell_1.setCellValue(new HSSFRichTextString("저자"));			
			HSSFCell cell_2 = row_0.createCell(2);
			cell_2.setCellValue(new HSSFRichTextString("출판사"));			
			HSSFCell cell_3 = row_0.createCell(3);
			cell_3.setCellValue(new HSSFRichTextString("isbn"));			
			HSSFCell cell_4 = row_0.createCell(4);
			cell_4.setCellValue(new HSSFRichTextString("이미지이름"));			
			HSSFCell cell_5 = row_0.createCell(5);
			cell_5.setCellValue(new HSSFRichTextString("이미지"));
			
			int i = 1;
			while(true) {
				System.out.print("책 제목 : ");
				String title = br.readLine();
				
				System.out.print("책 저자 : ");
				String author = br.readLine();
				
				System.out.print("출판사 : ");
				String company = br.readLine();
				
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell_title = row.createCell(0);
				cell_title.setCellValue(new HSSFRichTextString(title));
				HSSFCell cell_author = row.createCell(1);
				cell_author.setCellValue(new HSSFRichTextString(author));
				HSSFCell cell_company = row.createCell(2);
				cell_company.setCellValue(new HSSFRichTextString(company));
				
				i++;
				
				ExcelClass ex = new ExcelClass(title, author, company);
				
				SearchBook(ex);
				list.add(ex);
				
				System.out.println("계속입력 : Y / 입력종료 : N");
				String key = br.readLine();
				if(key.equals("N")) break;
			}
			
			System.out.println("데이터 추출 중.......");
			ExcelSave();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void ExcelSave() {
		try {
			HSSFSheet sheet = wb.getSheetAt(0);
			if(wb != null && sheet != null) {
				Iterator rows = sheet.rowIterator();
				rows.next();
				int i = 0;
				while(rows.hasNext()) {
					HSSFRow row = (HSSFRow) rows.next();
					HSSFCell cell = row.createCell(3);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(list.get(i).getIsbn());
					cell = row.createCell(4);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(list.get(i).getImgurl());
					
					InputStream iStream = new FileInputStream (list.get(i).getImgurl());
					byte[] bytes = IOUtils.toByteArray(iStream);
					int picIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
					iStream.close();
					
					CreationHelper helper = wb.getCreationHelper();
					Drawing drawing = sheet.createDrawingPatriarch();
					ClientAnchor anchor = helper.createClientAnchor();
					
					anchor.setCol1(5);		anchor.setCol2(6);	
					anchor.setRow1(i+1);	anchor.setRow2(i+2);
					
					Picture pic = drawing.createPicture(anchor, picIdx);
					Cell ImgCell = row.createCell(5);
					int width = 20 * 256;
					short height = 120 * 20;
					sheet.setColumnWidth(5, width);
					ImgCell.getRow().setHeight(height);
					i++;					
				}		
				FileOutputStream fos = new FileOutputStream("isbn.xls");
				wb.write(fos);
				fos.close();
				
				System.out.println("Excel 저장 성공");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	public ExcelClass SearchBook(ExcelClass ex) {
		try {
			// d_titl : 책 제목, d_auth : 저자, d_publ : 출판사 
			String strURL = "https://openapi.naver.com/v1/search/book_adv.xml"
					+ "?d_titl=" + URLEncoder.encode(ex.getTitle(), "UTF-8")
					+ "&d_auth=" + URLEncoder.encode(ex.getAuthor(), "UTF-8")
					+ "&d_publ=" + URLEncoder.encode(ex.getCompany(), "UTF-8");		
			
			URL url = new URL(strURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", "NS_1jo9R6tcj2nJwcLK_");
			con.setRequestProperty("X-Naver-Client-Secret", "ZChRefoiiW");
			
			BufferedReader br1 = null;
			int responsCode = con.getResponseCode();
			if(responsCode == 200) {
				br1 =  new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			}
			else {
				br1 =  new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer resp = new StringBuffer();
			while((inputLine = br1.readLine()) != null) {
				resp.append(inputLine);
			}
			br1.close();
			
			Document doc = Jsoup.parse(resp.toString());
			Element isbn = doc.select("isbn").first();
			String strisbn = isbn.text();
			strisbn = strisbn.split(" ")[1];
			ex.setIsbn(strisbn);
			
			// image 찾기			
			String strimg = doc.toString().substring(doc.toString().indexOf("<img>") + 5);	// <img> 이후 데이터만 저장
			strimg = strimg.substring(0, strimg.indexOf("?"));								// ? 이전 데이터만 저장
			String fName = strimg.substring(strimg.lastIndexOf("/") + 1);
			ex.setImgurl(fName);
			
			Runnable down = new DownloadBroker(strimg, fName);
			Thread th = new Thread(down);
			th.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ex;
	}

}
