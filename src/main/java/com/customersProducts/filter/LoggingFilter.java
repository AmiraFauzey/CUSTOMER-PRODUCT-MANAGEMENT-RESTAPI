package com.customersProducts.filter;

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
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(contentCachingRequestWrapper,contentCachingResponseWrapper);

        long timeTaken = System.currentTimeMillis() - startTime;
        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(),request.getCharacterEncoding());

        log.info("Request URL: {}", request.getRequestURL());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Headers: {}", getHeaders(request));
        log.info("Request Body: {}", requestBody);

        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(),response.getCharacterEncoding());
        log.info("Response Status: {}", response.getStatus());
        log.info("Response Body: {}", responseBody);
        log.info("Time Taken: {} ms", timeTaken);

        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try{
            return new String(contentAsByteArray,0, contentAsByteArray.length,characterEncoding);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("; "));
        return headers.toString();
    }
}
