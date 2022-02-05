package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User { //kod tekrar�n�n �n�ne ge�mek i�in user s�n�f�ndan tan�ml� bilgileri miras y�ntemiyle al�yoruz
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Bashekim(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);//super() metoduyla �st s�n�ftaki bilgilere eri�iyoruz.
	}
	public Bashekim() {
		
		
	}
	public ArrayList<User> getDoctorList() throws SQLException{ //doktorlar�n ba�hekim ekran�nda listelendi�i k�s�m.
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type = 'doktor' ");
			while(rs.next()) {
				obj = new User(rs.getInt("id"),rs.getString("tcno"),rs.getString("name"),rs.getString("password"),rs.getString("type"));
				list.add(obj);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		return list;
		
	}
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{ //doktorlar�n klinik se�ildi�inde listelenmesini sa�layan k�s�m.
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id="+clinic_id); 
			//ilk (sol) tablodaki t�m sat�rlar� ve ko�ul ile belirtilen ikinci (sa�) tablodaki sat�rlar� se�mek/birle�tirmek i�in kullan�l�r.
			//iki tablodaki id alanlar� e�itlenmi�. Birinci tablodan b�t�n kay�tlar al�nacakt�r. �kinci tablodan ise id alan�nda sadece e�itlenen kay�tlar al�nacakt�r.
			while(rs.next()) {
				obj = new User(rs.getInt("u.id"),rs.getString("u.tcno"),rs.getString("u.name"),rs.getString("u.password"),rs.getString("u.type"));
				list.add(obj);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		return list;
		
	}
		public boolean addDoctor(String tcno,String password,String name) throws SQLException { //veritaban�na doktor eklemek i�in kullan�lan metot
			String query = "INSERT INTO user"+"(tcno,password,name,type) VALUES"+"(?,?,?,?)";
			boolean key = false;
			try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "doktor");
			preparedStatement.executeUpdate();
			key = true;
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(key) {
				return true;
			}
			else {
				return false;
			}
		}
		public boolean deleteDoctor(int id) throws SQLException { //veritaban�ndan doktor silmek i�in kullan�lan metot
			String query = "DELETE FROM user WHERE id = ? ";
			boolean key = false;
			try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(key) {
				return true;
			}
			else {
				return false;
			}
		}
		public boolean updateDoctor(int id,String tcno,String password,String name) throws SQLException { //doktorlar�n bilgilerini g�ncellemek i�in kulland���m�z metot
			String query = "UPDATE user SET name=?, tcno=?,password=? WHERE id=?" ;
			boolean key = false;
			try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2,tcno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4,id);
			preparedStatement.executeUpdate();
			key = true;
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(key) {
				return true;
			}
			else {
				return false;
			}
		}
		public boolean addWorker(int user_id, int clinic_id) throws SQLException { //kliniklere doktor eklemek i�in kulland���m�z metot
			String query = "INSERT INTO worker" + "(user_id,clinic_id) VALUES" + "(?,?)";
			boolean key = false;
			int count =0;
			try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM  worker WHERE clinic_id AND user_id =" + clinic_id + user_id);
			while(rs.next()) {
				count++;
			}
			if(count==1) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, user_id);
				preparedStatement.setInt(2, clinic_id);
				preparedStatement.executeUpdate();
			}
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, clinic_id);
			preparedStatement.executeUpdate();
			key = true;
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(key) {
				return true;
			}
			else {
				return false;
			}
		}
}
	

