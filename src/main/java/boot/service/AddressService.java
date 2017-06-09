package boot.service;

import boot.domain.Address;

/**
 * Created by huishen on 16/10/13.
 *
 */
public interface AddressService {

    int addAddress(Address address);

    Address selectAddress(int id);

}
