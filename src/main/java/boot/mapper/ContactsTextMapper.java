package boot.mapper;

import boot.domain.ContactsText;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by huishen on 17/7/27.
 *
 */

public interface ContactsTextMapper {

    @Insert("insert into contacts_text(user_id) VALUES (#{userId})")
    int addContractsTextByUserId(int userId);

    @Insert("INSERT INTO contacts_text(user_id, text, info_date) VALUES (#{userId}, #{text}, #{infoDate})")
    int addContractsText(ContactsText contactsText);

    @Select("select * from contacts_text where user_id=#{userId}")
    ContactsText selectContactsTextByUserId(int userId);

}
