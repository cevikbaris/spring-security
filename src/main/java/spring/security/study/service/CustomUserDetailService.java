package spring.security.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.security.study.entity.CustomUserDetails;
import spring.security.study.entity.Users;
import spring.security.study.repository.UserRepository;

import java.util.function.Supplier;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Supplier<UsernameNotFoundException> exceptionSupplier = () -> new UsernameNotFoundException("Username not found.");

        Users user =userRepository.findUserByUsername(username).orElseThrow(exceptionSupplier);

        return new CustomUserDetails(user);
    }

}
