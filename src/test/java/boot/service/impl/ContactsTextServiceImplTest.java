package boot.service.impl;

import boot.BaseTest;
import boot.domain.ContactsText;
import boot.service.ContactsTextService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 17/7/27.
 *
 */

public class ContactsTextServiceImplTest extends BaseTest {

    @Autowired
    private ContactsTextService contactsTextService;

    private  StringBuffer sb = new StringBuffer();

    @Before
    public void before() {
        for (int i = 0; i < 200; i++) {
            sb.append("{\"name\":\"张三\",\"tel\":\"13888888888\"}");
        }
    }

    @Test
    public void testAddContractsText() {
        for (int i = 0; i < 200; i++) {
            ContactsText contactsText = ContactsText.builder()
                .userId(i)
                .text(sb.toString())
                .build();
            int ret = contactsTextService.addContractsText(contactsText);
        }

        System.out.println();
    }

    @Test
    public void testSelectContactsTextByUserId() {
        ContactsText contactsText = contactsTextService.selectContactsTextByUserId(50);
        System.out.println(contactsText);
    }

}
