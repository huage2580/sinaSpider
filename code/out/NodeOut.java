package out;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NodeOut {

	public static void main(String[] args) {
		DBhelper db=DBhelper.getInstance();
		
		try {
			FileOutputStream fo=new FileOutputStream("C:\\nodes.js", true);
			fo.write("var nodes = [ \n".getBytes());
			PreparedStatement ps = db.getConn().prepareStatement("select * from tuser");
			ResultSet rs = ps.executeQuery();
			System.out.println("====开始导出节点====");
			while (rs.next()) {
				StringBuffer sb=new StringBuffer();
				String uid=rs.getString(2);
				String name=rs.getString(3);
				int lv=rs.getInt(6);
				String line=sb.append("{")
				.append("uid:\"").append(uid).append("\",")
				.append("name:\"").append(name).append("\",")
				.append("lv:").append(lv)
				.append(" },\n")
				.toString();
				fo.write(line.getBytes());
			}
			fo.write("]; \n".getBytes());
			rs.close();
			ps.close();
			fo.close();
			System.out.println("====导出节点结束====");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
