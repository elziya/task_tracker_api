package ru.kpfu.itis.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.repositories.BlackListRepository;
import ru.kpfu.itis.security.config.JwtSecurityConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final String secretKey;
    private final BlackListRepository blackListRepository;
    private final UserDetailsService userDetailsService;

    private final String BEARER = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(JwtSecurityConfiguration.LOGIN_FILTER_PROCESSES_URL)
                  || request.getRequestURI().equals(JwtSecurityConfiguration.LOGOUT_FILTER_PROCESSES_URL)) {
            filterChain.doFilter(request, response);
        } else {
            String tokenHeader = request.getHeader(AUTHORIZATION);

            if (tokenHeader == null) {

                logger.warn("Token is missing");
                filterChain.doFilter(request, response);

            } else if (tokenHeader.startsWith(BEARER.concat(StringUtils.SPACE))) {
                String token = tokenHeader.substring(BEARER.concat(StringUtils.SPACE).length());

                if (blackListRepository.exists(token)) {

                    logger.warn("Token in blacklist");
                    filterChain.doFilter(request, response);
                }

                try {
                    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                            .build().verify(token);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetailsService.loadUserByUsername(decodedJWT.getClaim("email").asString()),
                                    token,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString())
                                    )
                            );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                } catch (JWTVerificationException e) {
                    sendForbidden(response);
                }
            } else {
                logger.warn("Wrong token format");
                sendForbidden(response);
            }
        }
    }

    private void sendForbidden(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "Can't find account with token"));
    }
}
