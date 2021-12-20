package Project01_NaverMap;


import java.io.*;
import java.net.*;

import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.*;

public class Project01_NaverMap {

	static JTextField address;
	static JLabel resAddress, resX, resY, jibunAddress, imageLabel;
	
	public static void main(String[] args) {
		
		new Project01_NaverMap().initGUI();
		
	}
	
	public static JSONArray geoCode_Service(String addr) {
	
		String clientId = "dnn0xl9nq6";
		String clientSecret = "xI7nDzFr4W2eTcwwk2uT854hS5ex1TIX5kggz2vS";
		
		JSONArray arr = null;
		
		try {			
			
			String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
			URL url = new URL(apiURL);	// ���� URL Ȯ��
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

			BufferedReader br;
			if(con.getResponseCode() == 200) {	//�����ڵ�				
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			
//			System.out.println(object.toString(2));
			
			String str = (String)object.get("status");
			arr = object.getJSONArray("addresses");			
				
		} catch (Exception e) {
			System.out.print(e);
		}
		
		return arr;
		
	}
	
	public static void staticMap_Service(String point_x, String point_y, String address, String jibun) {
		
		String clientId = "dnn0xl9nq6";
		String clientSecret = "xI7nDzFr4W2eTcwwk2uT854hS5ex1TIX5kggz2vS";
		
		try {
	
			String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
			
			String pos = URLEncoder.encode(point_x + " " + point_y, "UTF-8");
			String strurl = URL_STATICMAP;
			strurl += "center=" + point_x + "," + point_y;
			strurl += "&level=16&w=700&h=500";
			strurl += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(address, "UTF-8");
			
			URL url = new URL(strurl);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
			
			BufferedReader br;
			if(con.getResponseCode() == 200) {	//�����ڵ�		
			
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				String ImgName = Long.valueOf(new Date().getTime()).toString();
				File fName = new File(ImgName + ".jpg");
				fName.createNewFile();
				
				OutputStream outStream = new FileOutputStream(fName);
				while((read = is.read(bytes)) != -1)
				{
					outStream.write(bytes, 0, read);
				}
				
				ImageIcon img = new ImageIcon(fName.getName());
						
				imageLabel.setIcon(img);	
				resAddress.setText(address);
				jibunAddress.setText(jibun);
				resX.setText(point_x);
				resY.setText(point_y);
				
				if(fName.exists())
					fName.delete();
				
			} else {
				
			}
		

		} catch (Exception e) {
			System.out.print(e);
		}
				
		return;
	}
	
	public void initGUI() {
			
		JFrame frm=new JFrame("Map View");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frm.getContentPane();
		imageLabel = new JLabel("��������");
		JPanel pan=new JPanel();
		JLabel addressLbl = new JLabel("�ּ��Է�");
		address=new JTextField(50);
		JButton btn=new JButton("Ŭ��");
		pan.add(addressLbl);
		pan.add(address);
		pan.add(btn);
		btn.addActionListener(new NaverMap(this));
		JPanel pan1=new JPanel();
		pan1.setLayout(new GridLayout(4, 1));
		resAddress = new JLabel("���θ�");
		jibunAddress = new JLabel("�����ּ�");
		resX = new JLabel("�浵");
		resY = new JLabel("����");
		pan1.add(resAddress);
		pan1.add(jibunAddress);
		pan1.add(resX);
		pan1.add(resY);

		c.add(BorderLayout.NORTH, pan);
		c.add(BorderLayout.CENTER, imageLabel);
		c.add(BorderLayout.SOUTH, pan1);
		frm.setSize(730,660);
		frm.setVisible(true);

	}

}

class NaverMap implements ActionListener {
	
	Project01_NaverMap mClass;

	public NaverMap(Project01_NaverMap main_Class){
		this.mClass = main_Class;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String address = null;
		String addr = null;
		
		try {
			
			address = mClass.address.getText();
			addr = URLEncoder.encode(address, "UTF-8");
			
			// ���̹� GeoCode�� �̿��� �ּ� ã��
			JSONArray arr = mClass.geoCode_Service(addr);
			JSONObject temp = null;
			for(int i=0; i<arr.length(); i++)
			{
				temp = (JSONObject)arr.get(i);
			}
			
			// ���̹� StaticMap�� �̿��� Image ����
			mClass.staticMap_Service((String)temp.get("x"), (String)temp.get("y"), (String)temp.get("roadAddress"), (String)temp.getString("jibunAddress"));
			
		} catch(Exception ex) {
			
			System.out.print(ex);
			
		}		
		
	}
	
}

