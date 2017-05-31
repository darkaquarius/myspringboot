package boot.service;

import boot.BaseTest;
import boot.domain.Address;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 16/10/13.
 */
public class AddressServiceTest extends BaseTest{

    @Autowired
    private AddressService addressService;

    @Test
    public void testAddAddress(){
        Address address = new Address(1);
        address.setUserId(2);
        address.setCountry("China");
        address.setProvince("Jiangsu");
        address.setCity("Changzhou");
        addressService.addAddress(address);
    }

    @Test
    public void testSelectAddress(){
        addressService.selectAddress(1);
    }

}
