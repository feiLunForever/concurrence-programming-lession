package 并发编程15.分库分表.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import 并发编程15.分库分表.dao.UserMessageMapper;
import 并发编程15.分库分表.po.UserMessagePO;
import 并发编程15.分库分表.service.IUserMessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        paramMap.put("table_index", convertTableIndexName(String.valueOf(userId % 10000)));
        List<UserMessagePO> userMessagePOS = getBaseMapper().selectByUserId(paramMap);
        System.out.println(userMessagePOS);
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
        int i=9000001;
        System.out.println(convertTableIndexName(String.valueOf(i % 10000)));
        System.out.println(convertTableIndexName("1"));
        System.out.println(convertTableIndexName("11"));
        System.out.println(convertTableIndexName("112"));
        System.out.println(convertTableIndexName("1124"));
    }
}
