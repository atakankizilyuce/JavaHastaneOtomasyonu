package Model;

import Helper.DBConnection;
//bu sýnýf veritabanýnýn user tablosundaki verilerle iþlem yapabilmemizi saðlamaktadýr
public class User { 
	private int id;
	String tcno,name,password,type;
	DBConnection conn = new DBConnection();
	
	public int getId() {
		return id;
	}
	public User(int id, String tcno, String name, String password, String type) {
		this.id = id;
		this.tcno = tcno;
		this.name = name;
		this.password = password;
		this.type = type;
	}
	public User() {
		
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTcno() {
		return tcno;
	}
	public void setTcno(String tcno) {
		this.tcno = tcno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
