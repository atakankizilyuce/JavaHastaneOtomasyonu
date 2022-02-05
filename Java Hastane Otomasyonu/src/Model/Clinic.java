package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Clinic {
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	private int id;
	private String name;

	public Clinic() {

	}

	public Clinic(int id, String name)  {
		super();
		this.id = id;
		this.name = name;
	}

	public ArrayList<Clinic> getList() throws SQLException {
		ArrayList<Clinic> list = new ArrayList<>();
		Clinic obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic");
			while (rs.next()) {
				obj = new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}

		return list;
	}
	
	public boolean addClinic(String name) throws SQLException { //veritaban�na klinik eklemek i�in kulland���m�z metot
		String query = "INSERT INTO clinic"+"(name) VALUES"+"(?)";
		boolean key = false;
		Connection con = conn.connDb();
		try {
		st = con.createStatement();
		preparedStatement = con.prepareStatement(query);
		preparedStatement.setString(1, name);
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
	public boolean deleteClinic(int id) throws SQLException { //veritaban�ndan klinik silmek i�in kulland���m�z k�s�m.
		String query = "DELETE FROM clinic WHERE id = ? ";
		boolean key = false;
		Connection con = conn.connDb();
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
	public boolean updateClinic(int id,String name) throws SQLException { //veritaban�nda klinik bilgilerini g�ncellemek i�in kulland���m�z metot
		String query = "UPDATE clinic SET name = ? WHERE id = ?" ;
		boolean key = false;
		Connection con = conn.connDb();
		try {
		st = con.createStatement();
		preparedStatement = con.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, id);
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
	//getter ve setterlar
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Clinic getFetch(int id) { //kliniklerin se�ilen id'ye g�re s�raland��� k�s�m.
		Connection con = conn.connDb();
		Clinic c = new Clinic();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id ="+id);
			while(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{
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
}
