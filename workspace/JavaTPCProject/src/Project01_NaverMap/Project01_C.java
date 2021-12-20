package Project01_NaverMap;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {

	public static void main(String[] args) {
		
		String src = "info.json";
		InputStream fJson = Project01_C.class.getResourceAsStream(src);
		if(fJson == null) {
			throw new NullPointerException("Cann't Find Resource File");
		}
			
		JSONTokener tokener = new JSONTokener(fJson);
		JSONObject obj = new JSONObject(tokener);
		JSONArray students = obj.getJSONArray("students");
		
		for(int i=0; i<students.length(); i++) {
			JSONObject student = (JSONObject)students.get(i);
			System.out.print(student.get("name") + "\t");
			System.out.print(student.get("phone") + "\t");
			System.out.println(student.get("address"));
		}
		

	}

}
