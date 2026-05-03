package _DAM.Cine_V2.security;

import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.modelo.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Paso 3: JwtUtil (El Artesano) 🛠️
 * Clase encargada de fabricar, extraer y validar los Tokens JWT.
 */
@Component
public class JwtUtil {

    // Paso 3.5: La Secret Key (Base de la seguridad)
    @Value("${jwt.secret:defaultSecretKeyWithALengthOfAtLeast32CharactersForHS256Algorithm}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private long expirationTime;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // Ensure secret has enough bytes for HS256
        byte[] bytes = secret.getBytes();
        if (bytes.length < 32) {
            // In a real app, this should be an error or handled securely, 
            // but for this task, I'll ensure I have a valid key.
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        } else {
            this.key = Keys.hmacShaKeyFor(bytes);
        }
    }

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles",
                        usuario.getRoles()
                                .stream()
                                .map(Rol::getNombre)
                                .toList()
                )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
