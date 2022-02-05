package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User { //kod tekrarýnýn önüne geçmek için user sýnýfýndan tanýmlý bilgileri miras yöntemiyle alýyoruz
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Bashekim(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);//super() metoduyla üst sýnýftaki bilgilere eriþiyoruz.
	}
	public Bashekim() {
		
		
	}
	public ArrayList<User> getDoctorList() throws SQLException{ //doktorlarýn baþhekim ekranýnda listelendiði kýsým.
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
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{ //doktorlarýn klinik seçildiðinde listelenmesini saðlayan kýsým.
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id="+clinic_id); 
			//ilk (sol) tablodaki tüm satýrlarý ve koþul ile belirtilen ikinci (sað) tablodaki satýrlarý seçmek/birleþtirmek için kullanýlýr.
			//iki tablodaki id alanlarý eþitlenmiþ. Birinci tablodan bütün kayýtlar alýnacaktýr. Ýkinci tablodan ise id alanýnda sadece eþitlenen kayýtlar alýnacaktýr.
			while(rs.next()) {
				obj = new User(rs.getInt("u.id"),rs.getString("u.tcno"),rs.getString("u.name"),rs.getString("u.password"),rs.getString("u.type"));
				list.add(obj);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
			
		return list;
		
	}
		public boolean addDoctor(String tcno,String password,String name) throws SQLException { //veritabanýna doktor eklemek için kullanýlan metot
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
		public boolean deleteDoctor(int id) throws SQLException { //veritabanýndan doktor silmek için kullanýlan metot
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
		public boolean updateDoctor(int id,String tcno,String password,String name) throws SQLException { //doktorlarýn bilgilerini güncellemek için kullandýðýmýz metot
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
		public boolean addWorker(int user_id, int clinic_id) throws SQLException { //kliniklere doktor eklemek için kullandýðýmýz metot
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
	

