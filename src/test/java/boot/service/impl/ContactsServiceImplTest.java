package boot.service.impl;

import boot.BaseTest;
import boot.service.ContactsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

}
