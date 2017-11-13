package boot.service;

import boot.BaseTest;
import boot.domain.Address;
import boot.service.impl.AddressServiceImpl;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by huishen on 16/10/13.
 *
 */
@WebAppConfiguration
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
        Address address = addressService.selectAddress(1);
    }

    @Test
    public void testClazzCast() {
        AddressServiceImpl instantiate = BeanUtils.instantiate(AddressServiceImpl.class);
        Address cast = Address.class.cast(instantiate);
    }

}
