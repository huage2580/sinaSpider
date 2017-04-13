package sina;

import java.util.List;

import javax.swing.JLabel;

import sina.JsonBean.CardsEntity;
import sina.JsonBean.CardsEntity.UserEntity;

import com.google.gson.Gson;

public class WorkThread extends Thread{
	public volatile boolean running=true;
	private HttpClient hc=new HttpClient();
	private DBhelper dBhelper=DBhelper.getInstance();
	private int currPage=1;
	private int maxPage=2;
	private boolean getFans;//当前用户粉丝比关注少
	private Gson gson=new Gson();
	private JLabel label;
	WorkThread(JLabel label){
		this.label=label;
	}
	void tip(String t){
		label.setText(getName()+">>>>>>>>"+t);
	}
	@Override
	public void run() {
		while (running) {
			int count=0;
			int rcount=0;
			//取出一个用户
			User user = null;
			try {
				user = WorkQueue.take();
			} catch (InterruptedException e) {
				break;
			}
			tip("开始爬"+user.getName());
			getFans=user.getFans()<user.getFollowers();
			//分页获取当前粉丝或者关注
			for(currPage=1,maxPage=2;currPage<=maxPage;currPage++){
				String url=
						"http://m.weibo.cn/container/getSecond?containerid=100505"
						+user.getUid()+"_-_"+(getFans?"FANS":"FOLLOWERS")+(currPage!=1?("&page="+currPage):"");
				String json=null;
				int errorCount=0;
				while (errorCount<2) {
					try {
						json=hc.get(url);
					} catch (Exception e) {
						hc=new HttpClient();
					}
					if (json==null) {//400了吧
						errorCount++;
						hc=new HttpClient();
						tip("[40x错误码]"+errorCount);
						try {
							sleep(60000*1);//我特么歇一分钟
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						break;
					}
				}//while
				if(errorCount>1){
					running=false;
					System.out.println("400错误2次");
					//重新把这个用户丢回队列
					WorkQueue.queue.add(user);
					break;
					}//错误400多次，干脆关掉线程
				//String json=hc.get(url);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				//解析json
				JsonBean jb=gson.fromJson(json, JsonBean.class);
				if (jb==null) {
					continue;
				}
				if (currPage==1) {
					maxPage=jb.getMaxPage();
//					System.out.println("设置maxPage"+maxPage);
					tip("设置maxPage"+maxPage);
				}
				List<CardsEntity> cards;
				try {
					cards = jb.getCards();
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if(cards==null){continue;}
				int fansCount=0;
				for(CardsEntity c:cards){
					UserEntity ue=c.getUser();
					count++;
					//System.out.println(ue.getScreen_name());
					//先封装成User
					User fans=new User();
					fans.setUid(String.valueOf(ue.getId()));
					fans.setName(ue.getScreen_name());
					fans.setGender(ue.getGender());
					fans.setAvatar(ue.getProfile_image_url());
					fans.setFans(ue.getFollowers_count());
					fans.setFollowers(ue.getFollow_count());
					fans.setLv(user.getLv()+1);
					//这里判断要不要加入到工作队列,条件：没在用户表，粉丝数少于400
					if (fans.getFans()<400) {
						if(!dBhelper.haveUser(fans)){
							WorkQueue.add(fans);
							tip("加入队列："+fans.getName());
						}
						//写入关系数据表
						dBhelper.addRelation2DB(user.getUid(), fans.getUid());
						rcount++;
					}
					
					fansCount++;
				}//for each 用户
//				System.out.println(currPage+"========"+maxPage);
				tip("当前页数:"+currPage+"/"+maxPage);
				try {
					sleep(200*fansCount);
				} catch (Exception e) {
					
				}	
			}//for 分页
//			System.out.println(count+"==>"+rcount);
			tip("所有"+count+"==>加入队列"+rcount);
		}//while
		System.out.println("工作线程结束:"+getName());
		tip("线程结束了");
	}//run
	
}//class
