package boot.service.impl;

import boot.mapper.ContactsMapper;
import boot.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/7/27.
 *
 */

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    public void addContractsByUserId(int userId) {
        contactsMapper.addContractsByUserId(userId);
    }

}
