package sina;

public class DBTest {

	public static void main(String[] args) {
		DBhelper dBhelper=DBhelper.getInstance();
		User user=new User();
		user.setUid("123456");
		user.setName("hua");
		user.setGender("f");
		user.setAvatar("Í·Ïñ");
		user.setLv(2);
		user.setFans(100);
		user.setFollowers(200);
		boolean r=dBhelper.haveUser(user);
		System.out.println(r);
		dBhelper.addUser2DB(user);
		r=dBhelper.haveUser(user);
		System.out.println(r);
		dBhelper.addRelation2DB("456", "666");
	}

}
