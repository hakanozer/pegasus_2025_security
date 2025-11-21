package com.works.config;

import com.works.service.LogService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FilterConfig implements Filter {
    
    private final LogService logService;
    private final RateLimitConfig rateLimitConfig;
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String username = "anonymous";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            username = auth.getName();
        }

        // Rate Limiting kontrolü
        Bucket bucket = rateLimitConfig.resolveBucket(username);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            // İstek limiti içinde, devam et
            long time = System.currentTimeMillis();
            String sessionId = req.getSession().getId();
            String url = req.getRequestURI();
            String agent = req.getHeader("User-Agent");
            
            // Asenkron log yazma
            logService.writeLog(sessionId, username, time, url, agent);

            filterChain.doFilter(req, res);
        } else {
            // Rate limit aşıldı
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            res.setContentType("application/json");
            res.getWriter().write(String.format(
                "{\"error\": \"Too many requests\", \"message\": \"Rate limit exceeded. Please wait %d seconds.\", \"username\": \"%s\"}",
                waitForRefill, username
            ));
        }
    }

}
