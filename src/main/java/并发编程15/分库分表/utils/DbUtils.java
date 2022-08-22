package 并发编程15.分库分表.utils;

import 并发编程15.分库分表.config.TableEnum;
import 并发编程15.分库分表.config.TableIdContext;
import 并发编程15.分库分表.po.UserMessagePO;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * @Author linhao
 * @Date created in 10:09 下午 2022/8/22
 */
public class DbUtils<T> {


    /**
     * 根据片键去查询数据
     *
     * @param splitKey 片键
     * @param iSelect 执行查询操作
     * @param <T>
     * @return
     */
    public static <T> List<T> queryFromSplitTable(long splitKey, ISelect iSelect) {
        TableIdContext.setId(splitKey);
        Object result = iSelect.doSelect();
        return (List<T>) result;
    }

    /**
     * 在分表中进行批量的id查询
     *
     * @param splitIds 片键id集合
     * @param tableEnum 表名枚举
     * @param iSelect 执行查询操作
     * @param <T>
     * @return
     */
    public static <T> List<T> queryFromSplitTable(List<Long> splitIds, TableEnum tableEnum, ISelect iSelect) {
        Set<Long> splitIdSet = new HashSet<>(splitIds);
        return queryFromSplitTable(splitIdSet, tableEnum, iSelect);
    }

    /**
     * 在分表中进行批量的id查询
     *
     * @param splitIdSet 片键id集合
     * @param tableEnum 表名枚举
     * @param iSelect 执行查询操作
     * @param <T>
     * @return
     */
    public static <T> List<T> queryFromSplitTable(Set<Long> splitIdSet, TableEnum tableEnum, ISelect iSelect) {
        List<T> finalList = new ArrayList<>();
        Map<String, List<Long>> tempMap = buildTableIndex(splitIdSet, tableEnum);
        CompletableFuture<List<T>>[] arr = new CompletableFuture[tempMap.size()];
        int i = 0;
        for (String tableIndex : tempMap.keySet()) {
            CompletableFuture<List<T>> completableFuture = CompletableFuture.supplyAsync(() -> {
                //定义分表的切片
                TableIdContext.setId(Long.valueOf(tableIndex));
                Object result = iSelect.doSelect();
                return (Collection<? extends T>) result;
            }).handle(new BiFunction<Collection<? extends T>, Throwable, List<T>>() {
                @Override
                public List<T> apply(Collection<? extends T> ts, Throwable throwable) {
                    if (ts != null || !ts.isEmpty()) {
                        finalList.addAll(ts);
                    }
                    return finalList;
                }
            });
            arr[i++] = completableFuture;
        }
        CompletableFuture.allOf(arr).join();
        return finalList;
    }

    private static Map<String, List<Long>> buildTableIndex(Set<Long> splitIds, TableEnum tableEnum) {
        Map<String, List<Long>> splitIdMap = new HashMap<>();
        for (Long splitId : splitIds) {
            splitIdMap.computeIfAbsent(String.valueOf(splitId % tableEnum.getMod()), k -> new LinkedList<>()).add(splitId);
        }
        return splitIdMap;
    }

}
