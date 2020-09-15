package popov.test.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializerUMS
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Specify application configuration class
        return new Class[] {UMSConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        // Make servlet mapping, this servlet would be response to this address (root)
        return new String[] {"/"};
    }
}
