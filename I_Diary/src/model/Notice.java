package model;

public class Notice {

	private int no;
	private String title;
	private String contents;
	private String className;
	private String date;
	
	public Notice() {
		
	}
	
	public Notice(String title, String contents) {
		super();
		this.title = title;
		this.contents = contents;
	}

	public Notice(String title, String contents, String className) {
		super();
		this.title = title;
		this.contents = contents;
		this.className = className;
	}

	// 테이블뷰
	public Notice(int no, String title, String contents, String className, String date) {
		super();
		this.no = no;
		this.title = title;
		this.contents = contents;
		this.className = className;
		this.date = date;
	}

	// 디비
	public Notice(int no, String title, String date) {
		super();
		this.no = no;
		this.title = title;
		this.date = date;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
