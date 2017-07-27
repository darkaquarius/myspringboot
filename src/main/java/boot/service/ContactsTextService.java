package boot.service;

import boot.domain.ContactsText;

/**
 * Created by huishen on 17/7/27.
 *
 */

public interface ContactsTextService {

    int addContractsText(ContactsText contactsText);

    ContactsText selectContactsTextByUserId(int userId);

}
