package com.is.customerfinance.filter;

import com.is.customerfinance.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestId = System.currentTimeMillis();

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        log.info("RequestId: {}; Method: {}; Body: {}", requestId, Utils.getUrl(request), getRequestBody(requestWrapper));
        log.info("ResponseId: {}; Method: {}; Status: {}; Body: {}", requestId, Utils.getUrl(request), response.getStatus(), getResponseBody(responseWrapper));
        responseWrapper.copyBodyToResponse();
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] content = requestWrapper.getContentAsByteArray();
        return content.length > 0 ? new String(content, StandardCharsets.UTF_8) : " Request body пустой";
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        byte[] content = responseWrapper.getContentAsByteArray();
        return content.length > 0 ? new String(content, StandardCharsets.UTF_8) : " Response body пустой";
    }
}
