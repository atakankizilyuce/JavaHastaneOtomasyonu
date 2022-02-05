package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Helper.Helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Hasta extends User{
	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.connDb();
	PreparedStatement preparedStatement = null;
	
	public Hasta() {
		super();
		
	}

	public Hasta(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
		
	}
	

	public boolean register(String tcno,String password,String name) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)"; //veritabanýna yeni hasta kayýtý eklemek için kullandýðýmýz kýsým
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno='"+tcno+"'");

			while (rs.next()) {
				duplicate = true;
				Helper.showMsg("Bu TC Numarasýna ait bir kayýt bulunmaktadýr");
				break;
			}
			if (!duplicate) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4,"hasta");
				preparedStatement.executeUpdate();
				key = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addAppointment(int doctor_id,int hasta_id,String doctor_name,String hasta_name,String appDate) throws SQLException { //hastalarýn doktorlardan randevu almasýný saðlayan kýsým
		int key = 0;
		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES" + "(?,?,?,?,?)";
		try {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setInt(3, hasta_id);
				preparedStatement.setString(4,hasta_name);
				preparedStatement.setString(5,appDate);
				key =1;
				preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}

	}
	
	public boolean updateWhourStatus(int doctor_id,String wdate) throws SQLException{ //ileride oluþabilecek sorunlarý engellemek için seçilen randevunun bir daha seçilmemesini saðlayan kýsým
		int key = 0;
		String query = "UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate = ?";
		try {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, "p");
				preparedStatement.setInt(2, doctor_id);
				preparedStatement.setString(3, wdate);
				preparedStatement.executeUpdate();
				key =1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}

	}
}
