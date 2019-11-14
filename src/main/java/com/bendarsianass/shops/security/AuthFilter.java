package com.bendarsianass.shops.security;

import com.bendarsianass.shops.Util.TokenUtil;
import com.bendarsianass.shops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    private String TOKEN_HEADER = "Authorization";

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(TOKEN_HEADER);
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println("auth filter");
        if(header != null && securityContext.getAuthentication() == null) {
            String token = header.substring("Bearer ".length());
            String username = tokenUtil.getUsernameFromToken(token);
            System.out.println("header exists " + username);
            if(username != null) {
                System.out.println("username exists");
                UserDetails userDetails = userService.loadUserByUsername(username);
                if(tokenUtil.isTokenValid(token, userDetails)) {
                    System.out.println("valid token");
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
