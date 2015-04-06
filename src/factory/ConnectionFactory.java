package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection(){
		String url = "jdbc:mysql://localhost/livraria";
		
		try{
			return DriverManager.getConnection(url, "root", "kanatsu");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
//	public static void main(String[] args) {
//		Connection conn = new ConnectionFactory().getConnection();
//		System.out.println("OK!");
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
