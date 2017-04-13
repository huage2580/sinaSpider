package sina;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

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
	
	public synchronized void addUser2DB(User user) {
		try {
			CallableStatement call = conn.prepareCall("call addUser(?,?,?,?,?);");
			call.setString(1, user.getUid());
			call.setString(2, user.getName());
			call.setString(3, user.getGender());
			call.setString(4, user.getAvatar());
			call.setInt(5, user.getLv());
			call.execute();
			call.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public synchronized boolean haveUser(User user) {
		boolean result=false;
		try {
			PreparedStatement ps = conn.prepareStatement("select * from tuser where uid=?");
			ps.setString(1, user.getUid());
			ResultSet rs = ps.executeQuery();
			result=rs.first();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public synchronized void addRelation2DB(String uid,String fid) {
		try {
			CallableStatement call = conn.prepareCall("call addRelation(?,?);");
			call.setString(1, uid);
			call.setString(2, fid);
			call.execute();
			call.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void addQueue(String json){
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO tqueue (json) VALUES (?);");
			ps.setString(1, json);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public synchronized List<String> loadQueue(){
		Gson gson=new Gson();
		List<String> result=new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from tqueue;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//result.add(rs.getString(2));
				User u=gson.fromJson(rs.getString(2), User.class);
				WorkQueue.queue.add(u);
			}
			rs.close();
			ps.close();
			//清空队列表
			PreparedStatement ps2 = conn.prepareStatement("truncate table tqueue;");
			ps2.execute();
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
