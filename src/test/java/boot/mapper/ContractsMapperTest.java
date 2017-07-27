package boot.mapper;

import boot.BaseTest;
import boot.domain.Contacts;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 17/7/27.
 *
 */
public class ContractsMapperTest extends BaseTest {

    @Autowired
    private ContactsMapper contactsMapper;

    @Test
    public void testAddContractsByUserId() {
        int i = contactsMapper.addContractsByUserId(1);
        System.out.println(i);
    }

}
