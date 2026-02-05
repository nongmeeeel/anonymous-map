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

        String requestId = UUID.randomUUID().toString();
        String shortId = requestId.substring(0, 8);
        long startTime = System.currentTimeMillis();

        try {
            // MDC에 shortId 설정 (모든 로그에 자동 포함, p6spy에서도 사용)
            MDC.put(REQUEST_ID, shortId);

            // 요청 정보 구성
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            String fullPath = queryString != null ? uri + "?" + queryString : uri;

            // [시작] 기호와 함께 로깅
            log.info("[{}] >>>> --> {} {}", shortId, method, fullPath);

            filterChain.doFilter(request, response);

            // 응답 정보 로깅
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();

            // [끝] 기호와 함께 로깅 후 시각적 구분선 추가
            log.info("[{}] <<<< <-- {} {} ({}ms)", shortId, status, fullPath, duration);
            log.info("--------------------------------------------------------------------------------");

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
