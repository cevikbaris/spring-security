package spring.security.study.filter;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import spring.security.study.entity.Token;
import spring.security.study.repository.TokenRepository;

import java.util.Optional;
import java.util.UUID;
/*
    Custom bir token repository oluşturup kendi tokenreppository'mize bağlanarak db ye kayıt atıp token load ettik.
    Oluşturulan bu customCsrfTokenRepository'yi configde ekledik.
    Tablo : guid- identifier- token

    Implementing Unique CSRF Token for Every Request
 */

public class CustomCsrfTokenRepository implements CsrfTokenRepository {


    @Autowired
    private TokenRepository tokenRepository;

    private Logger logger =
            LoggerFactory.getLogger(CustomCsrfTokenRepository.class.getName());

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        logger.info("token generated : " + uuid);
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = tokenRepository.findTokenByIdentifier(identifier);
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setToken(csrfToken.getToken());
            logger.info("token updated : " +token.getToken());
        } else {
            Token token = new Token();
            token.setToken(csrfToken.getToken());
            token.setIdentifier(identifier);
            tokenRepository.save(token);
            logger.info("new token saved : " +token.getToken());

        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
        String identifier = httpServletRequest.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = tokenRepository.findTokenByIdentifier(identifier);
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            logger.info("token found : " +token.getToken());
            return new DefaultCsrfToken(
                    "X-CSRF-TOKEN",
                    "_csrf",
                    token.getToken());
        }
        return null;
    }
}
