package com.ashar.job.recruitment.management.Service.mail;

import com.ashar.job.recruitment.management.Exception.FailedProcessException;
import com.ashar.job.recruitment.management.Util.HtmlUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendHtmlEmail(String email, String html) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("jobrecruitmenatportal@gmail.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("Job Application Update");
            mimeMessageHelper.setText(html,true);

            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            log.error("Email delivery exception caught: {}",e.getMessage());
            throw new FailedProcessException("Email was not delivered.");
        }
    }
}
