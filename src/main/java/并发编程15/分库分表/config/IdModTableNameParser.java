package 并发编程15.分库分表.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

public class IdModTableNameParser implements TableNameHandler {

    private Integer mod;

    public IdModTableNameParser(Integer mod) {
        this.mod = mod;
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        Integer idValue = TableIdContext.getId();
        if (idValue == null) {
            throw new RuntimeException("请设置id值");
        } else {
            String suffix = String.valueOf(idValue % mod);
            //这里清除ThreadLocal的值，防止线程复用出现问题
            TableIdContext.clean();
            if (suffix.length() < 4) {
                String prefix = "";
                for (int i = 0; i < 4 - suffix.length(); i++) {
                    prefix = prefix + "0";
                }
                suffix = prefix + suffix;
            }
            return tableName + "_" + suffix;
        }
    }
}