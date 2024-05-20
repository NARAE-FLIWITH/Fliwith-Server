package com.narae.fliwith.service;

import com.narae.fliwith.domain.User;
import com.narae.fliwith.exception.user.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MailService {
    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String email;

    private MimeMessage createMessage(User user) throws MessagingException, UnsupportedEncodingException {
        //TODO: 서버 주소로 바꾸기
        String content = "<div>"
                + "<h1> 안녕하세요. Fliwith 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='http://localhost:8080/user/authemail?auth=" + user.getAuth() + "'>인증 링크</a>"
                + "</div>";


        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Fliwith 회원가입 이메일 인증:"); //메일 제목
        message.setText(content, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(email,"Fliwith_Official")); //보내는 사람의 메일 주소, 보내는 사람 이름

        log.info("message : " + message);
        return message;
    }

    //메일 발송
    public void sendMail(User user) {
        try{
            MimeMessage mimeMessage = createMessage(user);
            log.info("ok");
            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            e.printStackTrace();
            throw new EmailSendException();
        }
    }
}
