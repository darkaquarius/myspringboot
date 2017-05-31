package boot.service;

import boot.domain.Address;
import boot.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 16/10/13.
 */
public interface AddressService {

    int addAddress(Address address);

    Address selectAddress(int id);

}
