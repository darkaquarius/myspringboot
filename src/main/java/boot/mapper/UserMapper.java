package boot.mapper;

import boot.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by huishen on 16/10/10.
 *
 */
public interface UserMapper{

    @Insert("insert into user(name, password, age, sex, job, remark) values (#{name}, #{password}, #{age}, #{sex}, #{job}, #{remark})")
    int addUser(User user);

    @Update("update user set name=#{name}, password=#{password}, age=#{age}, sex=#{sex}, job=#{job}, remark=#{remark} where id=#{id}")
    int updateUser(User user);

    @Select("select id, name, password, age, sex, job, remark, update_time, create_time from user where id=#{id}")
//    @Results(value = {
//            @Result(id = true, column = "id", property = "id",javaType = Integer.class, jdbcType = JdbcType.INTEGER),
//            @Result(column = "name", property = "name", javaType = String.class, jdbcType = JdbcType.CHAR),
//            @Result(column = "password", property = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
//            @Result(column = "age", property = "age", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
//            @Result(column = "remark", property = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
//            @Result(column="update_time", property="updateTime", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP),
//            @Result(column="create_time", property="createTime", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP)})
    @ResultMap(value = "userResult")
    User selectByPrimaryKey(int id);
}
