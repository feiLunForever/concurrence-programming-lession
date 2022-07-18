package 并发编程09.guarded_suspension模式;

/**
 * @Author idea
 * @Date created in 6:42 下午 2022/5/17
 */
public enum SendResult {

    SUCCESS(200),
    FAIL(500);

    private int code;

    SendResult(int code) {
        this.code = code;
    }
}
