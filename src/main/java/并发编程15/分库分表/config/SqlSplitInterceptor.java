package 并发编程15.分库分表.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import jdk.nashorn.internal.ir.Splittable;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 分表拦截器
 *
 * @Author linhao
 * @Date created in 12:29 下午 2022/8/10
 */
public class SqlSplitInterceptor implements InnerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlSplitInterceptor.class);

    private ApplicationContext applicationContext;

    public SqlSplitInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        TableIdContext.setId(10);
        String mapperClassName = ms.getResource();
        //达成约束，所有的dao层都是PO对象前缀加mapper
        String mapperName = mapperClassName.substring(mapperClassName.lastIndexOf("/") + 1, mapperClassName.lastIndexOf("."));
        BaseMapper<?> baseMapper = (BaseMapper<?>) applicationContext.getBean(toLowerCaseFirstOne(mapperName));
        try {
            Class mapperClass = Class.forName(mapperClassName.replaceAll("/", ".").substring(0, mapperClassName.lastIndexOf(".")));
            SplitTable splitTable = (SplitTable) mapperClass.getAnnotation(SplitTable.class);
            if (splitTable != null) {
                System.out.println(splitTable);
                Class splitClass = splitTable.clazz();
                for (Field declaredField : splitClass.getDeclaredFields()) {
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                        SplitId splitId = declaredField.getAnnotation(SplitId.class);
                        if (splitId != null) {
                            splitId.mod();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }



    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        LOGGER.info("#####beforeQuery");
    }

    @Override
    public boolean willDoUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        LOGGER.info("#####willDoUpdate");
        // 一堆sql处理仅供参考
        BoundSql boundSql = ms.getBoundSql(parameter);
        ms.getSqlSource().getClass();
        String sql = boundSql.getSql();
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);
        LOGGER.info("sql:{}", sql);
        return false;
    }


    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) {
        LOGGER.info("#####beforeUpdate");
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        LOGGER.info("#####beforePrepare");
    }


    @Override
    public void setProperties(Properties properties) {
        LOGGER.info("#####setProperties");
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
