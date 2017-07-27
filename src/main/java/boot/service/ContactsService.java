package boot.service;

import boot.domain.Contacts;

import java.util.List;

/**
 * Created by huishen on 17/7/27.
 *
 */
public interface ContactsService {

    void addContractsByUserId(int userId);

    void addContracts(Contacts contacts);

    List<Contacts> selectContactsByUserId(int userId);
}
