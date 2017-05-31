package boot.mapper;

import boot.domain.Address;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 17/1/13.
 */
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void test(){
        String city = null;
        Address address = addressMapper.find(city);
        System.out.println(address);
    }
}
