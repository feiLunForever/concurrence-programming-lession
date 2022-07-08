package dubbo.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author linhao
 * @Date created in 3:39 下午 2022/7/7
 */
public class CommonRequestContext {

    private static final ThreadLocal<Map<Object,Object>> requestContentMap = new TransmittableThreadLocal<Map<Object, Object>>(){
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }

        @Override
        public Map<Object, Object> copy(Map<Object, Object> parentValue) {
            return parentValue != null ? new HashMap<>(parentValue) : null;
        }
    };

    public static void put(Object key,Object value) {
        requestContentMap.get().put(key,value);
    }

    public static Object get(Object key){
        return requestContentMap.get().get(key);
    }

    public static void clear() {
        requestContentMap.remove();
    }

}
