package spring.security.study.service;

import org.springframework.stereotype.Service;
import spring.security.study.entity.Otp;
import spring.security.study.entity.Users;

@Service
public interface UserService {

    void addUser(Users user);
    Otp auth(Users user);
    boolean check(Otp otp);

}
