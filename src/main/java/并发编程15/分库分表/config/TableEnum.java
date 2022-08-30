package 并发编程15.分库分表.config;

/**
 * 分表美剧类型
 *
 * @Author idea
 * @Date created in 10:23 下午 2022/8/22
 */
public enum TableEnum {

    T_USER_MESSAGE(0,"t_user_message",1000);

    int code;
    String name;
    int mod;

    TableEnum(int code, String name, int mod) {
        this.code = code;
        this.name = name;
        this.mod = mod;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getMod() {
        return mod;
    }
}
