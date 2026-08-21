package lv.venta.forest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET="ForestMonitoringSecretKey2026ForestMonitoringSecretKey2026";
    private static final long EXPIRATION_MS=86_400_000L;
    private SecretKey key(){ return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)); }
    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+EXPIRATION_MS)).signWith(key()).compact();
    }
    public String extractUsername(String token){ return claim(token, Claims::getSubject); }
    public boolean validateToken(String token,String username){
        try { return username.equals(extractUsername(token)) && claim(token,Claims::getExpiration).after(new Date()); }
        catch(Exception e){ return false; }
    }
    private <T> T claim(String token, Function<Claims,T> resolver){ return resolver.apply(Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload()); }
}
