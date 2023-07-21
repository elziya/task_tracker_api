package ru.kpfu.itis.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.kpfu.itis.repositories.BlackListRepository;
import ru.kpfu.itis.security.filters.JwtTokenAuthenticationFilter;
import ru.kpfu.itis.security.filters.JwtTokenAuthorizationFilter;
import ru.kpfu.itis.security.filters.JwtTokenLogoutFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String API = "/api/v1";

    public static final String LOGIN_FILTER_PROCESSES_URL = API + "/login";
    public static final String LOGOUT_FILTER_PROCESSES_URL = API + "/logout";

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration.millis}")
    private Long expirationInMillis;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService accountUserDetailsService;

    private final ObjectMapper objectMapper;

    private final BlackListRepository blackListRepository;

    private static final String[] PERMIT_ALL = {
            LOGIN_FILTER_PROCESSES_URL,
            API + "/sign-up",
            API + "/confirm/**"
    };

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtTokenAuthenticationFilter authenticationFilter =
                new JwtTokenAuthenticationFilter(authenticationManagerBean(),
                        objectMapper, secretKey, expirationInMillis);
        authenticationFilter.setFilterProcessesUrl(LOGIN_FILTER_PROCESSES_URL);

        JwtTokenAuthorizationFilter authorizationFilter =
                new JwtTokenAuthorizationFilter(objectMapper, secretKey, blackListRepository, accountUserDetailsService );

        JwtTokenLogoutFilter logoutFilter =
                new JwtTokenLogoutFilter(blackListRepository);
        logoutFilter.setFilterProcessesUrl(LOGOUT_FILTER_PROCESSES_URL);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(logoutFilter, JwtTokenAuthenticationFilter.class);
        http.logout().disable();

        http.authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .antMatchers(API + "/projects/**", API + "/files/**").authenticated();
    }

}
