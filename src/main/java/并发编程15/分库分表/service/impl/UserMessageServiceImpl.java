package 并发编程15.分库分表.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import 并发编程15.分库分表.config.TableEnum;
import 并发编程15.分库分表.config.TableIdContext;
import 并发编程15.分库分表.dao.UserMessageMapper;
import 并发编程15.分库分表.po.UserMessagePO;
import 并发编程15.分库分表.service.IUserMessageService;
import 并发编程15.分库分表.utils.DbUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;


/**
 * @Author linhao
 * @Date created in 10:08 下午 2022/8/9
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessagePO> implements IUserMessageService {

    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    @Override
    public List<UserMessagePO> selectByUserId(Long userId) {
        //定义分表的切片
        return DbUtils.queryFromSplitTable(userId, () -> getBaseMapper().selectByUserId(userId));
    }

    @Override
    public List<UserMessagePO> selectNotRead(Set<Long> userIdS) {
        //定义分表的切片
        return DbUtils.queryFromSplitTable(userIdS, TableEnum.T_USER_MESSAGE, () -> getBaseMapper().selectNotRead(userIdS));
    }

    @Override
    public void insertData() {
        //模拟生成测试数据，量不需要太大
        for (int i = 1; i < 100000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        UserMessagePO userMessagePO = new UserMessagePO();
                        userMessagePO.setUserId(RandomUtils.nextLong(100000000, 200000000));
                        userMessagePO.setObjectId(RandomUtils.nextLong(100000000, 200000000));
                        userMessagePO.setStatus(1);
                        userMessagePO.setExtJson("{\"shareId\":19891023,\"shareTime\":2022-01-01 11:00:00}");
                        userMessagePO.setType(0);
                        userMessagePO.setContent("测试内容");
                        getBaseMapper().insert(userMessagePO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void splitFromSourceTable() {
        int size = 1000;
        int beginIndex = 0;
        while (true) {
            LambdaQueryWrapper<UserMessagePO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.last("limit " + beginIndex + "," + size + "");
            List<UserMessagePO> userMessagePOS = getBaseMapper().selectList(lambdaQueryWrapper);
            for (UserMessagePO userMessagePO : userMessagePOS) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("写入数据");
                        userMessagePO.setRelationId(RandomUtils.nextLong(10000000, 20000000));
                        getBaseMapper().insertToSplitTable(userMessagePO, convertTableIndexName(String.valueOf(userMessagePO.getUserId() % 1000)));
                    }
                });
            }
            if (userMessagePOS.size() < size) {
                break;
            }
            beginIndex += size;
        }
        System.out.println("结束");
    }

    private static Map<String, List<Long>> buildTableIndex(Set<Long> userIdS) {
        Map<String, List<Long>> userIdMap = new HashMap<>();
        for (Long userId : userIdS) {
            String tableIndex = convertTableIndexName(String.valueOf(userId % 1000));
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
        return currentIndex;
    }


    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(50);
        for (int i = 500; i < 1000; i++) {
            String createTable = "CREATE TABLE t_user_message_" + i + " ( " +
                    "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '发信方id',\n" +
                    "  `object_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收信方id',\n" +
                    "  `relation_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '关联id',\n" +
                    "  `is_read` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否已读（0未读，1已读）',\n" +
                    "  `sid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '消息条数',\n" +
                    "  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态（0无效 1有效）',\n" +
                    "  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '消息内容',\n" +
                    "  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '类型（0文本，1语音，2图片，3视频，4表情，5分享链接）',\n" +
                    "  `ext_json` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '扩展字段',\n" +
                    "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                    "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `idx_user_id` (`user_id`) USING BTREE COMMENT '发信方id索引',\n" +
                    "  KEY `idx_object_id` (`object_id`) USING BTREE COMMENT '收信方id索引',\n" +
                    "  KEY `idx_relation_id` (`relation_id`) USING BTREE COMMENT '关联id索引'\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=100009 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户消息表';";
            System.out.println(createTable);
        }

    }

}
