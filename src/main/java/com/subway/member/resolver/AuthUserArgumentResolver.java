package com.subway.member.resolver;

import com.subway.member.dto.AuthMember;
import com.subway.member.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    public static final List<String> AUTH_HEADER_VALUE_PREFIXES = List.of("Bearer", "Authorization");
    private final TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return findAuthMember(getAuthHeader(request));
    }

    private AuthMember findAuthMember(String authHeader) {
        return Optional.ofNullable(authHeader)
                .filter(this::isValid)
                .map(h -> tokenService.findAuthMemberByToken(parseToken(h)))
                .orElseThrow(() -> new IllegalArgumentException("인증 헤더 정보가 없습니다."));
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