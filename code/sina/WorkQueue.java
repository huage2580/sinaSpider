package sina;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

public class WorkQueue {
	public static LinkedBlockingQueue<User> queue=new LinkedBlockingQueue<User>();
	/**
	 * 添加用户到工作队列
	 * @param u
	 */
	public static void add(User u) {
		try {
			//System.out.println("入队");
			queue.put(u);
			//TODO 这里要加入用户表
			DBhelper.getInstance().addUser2DB(u);
			//System.out.println("添加到数据库");
		} catch (InterruptedException e) {
			
		}
		
	}
	/**
	 * 出队一个用户
	 * @return
	 * @throws InterruptedException 
	 */
	public static User take() throws InterruptedException{
		return queue.take();
	}
	/**
	 * 保存所有数据到数据库
	 */
	public static void saveAll(){
		DBhelper db=DBhelper.getInstance();
		Gson gson=new Gson();
		User u=null;
		Main.tip.setText("开始存储状态!");
		while((u=queue.poll())!=null){
			String jsonString=gson.toJson(u);
			db.addQueue(jsonString);
		}
		System.out.println("存储状态成功!");
		Main.tip.setText("存储状态成功!");
	}
	/**
	 * 从数据库恢复数据
	 */
	public static void load() {
		DBhelper db=DBhelper.getInstance();
//		Gson gson=new Gson();
		List<String> result=db.loadQueue();
//		for (String string : result) {
//			User u=gson.fromJson(string, User.class);
//			try {
//				queue.put(u);
//				//System.out.println("add:"+u.getName());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		System.out.println("恢复状态成功!");
		Main.tip.setText("恢复状态成功!");
	}
}
