package boot.mapper;

import boot.domain.Contacts;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by huishen on 17/7/27.
 *
 */
public interface ContactsMapper {

    @Insert("insert into contacts(user_id) VALUES (#{userId})")
    int addContractsByUserId(int userId);

    @Insert("INSERT INTO contacts(user_id, contact_name, contact_tel) VALUES (#{userId}, #{contactName}, #{contactTel})")
    int addContracts(Contacts contacts);

    @Select("select * from contacts where user_id=#{userId}")
    List<Contacts> selectContactsByUserId(int userId);

}
