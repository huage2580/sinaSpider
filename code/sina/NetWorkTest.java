package sina;

public class NetWorkTest {

	public static void main(String[] args) {
		HttpClient hc=new HttpClient();
		String json=hc.get("http://m.weibo.cn/container/getSecond?containerid=1005052431592195_-_FANS&page=2");
		System.out.println(json);
	}

}
