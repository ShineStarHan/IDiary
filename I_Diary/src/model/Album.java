package model;

public class Album {
   
   private int no;
   private String image;
   
   public Album() {
      
   }

   // µðºñ
   public Album(int no, String image) {
      super();
      this.no = no;
      this.image = image;
   }

   public Album(String image) {
      super();
      this.image = image;
   }

   public int getNo() {
      return no;
   }

   public void setNo(int no) {
      this.no = no;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }
   
}