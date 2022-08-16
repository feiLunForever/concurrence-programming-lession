package 并发编程15.分库分表.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import 并发编程15.分库分表.dao.UserMessageMapper;
import 并发编程15.分库分表.po.UserMessagePO;
import 并发编程15.分库分表.service.IUserMessageService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.stream.Stream;


/**
 * @Author linhao
 * @Date created in 10:08 下午 2022/8/9
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessagePO> implements IUserMessageService {


    @Override
    public void doQueryFromSplitTable(Long userId) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("userId", userId);
        //定义分表的切片
        paramMap.put("tableIndex", convertTableIndexName(String.valueOf(userId % 10000)));
        List<UserMessagePO> userMessagePOS = getBaseMapper().selectByUserId(paramMap);
        System.out.println(userMessagePOS);
    }

    @Override
    public List<UserMessagePO> selectNotReply(Set<Long> userIdS) {
        List<UserMessagePO> finalList = new ArrayList<>();
        Map<String,List<Long>> userIdMap = buildTableIndex(userIdS);
        for (String tableIndex : userIdMap.keySet()) {
            List<Long> userIdList = userIdMap.get(tableIndex);
            Map<String, Object> paramMap = new HashMap<>(2);
            paramMap.put("userIdList", userIdList);
            //定义分表的切片
            paramMap.put("tableIndex", tableIndex);
            List<UserMessagePO> userMessagePOS = getBaseMapper().selectNotReply(paramMap);
            finalList.addAll(userMessagePOS);
            System.out.println("tableIndex" + tableIndex);
        }
        System.out.println(finalList);
        return finalList;
    }

    @Override
    public List<UserMessagePO> selectNotReplyAsync(Set<Long> userIdS) {
        List<UserMessagePO> finalList = new CopyOnWriteArrayList<>();
        Map<String,List<Long>> userIdMap = buildTableIndex(userIdS);
        CompletableFuture[] arr = new CompletableFuture[userIdMap.size()];
        List<CompletableFuture<List<UserMessagePO>>> completableFutures = new ArrayList<>();
        int i =0 ;
        for (String tableIndex : userIdMap.keySet()) {
            List<Long> userIdList = userIdMap.get(tableIndex);
            CompletableFuture<List<UserMessagePO>> userMessagePoFuture =CompletableFuture.supplyAsync(()->{
                Map<String, Object> paramMap = new HashMap<>(2);
                paramMap.put("userIdList", userIdList);
                //定义分表的切片
                paramMap.put("tableIndex", tableIndex);
                List<UserMessagePO> userMessagePOS = getBaseMapper().selectNotReply(paramMap);
                return userMessagePOS;
            }).handle(new BiFunction<List<UserMessagePO>, Throwable, List<UserMessagePO>>() {
                @Override
                public List<UserMessagePO> apply(List<UserMessagePO> userMessagePOS, Throwable throwable) {
                    finalList.addAll(userMessagePOS);
                    return finalList;
                }
            });
            arr[i++] = userMessagePoFuture;
        }

        CompletableFuture.allOf(arr).whenComplete((v,th) ->{
            System.out.println("end");
        }).join();
        return null;
    }



    private static Map<String,List<Long>> buildTableIndex(Set<Long> userIdS){
        Map<String, List<Long>> userIdMap = new HashMap<>();
        for (Long userId : userIdS) {
            String tableIndex = convertTableIndexName(String.valueOf(userId % 10000));
            List<Long> tempList = userIdMap.get(tableIndex);
            if (tempList == null) {
                tempList = new ArrayList<>(1);
                tempList.add(userId);
                userIdMap.put(tableIndex, tempList);
            } else {
                tempList.add(userId);
                userIdMap.put(tableIndex, tempList);
            }
        }
        return userIdMap;
    }

    private static String convertTableIndexName(String currentIndex) {
        if (currentIndex.length() == 4) {
            return currentIndex;
        } else {
            StringBuffer stb = new StringBuffer();
            for (int i = 0; i < 4 - currentIndex.length(); i++) {
                stb.append("0");
            }
            stb.append(currentIndex);
            return stb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(20500 + 4 * 200 + 600 + 27 * 50 + 800 );
        System.out.println((80 + 150) * 2 * 4 );
    }
}
