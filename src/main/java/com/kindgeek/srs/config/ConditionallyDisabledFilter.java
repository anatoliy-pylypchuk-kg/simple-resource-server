package com.kindgeek.srs.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class ConditionallyDisabledFilter implements Filter {

    private final Filter delegate;
    private final RequestMatcher[] matchers;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        delegate.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (shouldDisableFilter(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            delegate.doFilter(servletRequest, servletResponse, filterChain);
        }
    }

    @Override
    public void destroy() {
        delegate.destroy();
    }

    private boolean shouldDisableFilter(ServletRequest servletRequest) {
        return servletRequest instanceof HttpServletRequest request &&
                Arrays.stream(matchers).anyMatch(matcher -> matcher.matches(request));
    }
}
