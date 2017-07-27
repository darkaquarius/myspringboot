package boot.mapper;

import boot.BaseTest;
import boot.domain.Contacts;
import boot.domain.ContactsText;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huishen on 17/7/27.
 *
 */

public class ContactsTextMapperTest extends BaseTest {

    @Autowired
    private ContactsTextMapper contactsTextMapper;

    @Test
    public void testAddContractsTextByUserId() {
        contactsTextMapper.addContractsTextByUserId(1);
    }

    @Test
    public void testAddContractsText() {
        ContactsText contactsText = ContactsText.builder()
            .userId(1)
            .text("1")
            .build();

        int i = contactsTextMapper.addContractsText(contactsText);
        Assert.assertEquals(i, 1);
    }

    @Test
    public void testSelectContactsTextByUserId() {
        ContactsText contactsText = contactsTextMapper.selectContactsTextByUserId(1);
        System.out.println(contactsText);
    }

}
