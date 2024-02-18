package com.tr.kahveciefendi.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.tr.kahveciefendi.config.root.AppSecurityConfig;
import com.tr.kahveciefendi.config.root.DevelopmentConfiguration;
import com.tr.kahveciefendi.config.root.RootContextConfig;
import com.tr.kahveciefendi.config.root.TestConfiguration;
import com.tr.kahveciefendi.config.servlet.ServletContextConfig;

/**
 *
 * Replacement for most of the content of web.xml, sets up the root and the servlet context config.
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootContextConfig.class, DevelopmentConfiguration.class, TestConfiguration.class,
                AppSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ServletContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }




}


