package com.yongman.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString().substring(0, 8);
        long startTime = System.currentTimeMillis();

        try {
            // MDC에 requestId 설정 (모든 로그에 자동 포함)
            MDC.put(REQUEST_ID, requestId);

            // 요청 정보 로깅
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            String fullPath = queryString != null ? uri + "?" + queryString : uri;

            log.info("[{}] --> {} {}", requestId, method, fullPath);

            filterChain.doFilter(request, response);

            // 응답 정보 로깅
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();

            log.info("[{}] <-- {} {} ({}ms)", requestId, status, fullPath, duration);

        } finally {
            MDC.remove(REQUEST_ID);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 정적 리소스는 로깅 제외
        return path.contains("/favicon.ico") || path.contains("/static/");
    }
}
