package popov.test.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
    /* SecurityWebAppInitializer will register their filters before
     any other Initializer, that means that this class are invoked first */
}
