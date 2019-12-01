package com.bendarsianass.shops.security;

import com.bendarsianass.shops.Util.TokenUtil;
import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.repository.UserRepository;
import com.bendarsianass.shops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(TOKEN_HEADER);
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if(header != null && securityContext.getAuthentication() == null) {
            String token = header.substring("Bearer ".length());
            String username = tokenUtil.getUsernameFromToken(token);
            if(username != null) {

                // put the current user in the request
                Long userId = userService.findUserEntityIdFromToken(token);
                UserEntity userEntity = userRepository.findById(userId).get();
                request.setAttribute("currentUser", userEntity);

                UserDetails userDetails = userService.loadUserByUsername(username);
                if(tokenUtil.isTokenValid(token, userDetails)) {
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
