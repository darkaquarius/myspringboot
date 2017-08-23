package boot.controller;

import boot.domain.ContactsText;
import boot.service.ContactsTextService;
import boot.util.ObjectMapperUtil;
import boot.vo.ContactsTextVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huishen on 17/7/28.
 *
 */

@RestController
@RequestMapping("/contacts_text")
public class ContactsTextController {

    @Autowired
    private ContactsTextService contactsTextService;

    @RequestMapping(path = {"/add", "/add/"}, method = RequestMethod.POST)
    public ResponseEntity addContractsText(@RequestBody ContactsTextVo contactsTextVo) {
        String text = ObjectMapperUtil.writeValueAsString(contactsTextVo.getText());
        ContactsText contactsText = new ContactsText(contactsTextVo.getUserId(), text);
        int ret = contactsTextService.addContractsText(contactsText);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(path = {"/{user_id}","/user_id/"}, method = RequestMethod.GET)
    public ContactsText getContactsText(@PathVariable("user_id") int userId) {
        ContactsText contactsText = contactsTextService.selectContactsTextByUserId(userId);
        return contactsText;
    }

}
