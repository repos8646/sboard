package com.sboard.service;

import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;



    // JavaMailSender 주입
    private final JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;


    public void insertUser(UserDTO userDTO) {

        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);
        userRepository.save(userDTO.toEntity());
    }

    public int selectCountUser(String type, String value) {
        int count = 0;

        if(type.equals("uid")) {
            count = userRepository.countByUid(value);
        }else if(type.equals("nick")) {
            count = userRepository.countByNick(value);
        }else if(type.equals("hp")) {
            count = userRepository.countByHp(value);
        }else if(type.equals("email")) {
            count = userRepository.countByEmail(value);
        }
        return count;
    }

    /*
        - build.gradle 파일에 spring-boot-starter-mail 의존성 추가 할것
        - application.yml 파일 spring email 관련 설정
     */
    @Value("${spring.mail.username}")
    private String sender;
    public void sendEmailCode(HttpSession session, String receiver) {
        log.info("sender: "+sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));
        log.info("code: "+code);

        String title = "sboard 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setText(content, "text/html; charset=utf-8");

            // 메일 발송
            javaMailSender.send(message);
        }catch (Exception e){
            log.error("sendEmailCode" + e.getMessage());
        }

    }

}
