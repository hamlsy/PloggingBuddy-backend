package com.ploggingbuddy.global.auth.handler;

import com.ploggingbuddy.security.dto.JwtToken;
import com.ploggingbuddy.security.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final String REDIRECT_URI = "https://plogging-buddy.vercel.app/oauth/callback/kakao";
    private final TokenService tokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }
        log.info("--------------------------- OAuth2LoginSuccessHandler ---------------------------");
        JwtToken jwtToken = tokenService.generateToken(authentication);
        String provider = null;

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            provider = oauth2Token.getAuthorizedClientRegistrationId();
            Collection<GrantedAuthority> authorities = oauth2Token.getAuthorities();
            authorities.forEach(grantedAuthority -> log.info("role {}", grantedAuthority.getAuthority()));
        }

        String url = UriComponentsBuilder.fromHttpUrl(REDIRECT_URI)
                .queryParam("code", jwtToken.getAccessToken())
                .queryParam("provider", provider)
                .build()
                .toUriString();
       log.info("code {}", jwtToken.getAccessToken());
        response.addHeader("Authorization",
                jwtToken.getGrantType() + " " + jwtToken.getAccessToken());

        response.sendRedirect(url);

    }
}