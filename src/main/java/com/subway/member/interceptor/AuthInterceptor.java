package com.subway.member.interceptor;

import com.subway.common.exception.auth.AuthException;
import com.subway.member.dto.AuthMember;
import com.subway.member.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import java.util.List;
import java.util.Optional;

@Component
public class AuthInterceptor extends WebRequestHandlerInterceptorAdapter { // argment resolv

    private static final List<String> AUTH_HEADER_VALUE_PREFIXES = List.of("Bearer", "Authorization");
    public static final String AUTH_MEMBER = "authMember";

    private final TokenService tokenService;

    public AuthInterceptor(WebRequestInterceptor requestInterceptor, TokenService tokenService) {
        super(requestInterceptor);
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = getAuthHeader(request);

        if (!isValidAuthHeader(authHeader)) {
            throw new AuthException("인증헤더가 유효하지 않습니다");
        }

        AuthMember authMember = tokenService.findAuthMemberByToken(parseToken(authHeader));
        request.setAttribute(AUTH_MEMBER, authMember);

        return true;
    }

    private boolean isValidAuthHeader(String authHeader) {
        return Optional.ofNullable(authHeader)
                .filter(StringUtils::hasText)
                .filter(this::isValid)
                .isPresent();


    }

    private boolean isValid(String authHeader) {
        return AUTH_HEADER_VALUE_PREFIXES.stream()
                .anyMatch(authHeader::startsWith);
    }


    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private String parseToken(String authorizationHeader) {
        return authorizationHeader.split(" ")[1];
    }
}
