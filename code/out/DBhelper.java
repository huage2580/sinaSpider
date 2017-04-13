package out;

import java.sql.SQLException;

public class DBhelper {
	private static DBhelper ins;
	private java.sql.Connection conn;
	public static DBhelper getInstance() {
		if (ins==null) {
			synchronized (DBhelper.class) {
				if (ins==null) {
					ins=new DBhelper();
				}
			}
		}
		return ins;
	}
	public DBhelper(){
		try {  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {   
            e.printStackTrace();  
        }
        try {
			conn = java.sql.DriverManager.getConnection(  
			        "jdbc:mysql://localhost/sina",  
			        "root", "123456");
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	
	
	public java.sql.Connection getConn() {
		return conn;
	}
	public void setConn(java.sql.Connection conn) {
		this.conn = conn;
	}
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
