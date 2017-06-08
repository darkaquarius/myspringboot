package boot.mapper;

import boot.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * Created by huishen on 16/10/10.
 *
 */
public interface UserMapper{

    @Insert("insert into user(name, password, age, sex, job, remark) values (#{name}, #{password}, #{age}, #{sex}, #{job}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);

    @Update("update user set name=#{name}, password=#{password}, age=#{age}, sex=#{sex}, job=#{job}, remark=#{remark} where id=#{id}")
    int updateUser(User user);

   @Results(value = {
           @Result(id = true, column = "id", property = "id",javaType = Integer.class, jdbcType = JdbcType.INTEGER),
           @Result(column = "name", property = "name", javaType = String.class, jdbcType = JdbcType.CHAR),
           @Result(column = "password", property = "password", javaType = String.class, jdbcType = JdbcType.VARCHAR),
           @Result(column = "age", property = "age", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
           @Result(column = "remark", property = "remark", javaType = String.class, jdbcType = JdbcType.VARCHAR),
           @Result(column="update_time", property="updateTime", javaType = Date.class, jdbcType = JdbcType.DATE),
           @Result(column="create_time", property="createTime", javaType = Date.class, jdbcType = JdbcType.DATE)})
//     @ResultMap(value = "userResult")
    @Select("select id, name, password, age, sex, job, remark, update_time, create_time from user where id=#{id}")
    User selectByPrimaryKey(int id);
}
