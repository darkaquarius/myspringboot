package boot.service;

/**
 * Created by huishen on 17/7/4.
 * 发送邮件
 */
public interface EmailService {

    void sendSimpleEmail(String to);

    void sendEmailWithAttachment(String to);

    void sendEmailWithTemplate(String to);

}
