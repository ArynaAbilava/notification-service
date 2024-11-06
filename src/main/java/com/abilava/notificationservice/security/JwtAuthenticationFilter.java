package com.abilava.notificationservice.security;


import com.abilava.notificationservice.exceptions.InvalidCredentialsException;
import com.abilava.utils.LoggerUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            var accessToken = jwtUtil.resolveToken(request);
            if (Objects.isNull(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            var claims = jwtUtil.resolveClaims(request);

            if (Objects.nonNull(claims) && jwtUtil.validateClaims(claims)) {
                var authentication = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LoggerUtil.error(String.format("Exception: %s", e));
            throw new InvalidCredentialsException();

        }
        filterChain.doFilter(request, response);
    }

}
