package boot.mapper;

import org.apache.ibatis.annotations.Insert;

/**
 * Created by huishen on 17/7/27.
 *
 */
public interface ContactsMapper {

    @Insert("insert into contacts(user_id) VALUES (#{userId})")
    int addContractsByUserId(int userId);

}
