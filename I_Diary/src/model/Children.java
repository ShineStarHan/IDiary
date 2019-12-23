package model;

public class Children {
	
	private String childrenName;
	private String birthday;
	private String className;
	private String childrenGender;
	private String parentName;
	private String phone;
	private String address;
	private String image;
	public Children() {
		
	}
	
	

	public Children(String childrenName, String birthday, String className, String childrenGender, String parentName,
			String phone, String address, String image) {
		super();
		this.childrenName = childrenName;
		this.birthday = birthday;
		this.className = className;
		this.childrenGender = childrenGender;
		this.parentName = parentName;
		this.phone = phone;
		this.address = address;
		this.image = image;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public Children(String childrenName, String birthday, String className, String childrenGender, String parentName,
			String phone, String address) {
		super();
		this.childrenName = childrenName;
		this.birthday = birthday;
		this.className = className;
		this.childrenGender = childrenGender;
		this.parentName = parentName;
		this.phone = phone;
		this.address = address;
	}


	public Children(String childrenName, String className, String childrenGender, String parentName, String phone,
			String address) {
		super();
		this.childrenName = childrenName;
		this.className = className;
		this.childrenGender = childrenGender;
		this.parentName = parentName;
		this.phone = phone;
		this.address = address;
	}



	public String getChildrenName() {
		return childrenName;
	}

	public void setChildrenName(String childrenName) {
		this.childrenName = childrenName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getChildrenGender() {
		return childrenGender;
	}

	public void setChildrenGender(String childrenGender) {
		this.childrenGender = childrenGender;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}