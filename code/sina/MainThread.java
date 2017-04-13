package sina;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainThread extends Thread{
	static ArrayList<WorkThread> threads=new ArrayList<WorkThread>();
	public static int threadsCount=1;
	private JPanel jPanel;
	
	public MainThread(JPanel jPanel) {
		this.jPanel=jPanel;
	}
	@Override
	public void run() {
		//从数据库导入队列
		WorkQueue.load();
		//为空放入初始数据
		if (WorkQueue.queue.isEmpty()) {
			User user=new User();
			user.setUid("3268063401");
			user.setName("狸小华-Toxicant");
			user.setAvatar("自己就不要头像了吧~");
			user.setGender("m");
			user.setFans(200);
			user.setFollowers(100);
			user.setLv(1);
			WorkQueue.add(user);
		}
		//开始工作线程
		for(int i=0;i<threadsCount;i++){
			JLabel label=new JLabel("线程"+i);
			jPanel.add(label);
			WorkThread t=new WorkThread(label);
			threads.add(t);
			t.start();
		}
		//阻塞等待所有工作线程结束
		boolean run=true;
		while (run) {
			run=false;
			for (WorkThread t : threads) {
				run=run | t.isAlive();
			}
		}
		//存储队列
		WorkQueue.saveAll();
		//结束
		System.out.println("=====本次爬虫结束=====");
	}
	public void stopWork(){
		for (WorkThread workThread : threads) {
			workThread.running=false;
		}
	}
	

}
