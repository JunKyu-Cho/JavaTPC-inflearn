package Project01_NaverMap;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.inflearn.ObjBook;

public class Project01_A {

	public static void main(String[] args) {
		
		// JSon (Gson) Test
		ObjBook fromBook = new ObjBook("�ڹ�", 21000, "������", 670);
		Gson g = new Gson();

		String json = g.toJson(fromBook);
		System.out.println(json);
		
		ObjBook toBook =  g.fromJson(json, ObjBook.class);
		System.out.println(toBook);
		System.out.println(toBook.getTitle() + '\t' + toBook.getPrice() + '\t' + toBook.getCompany() + '\t' + toBook.getPage());
		
		 List<ObjBook> fromBooks = new ArrayList<ObjBook>();
		 fromBooks.add(new ObjBook("�ڹ�1", 11000, "������1", 670));
		 fromBooks.add(new ObjBook("�ڹ�2", 21000, "������2", 770));
		 fromBooks.add(new ObjBook("�ڹ�3", 31000, "������3", 870));
		 
		 String listjson = g.toJson(fromBooks);
		 System.out.println(listjson);
		 
		 // TypeToken =
		 List<ObjBook> toBooks = g.fromJson(listjson, new TypeToken<List<ObjBook>>(){}.getType());
		 
		 for(ObjBook vo : toBooks){
			 System.out.println(vo);
		 }
		 
	}

}
