package model;

public class Schedule {

	private String month;
	private String week;
	private String mon;
	private String tue;
	private String wen;
	private String thur;
	private String fri;
	private String sat;

	public Schedule() {

	}


	public Schedule(String mon, String tue, String wen, String thur, String fri, String sat) {
		super();
		this.mon = mon;
		this.tue = tue;
		this.wen = wen;
		this.thur = thur;
		this.fri = fri;
		this.sat = sat;
	}

	public Schedule(String month, String week, String mon, String tue, String wen, String thur, String fri,
			String sat) {
		super();
		this.month = month;
		this.week = week;
		this.mon = mon;
		this.tue = tue;
		this.wen = wen;
		this.thur = thur;
		this.fri = fri;
		this.sat = sat;
	}
	
	

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTue() {
		return tue;
	}

	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getWen() {
		return wen;
	}

	public void setWen(String wen) {
		this.wen = wen;
	}

	public String getThur() {
		return thur;
	}

	public void setThur(String thur) {
		this.thur = thur;
	}

	public String getFri() {
		return fri;
	}

	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}
}