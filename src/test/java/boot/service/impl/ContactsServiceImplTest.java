package boot.service.impl;

import boot.BaseTest;
import boot.domain.Contacts;
import boot.service.ContactsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huishen on 17/7/27.
 *
 */

public class ContactsServiceImplTest extends BaseTest {

    @Autowired
    private ContactsService contactsService;

    @Test
    public void testAddContractsByUserId() {
        contactsService.addContractsByUserId(1);
    }

    @Test
    public void testAddContracts() {
        for (int i = 0; i < 200; i++) {
            Contacts contacts = Contacts.builder()
                .userId(1)
                .contactName("zhangsan")
                .contactTel(i)
                .build();
            contactsService.addContracts(contacts);
        }

    }

    @Test
    public void test() {
        List<Contacts> contacts = contactsService.selectContactsByUserId(1);
        System.out.println(contacts);
    }

}
