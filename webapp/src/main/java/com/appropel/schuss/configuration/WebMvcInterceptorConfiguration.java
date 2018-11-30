package com.appropel.schuss.configuration;

import com.appropel.schuss.rest.UserController;
import com.appropel.schuss.rest.UtilityController;
import com.appropel.schuss.security.SecurityInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Vx app interceptor config.
 */
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final due to Spring Conventions
@Configuration
public class WebMvcInterceptorConfiguration extends WebMvcConfigurerAdapter
{
    /** Security interceptor. */
    private SecurityInterceptor securityInterceptor;

    @Autowired
    public void setHandlerInterceptor(final SecurityInterceptor securityInterceptor)
    {
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry)
    {
        registry.addInterceptor(securityInterceptor)
                // Apply to all methods...
                .addPathPatterns("/*")
                // ...except the new user/sign in
                .excludePathPatterns(UserController.USER_PATH + UserController.NEW_ACCOUNT_PARAM)
                .excludePathPatterns(UserController.USER_PATH + UserController.SIGN_IN_METHOD)
                .excludePathPatterns(UtilityController.UTILITY_PATH + "/*")
                .excludePathPatterns("/images/*"); // TODO: hacky way to show images
    }
}
