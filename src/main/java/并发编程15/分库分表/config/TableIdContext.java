package 并发编程15.分库分表.config;

/**
 * @Author linhao
 * @Date created in 12:20 下午 2022/8/10
 */
public class TableIdContext {

    //使用ThreadLocal防止多线程相互影响
    public static ThreadLocal<Integer> id = new ThreadLocal<Integer>();

    public static void setId(Integer idValue) {
        id.set(idValue);
    }

    public static Integer getId(){
        return id.get();
    }

    public static void clean(){
        id.remove();
    }
}
