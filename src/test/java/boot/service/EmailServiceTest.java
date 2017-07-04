package boot.service;

import boot.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huishen on 17/7/4.
 *
 */
public class EmailServiceTest extends BaseTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendSimpleEmail() {
        emailService.sendSimpleEmail("626823121@qq.com");
    }

    @Test
    public void testSendEmailWithAttachment() {
        emailService.sendEmailWithAttachment("626823121@qq.com");
    }

    @Test
    public void testSendEmailWithTemplate() {
        emailService.sendEmailWithTemplate("626823121@qq.com");
    }

}