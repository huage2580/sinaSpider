package sina;



import java.util.List;

/**
 * Created by hua on 2017.3.24.
 */

public class JsonBean {
    private int count;
    private int maxPage;
    private List<CardsEntity> cards;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<CardsEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardsEntity> cards) {
        this.cards = cards;
    }

    public static class CardsEntity {

        private UserEntity user;

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public static class UserEntity {
            private long id;
            private String screen_name;
            private String profile_image_url;
            private boolean verified;
            private String gender;
            private int followers_count;
            private int follow_count;//Fans

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getScreen_name() {
                return screen_name;
            }

            public void setScreen_name(String screen_name) {
                this.screen_name = screen_name;
            }

            public String getProfile_image_url() {
                return profile_image_url;
            }

            public void setProfile_image_url(String profile_image_url) {
                this.profile_image_url = profile_image_url;
            }

            public boolean isVerified() {
                return verified;
            }

            public void setVerified(boolean verified) {
                this.verified = verified;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getFollowers_count() {
                return followers_count;
            }

            public void setFollowers_count(int followers_count) {
                this.followers_count = followers_count;
            }

            public int getFollow_count() {
                return follow_count;
            }

            public void setFollow_count(int follow_count) {
                this.follow_count = follow_count;
            }
        }
    }
}
