package model;

public class Attend {
   
   private String className;
   private String month;
   private String name;
   private int attend;
   private int absent;
   private int early;
   
   public Attend() {
      
   }

  
  

  

   public Attend(String month, String name, int attend, int absent, int early) {
	super();
	this.month = month;
	this.name = name;
	this.attend = attend;
	this.absent = absent;
	this.early = early;
}






public Attend(String className, String month, String name, int attend, int absent, int early) {
	super();
	this.className = className;
	this.month = month;
	this.name = name;
	this.attend = attend;
	this.absent = absent;
	this.early = early;
}






public String getMonth() {
	return month;
}






public void setMonth(String month) {
	this.month = month;
}






public String getClassName() {
	return className;
}

public void setClassName(String className) {
	this.className = className;
}

public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

  

   public int getAttend() {
      return attend;
   }

   public void setAttend(int attend) {
      this.attend = attend;
   }

   public int getAbsent() {
      return absent;
   }

   public void setAbsent(int absent) {
      this.absent = absent;
   }

   public int getEarly() {
      return early;
   }

   public void setEarly(int early) {
      this.early = early;
   }
   
   

}