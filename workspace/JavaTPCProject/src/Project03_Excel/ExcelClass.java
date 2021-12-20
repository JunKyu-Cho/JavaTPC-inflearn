package Project03_Excel;

public class ExcelClass {
	private String title;
	private String author;
	private String company;
	private String isbn;
	private String imgurl;
	

	public ExcelClass() {
		
	}

	public ExcelClass(String[] str) {
		super();
	
		for(int i = 0; i < str.length; i++) {
			if(i == 0)
				this.title = str[i];
			else if(i == 1)
				this.author = str[i];
			else if(i == 2)
				this.company = str[i];
			else if(i == 3)
				this.isbn = str[i];
			else if(i == 4)
				this.imgurl = str[i];
		}				
	}
	
	
	public ExcelClass(String title, String author, String company) {
		super();
		this.title = title;
		this.author = author;
		this.company = company;
	}



	public ExcelClass(String title, String author, String company, String isbn, String imaurl) {
		super();
		this.title = title;
		this.author = author;
		this.company = company;
		this.isbn = isbn;
		this.imgurl = imaurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imaurl) {
		this.imgurl = imaurl;
	}
	

	@Override
	public String toString() {
		return "ExcelClass [title=" + title + ", author=" + author + ", company=" + company + ", isbn=" + isbn
				+ ", imaurl=" + imgurl + "]";
	}

}
