package boot.mapper;

import boot.domain.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * Created by huishen on 16/10/10.
 *
 */
public interface AddressMapper{

    @Insert("insert into address(user_id, country, province, city) values (#{userId}, #{country}, #{province}, #{city})")
    int addAddress(Address address);

    @ResultMap("AddressResult")
    @Select("select id, user_id, country, province, city, update_time, create_time from address where id = #{id}")
    Address selectAddress(int id);

    @Select("select * from address where city is #{city}")
    Address find(@Param(value = "city") String city);

}
