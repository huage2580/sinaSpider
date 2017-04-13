package sina;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;





public class HttpClient {
private static HttpClient hcClient;
private String sessionID = "_T_WM=c64d3ef98d4a6caffbaac030f1da7939; ALF=1493005224; SCF=As7PvPf6I0PpbYO_QCZcBogS7apliXZbgQc911qFyVYaqJlYUhdm8om4hw6m1KRek7egAnrSNv8Rg1YlecbHTGE.; SUB=_2A2510ZdQDeRxGeVM7VoR9i3Iyz2IHXVXPTkYrDV6PUJbktBeLWzikW0ZFsq6mJdB0eMvm809zPblltT5-w..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWDCAmRvF_vMO8rLhUN6cyg5JpX5o2p5NHD95Q0eoqRehq0Sh5pWs4Dqcj.i--NiKyFi-zRi--fi-z7iKysi--fiKy2iKy8THxawsLoqg4X; SUHB=0wn10-gxs2RX-E; SSOLoginState=1490413312; M_WEIBOCN_PARAMS=luicode%3D10000012%26lfid%3D1005052431592195_-_FANS";
private final String UA="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
private final String UA_SPIDER="spider";
private final String UA_SAFARI="Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
private final String UA_BAIDU="Mozilla/5.0 (compatible; Baiduspider-render/2.0; +http://www.baidu.com/search/spider.html)";
private String[] UAs={UA_BAIDU,UA,UA_SPIDER,UA_SAFARI};
	static HttpClient getInstance(){
		if (hcClient==null) {
			synchronized (HttpClient.class) {
				if (hcClient==null) {
					hcClient=new HttpClient();
				}
			}
		}
		return hcClient;
	}
	public String get(String url, String charset,String referer,boolean isRedirects) {
		try {
			String key = "";
			String cookieVal = "";
			URL httpURL = new URL(url);
			HttpURLConnection http = (HttpURLConnection) httpURL
					.openConnection();
			http.setInstanceFollowRedirects(isRedirects);//设置自动跳转
			if (referer!=null ) {
				http.setRequestProperty("Referer", referer);
			}
			Random random=new Random(System.currentTimeMillis());
			int i=random.nextInt(UAs.length);
			http.setRequestProperty("User-agent",UAs[i]);
//			if (!sessionID.equals("")) {
//				http.setRequestProperty("Cookie", sessionID);
//			}
			http.setRequestProperty("Accept", "application/json, text/plain, */*");
//			for (int i = 1; (key = http.getHeaderFieldKey(i)) != null; i++) {
//				if (key.equalsIgnoreCase("set-cookie")) {
//					cookieVal = http.getHeaderField(i);
//					cookieVal = cookieVal.substring(
//							0,
//							cookieVal.indexOf(";") > -1 ? cookieVal
//									.indexOf(";") : cookieVal.length() - 1);
//					sessionID = sessionID + cookieVal + ";";
//				}
//			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					http.getInputStream(), charset));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				sb.append("\n");
			}
			br.close();
			http.disconnect();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String get(String url) {
		return get(url, "utf-8", "", true);
	}

}
