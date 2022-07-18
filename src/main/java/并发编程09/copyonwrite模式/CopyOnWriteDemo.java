package 并发编程09.copyonwrite模式;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author idea
 * @Date created in 10:42 上午 2022/7/3
 */
public class CopyOnWriteDemo {

    @Test
    public void showCopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("idea1");
        copyOnWriteArrayList.add("idea2");
        copyOnWriteArrayList.add("idea3");
        copyOnWriteArrayList.add("idea4");
        System.out.println(copyOnWriteArrayList.get(0));
    }

    @Test
    public void showCopyOnWriteArraySet() {
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        copyOnWriteArraySet.add("t1");
        copyOnWriteArraySet.add("t1");
        copyOnWriteArraySet.add("t1");
        System.out.println(copyOnWriteArraySet.size());
    }

}
