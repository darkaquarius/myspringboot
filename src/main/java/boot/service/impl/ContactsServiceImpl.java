package boot.service.impl;

import boot.domain.Contacts;
import boot.mapper.ContactsMapper;
import boot.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void addContracts(Contacts contacts) {
        contactsMapper.addContracts(contacts);
    }

    @Override
    public List<Contacts> selectContactsByUserId(int userId) {
        return contactsMapper.selectContactsByUserId(userId);
    }

}
