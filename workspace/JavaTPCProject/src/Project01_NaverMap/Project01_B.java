package Project01_NaverMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_B {

	public static void main(String[] args) {

		// Json (Json-Java) Test
		JSONArray students = new JSONArray();
		
		JSONObject student = new JSONObject();
		student.put("name", "홍길동");
		student.put("phone", "010-1234-1234");
		student.put("address", "서울");
		students.put(student);
		
		student = new JSONObject();
		student.put("name", "이순신");
		student.put("phone", "010-1111-2222");
		student.put("address", "성남");
		students.put(student);
		System.out.println(students);
						
		JSONObject object = new JSONObject();
		object.put("students", students);
		System.out.println(object);
		System.out.println(object.toString(2));
		
	}

}
