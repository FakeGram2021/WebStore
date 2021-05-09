package tim6.inventorymanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtilities {

    private String secret;

    private Long expiration;

    @Autowired
    public TokenUtilities(@Value("${jwt.secret}") String secret, @Value("3600") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setIssuer("FakeGram.WebStore")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = this.getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            Claims claims = this.getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = this.getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    private Claims getClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(this.secret);
        return parser.parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = this.getExpirationDateFromToken(token);
        return expirationDate != null && expirationDate.before(new Date());
    }
}
