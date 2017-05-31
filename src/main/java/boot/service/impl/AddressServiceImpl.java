package boot.service.impl;

import boot.domain.Address;
import boot.mapper.AddressMapper;
import boot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 16/10/13.
 */
@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressMapper addressMap;

    @Override
    public int addAddress(Address address) {
        return addressMap.addAddress(address);
    }

    @Override
    public Address selectAddress(int id){
        Address address = addressMap.selectAddress(id);
        return address;
    }

}
