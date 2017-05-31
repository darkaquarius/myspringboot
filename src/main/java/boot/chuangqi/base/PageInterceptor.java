package boot.chuangqi.base;


import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author libo.ding
 * @since 2016-08-20
 */
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    // @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class PageInterceptor implements Interceptor {

    private static Map<String, MappedStatement> COUNT_MS = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];

        // need pagination?
        PageList pageList = getPageList(param);
        if (pageList == null)
            return invocation.proceed();

        // pagination
        args[2] = RowBounds.DEFAULT; // remove unused RowBounds
        int page = pageList.getPage();
        int perPage = pageList.getPerPage();

        Configuration configuration = ms.getConfiguration();
        BoundSql boundSql = ms.getBoundSql(param);
        // get row count
        MappedStatement countMs = getCountMappedStatement(ms);
        String countSql = "select count(*) as count from (".concat(boundSql.getSql()).concat(") as x");
        BoundSql countBoundSql = new BoundSql(configuration, countSql, boundSql.getParameterMappings(), param);
        CacheKey countCacheKey = executor.createCacheKey(countMs, param, RowBounds.DEFAULT, countBoundSql);
        List countList = executor.query(countMs, param, rowBounds, resultHandler, countCacheKey, countBoundSql);
        if (countList == null || countList.size() != 1 || (int) countList.get(0) == 0) {
            pageList.setList(Collections.EMPTY_LIST);
            return Collections.EMPTY_LIST;
        }

        int rowCount = (int) countList.get(0);
        int pageCount = rowCount % perPage == 0 ? rowCount / perPage : rowCount / perPage + 1;
        pageList.setRowCount(rowCount);
        pageList.setPageCount(pageCount);

        if (page > pageCount)
            return Collections.EMPTY_LIST;

        int offset = (page - 1) * perPage;
        int limit = perPage;

        StringBuilder sb = new StringBuilder(boundSql.getSql());
        if (sb.charAt(sb.length() - 1) == ';')
            sb.deleteCharAt(sb.length() - 1);
        if (sb.charAt(sb.length() - 1) != ' ')
            sb.append(' ');
        sb.append("limit ").append(offset).append(", ").append(limit).append(";");
        BoundSql limitBoundSql = new BoundSql(configuration, sb.toString(), boundSql.getParameterMappings(), param);
        CacheKey limitCacheKey = executor.createCacheKey(ms, param, RowBounds.DEFAULT, limitBoundSql);
        List list = executor.query(ms, param, rowBounds, resultHandler, limitCacheKey, limitBoundSql);
        pageList.setList(list);

        return list;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private PageList getPageList(Object param) {
        if (param instanceof PageList)
            return (PageList) param;

        if (param instanceof HashMap) {
            HashMap<?, ?> hashMap = (HashMap) param;
            Optional optional = hashMap.values().stream().filter(val -> val instanceof PageList).findFirst();
            return optional.isPresent() ? (PageList) optional.get() : null;
        }

        return null;
    }

    private MappedStatement getCountMappedStatement(MappedStatement ms) {
        MappedStatement countMs = COUNT_MS.get(ms.getId());
        if (countMs != null)
            return countMs;

        Configuration configuration = ms.getConfiguration();
        String countMsId = ms.getId() + "_COUNT";
        String resultMapId = countMsId + "_INLINE";
        ResultMap resultMap = new ResultMap.Builder(configuration, resultMapId, Integer.class, Collections.emptyList()).build();
        countMs = new MappedStatement.Builder(ms.getConfiguration(), countMsId, ms.getSqlSource(), SqlCommandType.SELECT)
            .useCache(false)
            .statementType(ms.getStatementType())
            .resultMaps(Collections.singletonList(resultMap))
            .build();
        COUNT_MS.put(ms.getId(), countMs);

        return countMs;
    }
}
