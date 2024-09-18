package top.mxzero.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import top.mxzero.common.dto.UserProfile;
import top.mxzero.security.jwt.service.impl.JwtService;
import top.mxzero.common.utils.UserProfileUtils;

import java.io.IOException;


/**
 * @author Peng
 * @since 2024/9/3
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authData = request.getHeader("Authorization");
        String token;
        if (authData != null && authData.startsWith("Bearer ")) {
            token = authData.substring((7));
        } else {
            token = request.getParameter("access_token");

        }

        if (token != null) {
            if (jwtService.verifyToken(token)) {
                Jws<Claims> claimsJws = jwtService.parseToken(token);
                String subject = claimsJws.getBody().getSubject();

                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
                    if (userDetails instanceof UserProfile userProfile) {
                        UserProfileUtils.set(userProfile);
                    }
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()));
                } catch (UsernameNotFoundException ignored) {
                }
            }
        }


        try {
            filterChain.doFilter(request, response);
        } finally {
            UserProfileUtils.clear();
        }
    }

}
