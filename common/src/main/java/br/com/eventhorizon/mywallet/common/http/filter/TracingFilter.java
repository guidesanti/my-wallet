package br.com.eventhorizon.mywallet.common.http.filter;

import br.com.eventhorizon.mywallet.common.http.Conventions;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class TracingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        var idempotenceId = request.getHeader(Conventions.HEADER_X_IDEMPOTENCE_ID);
        var traceId = request.getHeader(Conventions.HEADER_X_TRACE_ID);
        log.info("HTTP request: method={}, path={}, idempotenceId={}, requestId={}",
                request.getMethod(),
                request.getServletPath(),
                idempotenceId,
                traceId);
        MDC.put("idempotenceId", idempotenceId);
        MDC.put("traceId", traceId);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
