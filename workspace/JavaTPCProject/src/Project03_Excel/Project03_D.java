package Project03_Excel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kr.inflearn.ObjBook;


public class Project03_D {

	public static void main(String[] args) {
		// ���̹� API �̿� �������� �˻� (ISBN, Image)

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("å ���� : ");
			String title = br.readLine();
			
			System.out.print("å  ���� : ");
			String author = br.readLine();
			
			System.out.print("���ǻ� : ");
			String company = br.readLine();
			
			ExcelClass book = new ExcelClass(title, author, company);
			getIsbnImage(book);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getIsbnImage(ExcelClass book) {
		
		try {
			// d_titl : å ����, d_auth : ����, d_publ : ���ǻ� 
			String strURL = "https://openapi.naver.com/v1/search/book_adv.xml"
					+ "?d_titl=" + URLEncoder.encode(book.getTitle(), "UTF-8")
					+ "&d_auth=" + URLEncoder.encode(book.getAuthor(), "UTF-8")
					+ "&d_publ=" + URLEncoder.encode(book.getCompany(), "UTF-8");		
			
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
			
//			System.out.println(resp.toString());
			Document doc = Jsoup.parse(resp.toString());
//			System.out.println(doc.toString());
			
			// �˻� ��� Ȯ��
			Element total = doc.select("total").first();
			if(!total.text().equals("0")) {
				// isbn ã��
				Element isbn = doc.select("isbn").first();
				String strisbn = isbn.text();
				strisbn = strisbn.split(" ")[1];
				book.setIsbn(strisbn);
				
				// image ã��			
				String strimg = doc.toString().substring(doc.toString().indexOf("<img>") + 5);	// <img> ���� �����͸� ����
				strimg = strimg.substring(0, strimg.indexOf("?"));								// ? ���� �����͸� ����
				String fName = strimg.substring(strimg.lastIndexOf("/") + 1);
				book.setImgurl(fName);
				
			}else {
				System.out.println("�˻� ������ ����");
			}
			
			System.out.println(book.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
