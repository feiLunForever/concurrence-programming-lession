package 并发编程11.用户查询.bean;

/**
 * @Author idea
 * @Date created in 6:42 下午 2022/7/4
 */
public class UserInfoPO {

    private long userId;

    private String userName;

    private String tel;

    private short age;

    private short sex;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }
}
