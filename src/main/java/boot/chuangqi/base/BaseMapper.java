package boot.chuangqi.base;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author libo.ding
 * @since 2016-08-15
 */
public interface BaseMapper<T> {

    @ResultMap("BaseResultMap")
    @SelectProvider(type = BaseProvider.class, method = "selectByPrimaryKey")
    T selectByPrimaryKey(T record);

    @InsertProvider(type = BaseProvider.class, method = "insertSelective")
    int insertSelective(T record);

    @UpdateProvider(type = BaseProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(T record);

    @UpdateProvider(type = BaseProvider.class, method = "updateByPrimaryKey")
    int updateByPrimaryKey(T record);

    @ResultMap("BaseResultMap")
    @Select("select $COLUMNS$ from $TABLE_NAME$")
    List<T> selectAll(PageList<T> pageList);

    @Deprecated
    @ResultMap("BaseResultMap")
    @Select("select $COLUMNS$ from $TABLE_NAME$ where ${where} order by ${orderBy}")
    List<T> selectByConditions(PageList<T> pageList, String where, String orderBy);

    @ResultMap("BaseResultMap")
    @SelectProvider(type = BaseProvider.class, method = "selectByEntity")
    List<T> selectByEntity(T t);
}
