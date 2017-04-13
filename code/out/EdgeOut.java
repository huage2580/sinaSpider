package out;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdgeOut {

	public static void main(String[] args) {
		DBhelper db=DBhelper.getInstance();
		try {
			FileOutputStream fo=new FileOutputStream("C:\\edges.js", true);
			fo.write("var edges = [ \n".getBytes());
			PreparedStatement ps = db.getConn().prepareStatement("select * from trelation");
			ResultSet rs = ps.executeQuery();
			System.out.println("====开始导出连线====");
			while (rs.next()) {
				StringBuffer sb=new StringBuffer();
				String uid=rs.getString(2);
				String fid=rs.getString(3);
				String line=sb.append("{")
				.append("source : \"").append(uid).append("\",")
				.append("target: \"").append(fid).append("\",")
				.append(" },\n")
				.toString();
				fo.write(line.getBytes());
			}
			fo.write("]; \n".getBytes());
			rs.close();
			ps.close();
			fo.close();
			System.out.println("====导出连线结束====");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
