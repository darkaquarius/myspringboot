package boot.controller;

import boot.domain.Address;
import boot.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huishen on 16/10/13.
 *
 */
@RestController
@RequestMapping("/address")
@ManagedResource(objectName = "address:name=addressController")  // JMX
public class AddressController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int perPage = 25;

    @ManagedAttribute   // JMX
    public int getPerPage() {
        return perPage;
    }

    @ManagedAttribute    // JMX
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    @Autowired
    private AddressService addressService;

    @RequestMapping(path = "/add_address", method = RequestMethod.POST)
    public ResponseEntity addAddress(@RequestBody Address address){
        addressService.addAddress(address);


        return new ResponseEntity(HttpStatus.CREATED);
    }

    // http://localhost:8088/address/get_address/1
    @RequestMapping(path = "/get_address/{id}", method = RequestMethod.GET)
    public ResponseEntity<Address> getAddressById(@PathVariable("id") int id) {
        Address address = addressService.selectAddress(id);

        // 返回中增加消息头
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("custom-header", "60");

        return new ResponseEntity<>(address, responseHeaders, HttpStatus.OK);
    }

}
