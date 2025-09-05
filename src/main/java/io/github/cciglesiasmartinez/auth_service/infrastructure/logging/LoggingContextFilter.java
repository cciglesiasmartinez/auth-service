package io.github.cciglesiasmartinez.auth_service.infrastructure.logging;

import io.github.cciglesiasmartinez.auth_service.infrastructure.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class LoggingContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("timestamp", LocalDateTime.now().toString());
            MDC.put("ip", request.getRemoteAddr());
            MDC.put("userAgent", request.getHeader("User-Agent"));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            if (auth != null && auth.isAuthenticated() && principal instanceof CustomUserDetails) {
                String userId = ((CustomUserDetails) principal).getUserId();
                MDC.put("userId", userId);
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear(); // Avoids memory leaks
        }
    }
}