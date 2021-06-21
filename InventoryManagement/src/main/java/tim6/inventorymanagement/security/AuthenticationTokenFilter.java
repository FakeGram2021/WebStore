package tim6.inventorymanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    private final TokenUtilities tokenUtilities;

    @Autowired
    public AuthenticationTokenFilter(
            UserDetailsService userDetailsService, TokenUtilities tokenUtilities) {
        this.userDetailsService = userDetailsService;
        this.tokenUtilities = tokenUtilities;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String token;
        if (httpServletRequest.getCookies() != null) {
            token = this.getTokenFromCookie(httpServletRequest);
        } else {
            token = this.getTokenFromHeader(httpServletRequest);
        }

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        String username = this.tokenUtilities.getUsernameFromToken(token);

        if (username != null || SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.tokenUtilities.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, username, userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest) {
        String token;
        token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring("Bearer ".length());
        }
        return token;
    }

    private String getTokenFromCookie(HttpServletRequest httpServletRequest) {
        String token;
        token =
                Arrays.stream(httpServletRequest.getCookies())
                        .filter(c -> c.getName().equals("token"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null);
        return token;
    }
}
