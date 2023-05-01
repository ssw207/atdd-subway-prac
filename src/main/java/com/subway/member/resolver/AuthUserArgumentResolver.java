package com.subway.member.resolver;

import com.subway.member.domain.Member;
import com.subway.member.dto.MemberResponse;
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

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String TOKEN_PREFIX = "Bearer ";
    private final TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            throw new IllegalArgumentException("인증 헤더 정보가 없습니다.");
        }

        Member member = tokenService.findMemberByToken(parseToken(authorizationHeader));
        return MemberResponse.of(member);
    }

    private String parseToken(String authorizationHeader) {
        return authorizationHeader.replace(TOKEN_PREFIX, "");
    }
}