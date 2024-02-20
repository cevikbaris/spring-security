package spring.security.study.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import spring.security.study.filter.CustomCsrfTokenRepository;
import spring.security.study.service.AuthenticationProviderService;
import spring.security.study.service.CustomUserDetailService;

/**
 * @author Barış Çevik
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager( ) throws Exception {
        return new ProviderManager(new AuthenticationProviderService(bCryptPasswordEncoder(),customUserDetailService));
    }

    @Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
             //  .csrfTokenRepository(customTokenRepository());
        return http.build();
    }
*/

/*
    "/x/y/**" gibi bi kullanım x/y altındaki tüm apileri kapsar
    "a/ ** /f" kullanım a/s/d/f  a/b/f gibi anlamlara gelir
    "a/*" şeklinde kullanım  a/b  a/d  örneklere izin verir ama a/b/c ye izin vermez
    .requestMatchers(HttpMethod.GET,"/api") seklinde kullanilabilir
    .requestMatchers("/product/{code:^[0-9]*$}")  bu kullanım regex ile product/ dan sonra sadece string kabul eder
 */


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
            //            .requestMatchers("/user").hasAnyRole("USER")
          //              .requestMatchers("/admin").authenticated()
                        .anyRequest().permitAll()
                )//.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class) // oluşturulan CrsfTokenLogger csrfFilter'dan sonra çalışacak
               // .csrf(c -> c.csrfTokenRepository(customTokenRepository())) // kendi CustomCsrfTokenRepository'mizi kullanıcaz
               // .httpBasic(withDefaults())
                .csrf(c -> c.disable()); // TODO: 6.02.2024 csrf configurasyonu yap.
        return http.build();
    }



}
