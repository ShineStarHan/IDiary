package model;

public class Inform {

	private int no;
	private String send;
	private String receive;
	private String inform;
	private String date;
	
	public Inform() {
		
	}

	public Inform(int no, String send, String receive, String date) {
		super();
		this.no = no;
		this.send = send;
		this.receive = receive;
		this.date = date;
	}

	public Inform(int no, String send, String receive, String inform, String date) {
		super();
		this.no = no;
		this.send = send;
		this.receive = receive;
		this.inform = inform;
		this.date = date;
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

	public String getInform() {
		return inform;
	}

	public void setInform(String inform) {
		this.inform = inform;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
