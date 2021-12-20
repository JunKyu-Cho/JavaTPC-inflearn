package Project03_Excel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Project03_Excel {

	public static void main(String[] args) {
		eClass excel = new eClass();		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("입력처리(I)/종료(E) : ");
			String sw = br.readLine();
			switch(sw) {
			case "I":
				excel.addExcel();
				break;
			case "E":
				System.exit(0);
				break;
			default:
				System.out.println("I or E Input");					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
