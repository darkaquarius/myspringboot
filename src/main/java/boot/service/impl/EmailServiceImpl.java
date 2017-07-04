package boot.service.impl;

import boot.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by huishen on 17/7/4.
 * 发送邮件
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendSimpleEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("darkaquarius88@163.com");
        message.setTo(to);
        message.setSubject("hello mail");
        message.setText("hello mail text");
        mailSender.send(message);
    }

    @Override
    public void sendEmailWithAttachment(String to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        // 类路径
        ClassPathResource image = new ClassPathResource("/attachment/attachment.jpg");
        // 文件系统
        // FileSystemResource image = new FileSystemResource("/coupon.jpg");

        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("darkaquarius88@163.com");
            helper.setTo(to);
            helper.setSubject("hello mail");
            // 简单文本
            // helper.setText("hello mail text");
            // html格式
            helper.setText("<html><body><img src='cid:logo'><h4>hello mail text</h4></body></html>", true);
            helper.addInline("logo", image);
            helper.addAttachment("attachment.jpg", image);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

    @Override
    public void sendEmailWithTemplate(String to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        Context ctx = new Context();
        ctx.setVariable("thText", "thTextValue");
        String emailText = templateEngine.process("mailTemplate.html", ctx);

        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("darkaquarius88@163.com");
            helper.setTo(to);
            helper.setSubject("hello mail");
            helper.setText(emailText, true);
            // mailSender
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);
    }

}
