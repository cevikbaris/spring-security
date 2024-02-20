package spring.security.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.security.study.entity.Otp;

import java.util.Optional;

public interface OtpRepository  extends JpaRepository<Otp,Integer> {

    Optional<Otp> findOtpByUsername(String username);
}
