package com.yang.blog.config;

import com.yang.blog.shiro.AccountRealm;
import com.yang.blog.shiro.JwtFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: shiro启用注解拦截控制器
 * @Author: Guo.Yang
 * @Date: 2022/03/08/23:29
 */
@Configuration
public class ShiroConfig {

    @Autowired
    RedisSessionDAO redisSessionDAO;

    @Autowired
    JwtFilter jwtFilter;
    /**
     * 配置sessionManager Bean 和securityManager bean
     * @param redisSessionDAO
     * @return
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);

        // other stuff...

        return sessionManager;
    }

//    @Bean
//    public SessionsSecurityManager securityManager(AccountRealm accountRealm, SessionManager sessionManager, RedisCacheManager redisCacheManager) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
//
//        //inject sessionManager
//        securityManager.setSessionManager(sessionManager);
//
//        // inject redisCacheManager
//        securityManager.setCacheManager(redisCacheManager);
//
//        // other stuff...
//
//        return securityManager;
//    }

    /**
     * 过滤器链
     * @return
     */
    @Bean("shiroFilterChainDefinition")
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){

        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        HashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**","jwt");

        //不可用
//        filterMap.put("/g-blog/**","authc");

        defaultShiroFilterChainDefinition.addPathDefinitions(filterMap);
        return defaultShiroFilterChainDefinition;
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }
//    @Bean("getDefaultWebSecurityManager")
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getAccountRealm") AccountRealm accountRealm){
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        defaultWebSecurityManager.setRealm(accountRealm);
//        return defaultWebSecurityManager;
//    }
//

    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);

        //inject sessionManager
        securityManager.setSessionManager(sessionManager);

        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }


    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
}
