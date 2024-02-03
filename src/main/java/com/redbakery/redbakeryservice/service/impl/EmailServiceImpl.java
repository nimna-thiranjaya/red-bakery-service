package com.redbakery.redbakeryservice.service.impl;

import com.redbakery.redbakeryservice.config.FreeMakerConfig;
import com.redbakery.redbakeryservice.exception.InternalServerErrorException;
import com.redbakery.redbakeryservice.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendEmail(String sendTo, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(sendTo);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            javaMailSender.send(mailMessage);


        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void sendEmailWithAttachment(String sendTo, String subject, String message, String attachmentPath) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(message);

            File file = new File(attachmentPath);
            helper.addAttachment(file.getName(), file);

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void sendHtmlTemplateEmail(String sendTo, String subject, String templateName, HashMap<String, Object> model) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

//            helper.addAttachment("logo.png", new File("src/main/resources/static/images/logo.png"));

            Template template = configuration.getTemplate("email-templates/"+templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(sendTo);
            helper.setText(html, true);
            helper.setSubject(subject);

            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }

    }
}
