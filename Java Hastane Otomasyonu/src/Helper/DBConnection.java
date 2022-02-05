package Helper;
import java.sql.*;
public class DBConnection {
	Connection c = null;
	/*bu sýnýf yalnýzca veritabaný baðlantýlarý için kullanýlmaktadýr kod tekrarýnýn önüne geçmek ve daha hýzlý çalýþan bir program 
	  üretebilmek adýna sadece ihtiyaç duyulan yerlerde çalýþmasý için
	  nesne üretilerek kullanýlýyor.
	*/
	public DBConnection() {
		
	}
	public Connection connDb() {
		try {
			this.c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/hospital?user=root&password=root"); //veritabaný baðlantýmýz
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
