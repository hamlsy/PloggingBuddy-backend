package com.ploggingbuddy.security.resolver;

import com.ploggingbuddy.domain.member.adaptor.MemberAdaptor;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.security.aop.CurrentMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

@Slf4j
@Component
@RequiredArgsConstructor
public final class CustomAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberAdaptor memberAdaptor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return findMethodAnnotation(CurrentMember.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        CurrentMember annotation = findMethodAnnotation(CurrentMember.class, parameter);
        if (principal == "anonymousUser") {
            throw new RuntimeException();
        }
        findMethodAnnotation(CurrentMember.class, parameter);
        Member member = memberAdaptor.queryByUsername(authentication.getName());

        return member;
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }

        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation a : annotations) {
            if (a.annotationType().isAnnotationPresent(annotationClass)) {
                return parameter.getParameterAnnotation(annotationClass);
            }
        }

        return null;
    }

}