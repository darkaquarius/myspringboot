package boot.controller;

import boot.domain.Address;
import boot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(path = "/add_address", method = RequestMethod.POST)
    public ResponseEntity addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // http://localhost:8088/address/get_address/1
    @RequestMapping(path = "/get_address/{id}", method = RequestMethod.GET)
    public Address getAddressById(@PathVariable("id") int id) {
        Address address = addressService.selectAddress(id);
        return address;
    }

}
