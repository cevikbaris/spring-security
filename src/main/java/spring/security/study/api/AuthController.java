package spring.security.study.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.security.study.entity.Otp;
import spring.security.study.entity.Users;
import spring.security.study.service.UserServiceImpl;

/**
    * Yeni user kayıt etme ve şifreyi encrypt üretme
    * User ile birlikte otp üretme veya güncelleme
    * Otp kontrolü

 */

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody Users user) {
        userService.addUser(user);
        logger.info("User kaydedildi");
        return ResponseEntity.ok("User Kaydedildi");
    }

    @PostMapping("/user/auth")
    public ResponseEntity<String> auth(@RequestBody Users user) {
        Otp createdOtp = userService.auth(user); // otp üretir veya günceller
        logger.info("OTP oluşturuldu");
        return ResponseEntity.ok("OTP oluşturuldu. OTP:"+createdOtp.getCode());

    }


    @PostMapping("/otp/check")
    public ResponseEntity<String> check(@RequestBody Otp otp) {
        if (userService.check(otp)) {
            return ResponseEntity.ok("OTP doğrulandı");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("OTP geçersiz");
        }
    }

}