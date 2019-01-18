package com.lzw.config;

import com.lzw.config.filter.JwtAuthenticationTokenFilter;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * 注意此对象的加载是单例的应该
     * 加此注解的方法会纳入bean管理
     * 现在是使用spring容器
     * spring的bean管理一定保证了单例。不会线程安全问题的
     * 如果不使用spring，请使用双重检查锁，实现静态 单例对象的初始化，保证多线程初始化之初始化一个
     * 或者使用内部类单例方式，类的加载本身是线程安全的，并且延时加载的，利用这个特性不再单独枷锁
     */


    /**
     *  springboot两种创建过滤器
     *  FilterRegistrationBean方式直接注册到servlet容器中
     *
     *  DelegatingFilterProxyRegistrationBean是代理过滤器，目标filter是在spring容器中
     *  所以两者的区别，在不同的容器中
     *  FilterRegistrationBean方式，过滤器中无法直接注入spring的东西，需要先拿到spring容器，然后在获取spring对象
     *  DelegatingFilterProxyRegistrationBean方式可以直接注入spring对象
     *
     */


//    @Bean
//    public FilterRegistrationBean jwtFilter(){
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new JwtAuthenticationTokenFilter());
//        bean.addUrlPatterns("/*");
//        return bean;
//    }

    @Bean
    public DelegatingFilterProxyRegistrationBean jwtFilter(){
        DelegatingFilterProxyRegistrationBean bean = new DelegatingFilterProxyRegistrationBean("jwtAuthenticationTokenFilter");
        bean.addUrlPatterns("/*");
        return bean;
    }
}
