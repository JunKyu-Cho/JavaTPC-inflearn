package Project02_Crawling;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_Crawling extends JFrame implements ActionListener, ItemListener {

	private Choice chyear, chmonth;
	private JLabel yLabel, mLabel;
	private JTextArea area;
	GregorianCalendar gc;
	private int year, month;
	private JLabel[] dayLabel = new JLabel[7];
	private String[] day={"일","월","화","수","목","금","토"};
	private JButton[] days = new JButton[42];			// 7*6 = 42개의 버튼필요 (6주)
	private JPanel selectPanel = new JPanel();
	private GridLayout grid = new GridLayout(7,7,5,5);	//행,열,수평갭,수직갭
	private Calendar ca = Calendar.getInstance();
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	
	public static void main(String[] args) {
		new Project02_Crawling();
	}

	public Project02_Crawling() {
		
		// Calendar.MONTH : 1월 => 0
		setTitle("오늘의 QT:"+ca.get(Calendar.YEAR)+"/"+(ca.get(Calendar.MONTH)+1)+"/"+ca.get(Calendar.DATE));		
		setSize(900,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		dimen1 = this.getSize();
		xpos = (int)(dimen.getWidth()/2 - dimen1.getWidth()/2);
		ypos = (int)(dimen.getHeight()/2 - dimen1.getHeight()/2);
		setLocation(xpos, ypos);//화면의 가운데에 출력
		setResizable(false);
		setVisible(true);
		chyear = new Choice(); chmonth = new Choice();
		yLabel = new JLabel("년"); mLabel = new JLabel("월");
		
		init();
	}

	public void init(){
		
		select();		
		calendar();
		
	}
	
	public void select() {
		
		JPanel panel =  new JPanel(grid);
		
		for(int i = 2021; i >= 2000; i--) {			
			chyear.add(String.valueOf(i));			// 년 선택 추가 (2000 ~ 2021년)
		}
		chyear.select(String.valueOf(ca.get(Calendar.YEAR)));
		
		for(int i = 1; i <= 12; i++) {			
			chmonth.add(String.valueOf(i));			// 월 선택 추가 (1 ~ 12월)
		}	
		chmonth.select(String.valueOf(ca.get(Calendar.MONTH)+1));

		for(int i = 0; i < day.length; i++) {		// 요일 정보 추가	
			dayLabel[i] = new JLabel(day[i], JLabel.CENTER);
					
			if(i == 6)
				dayLabel[6].setBackground(Color.BLUE);		// 토요일 색상
			else if(i == 0)
				dayLabel[0].setBackground(Color.RED);		// 일요일 색상
			else
				dayLabel[i].setBackground(Color.GRAY);		// 월~금 색상
			
			panel.add(dayLabel[i]);
		}
		
		
		
		for(int i = 0; i < 42; i++)	{				// 7*6 개의 (6주) 버튼 생성
			days[i] = new JButton("");				// 제목이 없는 버튼
			
			if(i % 7 == 0)
				days[i].setForeground(Color.RED);	// 일요일 버튼
			else if(i % 7 == 6)
				days[i].setForeground(Color.BLUE);	// 토요일 버튼
			else
				days[i].setForeground(Color.BLACK);	// 월~금 버튼
			
			days[i].addActionListener(this);		// 각 버튼 Click Event
			panel.add(days[i]);						// 패널에 버튼 추가
		}
		
		selectPanel.add(chyear);
		selectPanel.add(yLabel);
		selectPanel.add(chmonth);
		selectPanel.add(mLabel);
		
		area = new JTextArea(60, 40);
		area.setCaretPosition(area.getDocument().getLength());
		JScrollPane scrollPane = new JScrollPane(area);
		this.add(selectPanel, "North");
		this.add(panel, "Center");
		this.add(scrollPane, "East");		
		
		chyear.addItemListener(this);		// Event 추가
		chmonth.addItemListener(this);
	}

	public void calendar() {
		
		year = Integer.parseInt(chyear.getSelectedItem());					// Choice(콤보박스) 년 값
		month=Integer.parseInt(chmonth.getSelectedItem());  				// Choice(콤보박스) 월 값		
		gc = new GregorianCalendar(year, month-1, 1);						// 년, 월, 일 설정
		
		int max = gc.getActualMaximum(gc.DAY_OF_MONTH);						// 해당 달의 최대 일 수 획득
		int week = gc.get(gc.DAY_OF_WEEK);									// 해당 달의 시작 요일 (1:일, 7:토)

		String today = Integer.toString(ca.get(Calendar.DATE));				// 오늘 날짜 획득
		String today_month = Integer.toString(ca.get(Calendar.MONTH)+1);	// 오늘의 달 획득

		
		for(int i = 0; i < days.length; i++) {								// 전체 버튼 활성화
			days[i].setEnabled(true);
		}
		
		for(int i = 0; i < week - 1; i++) {									//시작일 이전의 버튼을 비활성화
			days[i].setEnabled(false);
		}
		
		for(int i = week; i< max + week; i++) {								// 1일~마지막일  일자 표시 및 색 변경							
			days[i-1].setText((String.valueOf(i-week+1)));					// 일자 표시 
			days[i-1].setBackground(Color.WHITE);							// 버튼 배경 변경
			
			if(today_month.equals(String.valueOf(month))){					// 오늘이 속한 달과 같은 달인 경우
				if(today.equals(days[i-1].getText())){						// 버튼의 날짜와 오늘날짜가 일치하는 경우
					days[i-1].setBackground(Color.CYAN);					// 버튼의 배경색 지정
				}
			}
		}
		for(int i = (max+week-1); i < days.length; i++){					// 최대 일자 이후 (날짜가 없는 버튼) 비활성화
				days[i].setEnabled(false);
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		Color color = this.getBackground();
		if(e.getStateChange() == ItemEvent.SELECTED) {
			for(int i = 0; i < 42; i++) {
				days[i].setText("");
				days[i].setBackground(color);
			}
			
			calendar();
		}				
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		area.setText("");
		
		String year = chyear.getSelectedItem();
		String month = chmonth.getSelectedItem();
		JButton btn = (JButton)e.getSource();
		String day = btn.getText();
				
		Run(year+"-"+month+"-"+day);	

		
	}
	
	public void Run(String bible) {
		
		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BodyMatter?qt_ty=QT1&base_de=" + bible + "&bibleType=1&SearchTxt1=Calder";
		try {			
			Document doc = Jsoup.connect(url).post();
			Element bible_text = doc.select(".bible_text").first();			
			Element bibleinfo_box = doc.select(".bibleinfo_box").first();			
			Element dailybible_info = doc.select(".dailybible_info").first();

			area.append(dailybible_info.text()+"\n");
			area.append(bible_text.text()+"\n");
			area.append(bibleinfo_box.text()+"\n");
			
			Elements liList = doc.select(".body_list > li");
			for(Element li : liList) {
				String line = li.select(".info").text();
				
				if (line.length() > 90)
					line = line.substring(0, 30) + "\n" + line.substring(30, 60) + "\n" + line.substring(60, 90) + "\n" + line.substring(90) + "\n";
				else if(line.length() > 60)
					line = line.substring(0, 30) + "\n" + line.substring(30, 60) + "\n" + line.substring(60) + "\n";
				else if(line.length() > 30)
					line = line.substring(0, 30) + "\n" + line.substring(30) + "\n";
				else
					line += "\n";
				
				area.append(li.select(".num").first().text() + " : " + line);
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
}
