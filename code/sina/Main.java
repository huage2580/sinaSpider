package sina;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	private JButton startButton;
	private static JPanel jPanel;
	static MainThread mainThread;
	static JLabel tip;
	Main(){
		super("微博数据爬取");
		jPanel = new JPanel();
		tip = new JLabel("这里是提示");
		startButton=new JButton("开始");
		JButton endButton=new JButton("停止");
		jPanel.add(tip);
		jPanel.add(startButton);
		jPanel.add(endButton);
		this.add(jPanel);
		this.setSize(400, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!mainThread.isAlive()) {
					mainThread.start();
					tip.setText("开启主线程");
				}	
			}
		});
		endButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainThread.stopWork();
				tip.setText("正在等待所有工作线程关闭");
			}
		});
	}
	
	public static void main(String[] args) {
		Main m=new Main();
		mainThread=new MainThread(jPanel);
	}

}
