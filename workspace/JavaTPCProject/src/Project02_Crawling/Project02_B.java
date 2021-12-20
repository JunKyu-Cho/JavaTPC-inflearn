package Project02_Crawling;

import java.io.*;
import java.net.URL;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Project02_B {

	public static void main(String[] args) {

		// 조건 조회를 이용한 Crawling 
		
		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BodyMatter?qt_ty=QT1";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			System.out.print("[입력->년(yyyy)-월(mm)-일(dd)]");
			String bible = br.readLine();
			url = url + "&base_de=" + bible + "&bibleType=1";
			System.out.println("=============================");
			
			Document doc = Jsoup.connect(url).post();
			Element bible_text = doc.select(".bible_text").first();
			System.out.println(bible_text.text());			
			
			Element bibleinfo_box = doc.select(".bibleinfo_box").first();
			System.out.println(bibleinfo_box.text());
			
			Elements liList = doc.select(".body_list > li");
			for(Element li : liList) {
				System.out.print(li.select(".num").first().text() + '\t');
				System.out.println(li.select(".info").first().text());
			}
			
			//Resource Download (mp3, image);
			Element tag = doc.select("source").first();
			String mp3Path = tag.attr("src").trim();
			System.out.println(mp3Path);			
			String mp3fName = mp3Path.substring(mp3Path.lastIndexOf("/") + 1);
			
			tag = doc.select(".img > img").first();
			String imagePath = "https://sum.su.or.kr:8888" + tag.attr("src").trim();
			System.out.println(imagePath);			
			String imagefName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
			
//			Runnable r = new DownloadBroker(imagePath, imagefName);
			Runnable r = new DownloadBroker(mp3Path, mp3fName);
			Thread dLoad = new Thread(r);
			dLoad.start();
			
			for(int i=0; i<10; i++) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.print("" + (i + 1));
			}
			
			System.out.println();
			System.out.println("==========================");
			

		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

}

