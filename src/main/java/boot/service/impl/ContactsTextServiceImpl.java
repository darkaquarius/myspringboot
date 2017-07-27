package boot.service.impl;

import boot.domain.ContactsText;
import boot.mapper.ContactsTextMapper;
import boot.service.ContactsTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/7/27.
 *
 */

@Service
public class ContactsTextServiceImpl implements ContactsTextService {

    @Autowired
    private ContactsTextMapper contactsTextMapper;

    @Override
    public int addContractsText(ContactsText contactsText) {
        return contactsTextMapper.addContractsText(contactsText);
    }

    @Override
    public ContactsText selectContactsTextByUserId(int userId) {
        return contactsTextMapper.selectContactsTextByUserId(userId);
    }

}
