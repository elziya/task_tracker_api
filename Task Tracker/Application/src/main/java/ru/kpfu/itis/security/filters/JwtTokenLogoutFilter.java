package ru.kpfu.itis.security.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.repositories.BlackListRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenLogoutFilter extends OncePerRequestFilter {

    private final BlackListRepository blackListRepository;

    private static final RequestMatcher DEFAULT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/logout", "GET");
    private RequestMatcher logoutRequestMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        RequestMatcher logoutRequest = logoutRequestMatcher == null ? DEFAULT_PATH_REQUEST_MATCHER : logoutRequestMatcher;

        if (logoutRequest.matches(request)) {

            String tokenHeader = request.getHeader("Authorization");
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                String token = tokenHeader.substring("Bearer ".length());
                blackListRepository.save(token);
                SecurityContextHolder.clearContext();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.logoutRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl, "GET");
    }
}

