package com.yongman.common.logging;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.slf4j.MDC;

import java.util.Locale;

/**
 * P6Spy 커스텀 로그 포맷
 * - 요청 추적용 UUID 포함
 * - 바인딩된 SQL만 출력
 * - 컬러 + 포맷팅
 */
public class P6SpyFormatter implements MessageFormattingStrategy {

    private final BasicFormatterImpl formatter = new BasicFormatterImpl();
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";

    @Override
    public String formatMessage(int connectionId, String now, long elapsed,
                                String category, String prepared, String sql, String url) {

        if (sql == null || sql.trim().isEmpty()) return "";

        // 실제 쿼리(STATEMENT)만 로깅
        if (!Category.STATEMENT.getName().equals(category)) return "";

        // MDC null 체크 (getOrDefault 에러 해결)
        String requestId = MDC.get("requestId");
        if (requestId == null) requestId = "--------";

        // Hibernate 포맷터로 개행 처리 + 색상 입히기
        return String.format("[%s] SQL : %s%s%s",
                requestId,
                CYAN,
                formatter.format(sql), // 여기서 자동으로 이쁘게 줄바꿈 해줍니다
                RESET
        );
    }
}