package com.lzw.config.filter;

import com.lzw.config.Constant;
import com.lzw.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 * Created by macro on 2018/4/26.
 * OncePerRequestFilter 保证一次请求只经过一次filter
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${login.start-urls}")
    private String startUrl;
    @Value("${login.end-urls}")
    private String endUrl;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String[] startUrls = startUrl.split(",");
        String[] endUrls = endUrl.split(",");
        boolean anon = false;
        String tagertUrl = request.getServletPath();
        for(String url:endUrls){
            if(tagertUrl.endsWith(url)){
                anon = true;
                break;
            }
        }
        if(!anon){
            for(String url:startUrls){
                if(tagertUrl.startsWith(url)){
                    anon = true;
                    break;
                }
            }
        }

        if(anon){
            chain.doFilter(request, response);
        } else {
            String authHeader = null;
            javax.servlet.http.Cookie cookie = null;
            javax.servlet.http.Cookie cookies[] = request.getCookies();
            if (cookies != null) {
                for (javax.servlet.http.Cookie cook : cookies) {
                    if (Constant.JSESSIONID.equals(cook.getName())) {
                        cookie = cook;
                    }
                }
            }
            if (cookie != null) {
                authHeader = cookie.getValue();
            }
            if(authHeader == null){
                authHeader = request.getHeader(this.tokenHeader);
            }
            if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
                String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
                String username = jwtTokenUtil.getUserName(authToken);
                LOGGER.info("checking username:{}", username);
                if (username != null) {
                    LOGGER.info("authenticated user:{}", username);
                    String newAuthToken = jwtTokenUtil.refreshToken(authToken);
                    response.setHeader(this.tokenHeader, this.tokenHead + newAuthToken);
                    response
                    chain.doFilter(request, response);
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().print("没有登录。");
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("没有登录。");
            }
        }
    }
}
