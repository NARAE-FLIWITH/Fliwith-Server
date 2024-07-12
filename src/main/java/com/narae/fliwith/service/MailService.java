package com.narae.fliwith.service;

import com.narae.fliwith.domain.User;
import com.narae.fliwith.exception.user.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MailService {
    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String email;
    @Value("${email.server}")
    private String server;

    private final PasswordEncoder passwordEncoder;

    private static final char[] rndAllCharacters = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',

            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            };


    private static final char[] numberCharacters = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final char[] uppercaseCharacters = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final char[] lowercaseCharacters = new char[] {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };


    private MimeMessage createMessage(User user) throws MessagingException, UnsupportedEncodingException {
        String content = "<div>"
                + "<h1> 안녕하세요. Fliwith 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='"+server + user.getAuth() + "'>인증 링크</a>"
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

    private String createPw() {
        StringBuilder pwd = new StringBuilder();
        SecureRandom random = new SecureRandom();
        List<Character> passwordCharacters = new ArrayList<>();

        int numberCharactersLength = numberCharacters.length;
        passwordCharacters.add(numberCharacters[random.nextInt(numberCharactersLength)]);

        int uppercaseCharactersLength = uppercaseCharacters.length;
        passwordCharacters.add(uppercaseCharacters[random.nextInt(uppercaseCharactersLength)]);

        int lowercaseCharactersLength = lowercaseCharacters.length;
        passwordCharacters.add(lowercaseCharacters[random.nextInt(lowercaseCharactersLength)]);

        int rndAllCharactersLength = rndAllCharacters.length;
        for (int i = 0; i < 10; i++) {
            passwordCharacters.add(rndAllCharacters[random.nextInt(rndAllCharactersLength)]);
        }

        Collections.shuffle(passwordCharacters);

        for (Character character : passwordCharacters) {
            pwd.append(character);
        }
        log.warn("randomPw={}", pwd.toString());
        return pwd.toString();
    }

    public void tempoaryPassword(User user) {

        String pw = createPw();
        try{
            MimeMessage mimeMessage = createTemporaryPasswordMessage(user, pw);
            javaMailSender.send(mimeMessage);
            String encodedPwd = passwordEncoder.encode(pw);
            user.changePWd(encodedPwd);
        }catch (Exception e){
            e.printStackTrace();
            throw new EmailSendException();
        }
    }

    private MimeMessage createTemporaryPasswordMessage(User user, String initPassword)
            throws MessagingException, UnsupportedEncodingException {
        String content = "<head> <link rel='stylesheet' type='text/css'></head>\n"
                + "<div> <h1 style=\"text-align: center;\"> 안녕하세요. Fliwith 입니다</h1>\n"
                + "                <br>\n"
                + "<div style=\"text-align: center; font-weight: 400;\">\n"
                + "                아래 임시 비밀번호를 로그인 시 사용해주세요.\n"
                + "<br>로그인 후 비밀번호를 꼭 변경해주세요.\n"
                + "</div>\n"
                + "        <div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\">\n"
                + "        <table style=\"border-collapse: collapse; border: 0; background-color: #C1BAE5; height: 61px; table-layout: fixed; word-wrap: break-word; border-radius: 15px; margin-top: 10px; margin-left:auto; margin-right:auto;\"><tbody> <tr><td style = \"text-align: center; vertical-align: middle; font-size: 32px; color: #FFFFFF; font-weight: 500; padding-left: 109px; padding-right: 109px; padding-top: 11px; padding-bottom: 12px; text-align: center;\">\n"
                + initPassword+"\n"
                + "        </td></tr></tbody></table></div>\n"
                + "                </div>";


        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Fliwith 임시 비밀번호:"); //메일 제목
        message.setText(content, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(email,"Fliwith_Official")); //보내는 사람의 메일 주소, 보내는 사람 이름

        log.info("message : " + message);
        return message;
    }


}
