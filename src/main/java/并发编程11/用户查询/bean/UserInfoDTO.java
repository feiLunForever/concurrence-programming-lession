package 并发编程11.用户查询.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author idea
 * @Date created in 5:17 下午 2022/7/4
 */
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 4348987230551391764L;

    private long userId;

    private String userName;

    private String tel;

    private short age;

    private short sex;

    /**
     * 认证状态
     */
    private boolean verifyStatus;

    /**
     * 用户等级
     */
    private int memberLevel;

    /**
     * 头像图片
     */
    private List<String> headPortrait;

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

    public boolean isVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(boolean verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public List<String> getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(List<String> headPortrait) {
        this.headPortrait = headPortrait;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", tel='" + tel + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", verifyStatus=" + verifyStatus +
                ", memberLevel=" + memberLevel +
                ", headPortrait=" + headPortrait +
                '}';
    }
}
