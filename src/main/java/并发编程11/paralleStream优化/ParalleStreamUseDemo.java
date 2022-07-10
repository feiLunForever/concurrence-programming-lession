package 并发编程11.paralleStream优化;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * 使用了并行流并不一定是真的并行
 *
 * @Author linhao
 * @Date created in 10:01 上午 2022/7/10
 */
public class ParalleStreamUseDemo {

    /**
     * 对非线程安全集合使用并行流
     */
    @Test
    public void userNotSafeCollection() {
        List<Integer> idCOWList = new CopyOnWriteArrayList<>();
        List<Integer> idArrayList = new ArrayList<>();
        List<String> numList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            numList.add(String.valueOf(i));
        }
        numList.parallelStream().forEach(x -> {
            idCOWList.add(Integer.valueOf(x));
        });
        System.out.println(idCOWList.size());

        numList.parallelStream().forEach(x -> {
            idArrayList.add(Integer.valueOf(x));
        });
        System.out.println(idArrayList.size());
    }


    @Test
    public void testParallel() {
        List<String> numList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            numList.add(String.valueOf(i));
        }
        numList.parallelStream();
        numList.stream().parallel();
        Stream.of(numList).parallel();
    }
}
