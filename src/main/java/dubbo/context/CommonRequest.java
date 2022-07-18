package dubbo.context;

import java.io.Serializable;

/**
 * @Author idea
 * @Date created in 2:51 下午 2022/7/7
 */
public class CommonRequest implements Serializable {

    private static final long serialVersionUID = -784170575887989214L;

    private String traceId;

    private long userId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommonRequest{" +
                "traceId='" + traceId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
