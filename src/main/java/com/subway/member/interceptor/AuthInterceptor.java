package com.subway.member.interceptor;

import com.subway.common.exception.auth.AuthException;
import com.subway.member.dto.AuthMember;
import com.subway.member.resolver.AuthMemberPrincipal;
import com.subway.member.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor { // argment resolv

    private static final List<String> AUTH_HEADER_VALUE_PREFIXES = List.of("Bearer", "Authorization");
    public static final String AUTH_MEMBER = "authMember";

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isAuthMemberPrincipalNullable(handler)) {
            return true;
        }

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

    private boolean isAuthMemberPrincipalNullable(Object handler) {
        return findAuthMemberPrincipalAnnotation(toMethodParameters(handler))
                .filter(AuthMemberPrincipal::nullable)
                .isPresent();
    }

    private MethodParameter[] toMethodParameters(Object handler) {
        return ((HandlerMethod) handler).getMethodParameters();
    }

    private Optional<AuthMemberPrincipal> findAuthMemberPrincipalAnnotation(MethodParameter[] methodParameters) {

        return Arrays.stream(methodParameters)
                .map(p -> p.getParameterAnnotation(AuthMemberPrincipal.class))
                .filter(Objects::nonNull)
                .findAny();
    }
}
