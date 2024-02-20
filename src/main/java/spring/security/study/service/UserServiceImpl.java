package spring.security.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.security.study.common.GenerateCodeUtil;
import spring.security.study.entity.Otp;
import spring.security.study.entity.Users;
import spring.security.study.repository.AuthorityRepository;
import spring.security.study.repository.OtpRepository;
import spring.security.study.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    @Override
    public void addUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled("Y");
        user.setAuthority(authorityRepository.findByAuthorityName("ROLE_USER").get());
        userRepository.save(user);

    }

    @Override
    public Otp auth(Users user) {
        Optional<Users> o =userRepository.findUserByUsername(user.getUsername());
        if(o.isPresent()) {
            Users u = o.get();
            if (passwordEncoder.matches(user.getPassword(),u.getPassword())) { // eğer password doğruysa otp yarat
                return renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }


    @Override
    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }
        return false;
    }

    private Otp renewOtp(Users u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
            return otpRepository.save(otp); // varsa güncelle
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            return otpRepository.save(otp); // yoksa yenisini kaydet
        }
    }





}
