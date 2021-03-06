package ru.itis.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                WebConfig.class}; // We dont need any special servlet config yet.
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    @Override
    protected FrameworkServlet createDispatcherServlet (WebApplicationContext wac) {
        DispatcherServlet ds = new DispatcherServlet(wac);
        //setting this flag to true will throw NoHandlerFoundException instead of 404 page
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

}