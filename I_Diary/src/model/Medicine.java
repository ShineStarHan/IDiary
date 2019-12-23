package model;

public class Medicine {
	private int no;
	private String send;
	private String receive;
	private String time;
	private String capacity;
	private String keep;
	private String ref;
	private String medicine;
	private String date;
	
	public Medicine() {
		
	}
	
	// 테이블뷰


	// 디비
	public Medicine(int no, String send, String receive, String time, String capacity, String keep, String ref,
			String medicine, String date) {
		super();
		this.no = no;
		this.send = send;
		this.receive = receive;
		this.time = time;
		this.capacity = capacity;
		this.keep = keep;
		this.ref = ref;
		this.medicine = medicine;
		this.date = date;
	}

	public Medicine(int no, String receive, String ref, String date) {
		super();
		this.no = no;
		this.receive = receive;
		this.ref = ref;
		this.date = date;
	}

	public Medicine(int no, String send, String receive, String time, String capacity, String keep, String ref,
			String date) {
		super();
		this.no = no;
		this.send = send;
		this.receive = receive;
		this.time = time;
		this.capacity = capacity;
		this.keep = keep;
		this.ref = ref;
		this.date = date;
	}

	public Medicine(String send, String receive, String time, String capacity, String keep, String ref) {
		super();
		this.send = send;
		this.receive = receive;
		this.time = time;
		this.capacity = capacity;
		this.keep = keep;
		this.ref = ref;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getKeep() {
		return keep;
	}

	public void setKeep(String keep) {
		this.keep = keep;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
