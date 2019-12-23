package model;

public class Admin {
	private String id;
	private String password;
	private String name;
	private String className;
	private String image;
	
	
	
	
	
	public Admin(String id, String password, String name, String className, String image) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.className = className;
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Admin() {
		
	}
	
	public Admin(String id, String password, String name, String className) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
