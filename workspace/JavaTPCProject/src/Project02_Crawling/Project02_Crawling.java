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
	private String[] day={"��","��","ȭ","��","��","��","��"};
	private JButton[] days = new JButton[42];			// 7*6 = 42���� ��ư�ʿ� (6��)
	private JPanel selectPanel = new JPanel();
	private GridLayout grid = new GridLayout(7,7,5,5);	//��,��,����,������
	private Calendar ca = Calendar.getInstance();
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	
	public static void main(String[] args) {
		new Project02_Crawling();
	}

	public Project02_Crawling() {
		
		// Calendar.MONTH : 1�� => 0
		setTitle("������ QT:"+ca.get(Calendar.YEAR)+"/"+(ca.get(Calendar.MONTH)+1)+"/"+ca.get(Calendar.DATE));		
		setSize(900,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		dimen1 = this.getSize();
		xpos = (int)(dimen.getWidth()/2 - dimen1.getWidth()/2);
		ypos = (int)(dimen.getHeight()/2 - dimen1.getHeight()/2);
		setLocation(xpos, ypos);//ȭ���� ����� ���
		setResizable(false);
		setVisible(true);
		chyear = new Choice(); chmonth = new Choice();
		yLabel = new JLabel("��"); mLabel = new JLabel("��");
		
		init();
	}

	public void init(){
		
		select();		
		calendar();
		
	}
	
	public void select() {
		
		JPanel panel =  new JPanel(grid);
		
		for(int i = 2021; i >= 2000; i--) {			
			chyear.add(String.valueOf(i));			// �� ���� �߰� (2000 ~ 2021��)
		}
		chyear.select(String.valueOf(ca.get(Calendar.YEAR)));
		
		for(int i = 1; i <= 12; i++) {			
			chmonth.add(String.valueOf(i));			// �� ���� �߰� (1 ~ 12��)
		}	
		chmonth.select(String.valueOf(ca.get(Calendar.MONTH)+1));

		for(int i = 0; i < day.length; i++) {		// ���� ���� �߰�	
			dayLabel[i] = new JLabel(day[i], JLabel.CENTER);
					
			if(i == 6)
				dayLabel[6].setBackground(Color.BLUE);		// ����� ����
			else if(i == 0)
				dayLabel[0].setBackground(Color.RED);		// �Ͽ��� ����
			else
				dayLabel[i].setBackground(Color.GRAY);		// ��~�� ����
			
			panel.add(dayLabel[i]);
		}
		
		
		
		for(int i = 0; i < 42; i++)	{				// 7*6 ���� (6��) ��ư ����
			days[i] = new JButton("");				// ������ ���� ��ư
			
			if(i % 7 == 0)
				days[i].setForeground(Color.RED);	// �Ͽ��� ��ư
			else if(i % 7 == 6)
				days[i].setForeground(Color.BLUE);	// ����� ��ư
			else
				days[i].setForeground(Color.BLACK);	// ��~�� ��ư
			
			days[i].addActionListener(this);		// �� ��ư Click Event
			panel.add(days[i]);						// �гο� ��ư �߰�
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
		
		chyear.addItemListener(this);		// Event �߰�
		chmonth.addItemListener(this);
	}

	public void calendar() {
		
		year = Integer.parseInt(chyear.getSelectedItem());					// Choice(�޺��ڽ�) �� ��
		month=Integer.parseInt(chmonth.getSelectedItem());  				// Choice(�޺��ڽ�) �� ��		
		gc = new GregorianCalendar(year, month-1, 1);						// ��, ��, �� ����
		
		int max = gc.getActualMaximum(gc.DAY_OF_MONTH);						// �ش� ���� �ִ� �� �� ȹ��
		int week = gc.get(gc.DAY_OF_WEEK);									// �ش� ���� ���� ���� (1:��, 7:��)

		String today = Integer.toString(ca.get(Calendar.DATE));				// ���� ��¥ ȹ��
		String today_month = Integer.toString(ca.get(Calendar.MONTH)+1);	// ������ �� ȹ��

		
		for(int i = 0; i < days.length; i++) {								// ��ü ��ư Ȱ��ȭ
			days[i].setEnabled(true);
		}
		
		for(int i = 0; i < week - 1; i++) {									//������ ������ ��ư�� ��Ȱ��ȭ
			days[i].setEnabled(false);
		}
		
		for(int i = week; i< max + week; i++) {								// 1��~��������  ���� ǥ�� �� �� ����							
			days[i-1].setText((String.valueOf(i-week+1)));					// ���� ǥ�� 
			days[i-1].setBackground(Color.WHITE);							// ��ư ��� ����
			
			if(today_month.equals(String.valueOf(month))){					// ������ ���� �ް� ���� ���� ���
				if(today.equals(days[i-1].getText())){						// ��ư�� ��¥�� ���ó�¥�� ��ġ�ϴ� ���
					days[i-1].setBackground(Color.CYAN);					// ��ư�� ���� ����
				}
			}
		}
		for(int i = (max+week-1); i < days.length; i++){					// �ִ� ���� ���� (��¥�� ���� ��ư) ��Ȱ��ȭ
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
