package com.syllabus.modulo1_proj.avaliativo.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.syllabus.modulo1_proj.avaliativo.entities.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${modulo1_proj.avaliativo.security.token.secret}")
    private String secret;

    public String generateToken(Usuario user){
        try{
            logger.info("Gerando token/autenticação.");
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("modulo1_proj.avaliativo")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            logger.error("Erro ao gerar Token");
            throw new RuntimeException("Erro ao gerar Token", exception);
        }
    }

    public String validateToken(String token){
        try {
            logger.info("Validando token informado.");
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("modulo1_proj.avaliativo")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            logger.error("Token não validado, retornando usuário nulo.");
            return "";
        }
    }

    private Instant genExpirationDate(){
        logger.debug("Gerando expirationDate ao Token.");
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
