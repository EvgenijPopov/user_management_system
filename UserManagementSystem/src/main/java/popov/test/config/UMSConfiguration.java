package popov.test.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

// Specify configuration class annotations
@Configuration // bean definition like @Component but associated with configuration
@ComponentScan("popov.test") // define component scanning directives
@EnableWebMvc // support Spring MVC configuration
@EnableTransactionManagement // support transaction management
@PropertySource({"classpath:persistence.properties"})
public class UMSConfiguration implements WebMvcConfigurer {
    // Apply this interface for Thymeleaf settings
    private final ApplicationContext applicationContext;
    // Apply this interface for work with persistence.properties
    private final Environment environment;

    @Autowired
    public UMSConfiguration(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    // Bean responsible for invoke the specified view component based on prefix, suffix and viewName received from FrontController
    public SpringResourceTemplateResolver resourceTemplateResolver() {
        SpringResourceTemplateResolver templateResolver =
                new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    // Add bean to work with sec:authentication, sec:authorize (thymeleaf extras spring security)
    public SpringSecurityDialect securityDialect() {
        SpringSecurityDialect securityDialect = new SpringSecurityDialect();
        return securityDialect;
    }

    @Bean
    // Add and configure Thymeleaf engine bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine =
                new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resourceTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true); // SpEL expressions should be compiled if possible
        templateEngine.addDialect(securityDialect()); // must apply Spring Security dialect Bean to Engine
        return templateEngine;
    }

    @Override
    // registry new ViewResolver as Thymeleaf
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(springTemplateEngine());
        registry.viewResolver(resolver);
    }

    @Bean
    // bean for DB access set up
    public DataSource dataSource() {
        ComboPooledDataSource dataSource =
                new ComboPooledDataSource();
        // try to install driver for DB: invoke properties from file
        try {
            dataSource.setDriverClass(environment.getProperty("jdbc.psql.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException();
        }
        // set up main parameters for DB connection
        dataSource.setJdbcUrl(environment.getProperty("jdbc.psql.url"));
        dataSource.setUser(environment.getProperty("jdbc.psql.user"));
        dataSource.setPassword(environment.getProperty("jdbc.psql.password"));
        // set up connection pooling properties
        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
        return dataSource;
    }

    // auxiliary method for String -> int parsing
    private int getIntProperty(String propertyName) {
        String property = environment.getProperty(propertyName);
        return Integer.parseInt(property);
    }

    @Bean
    // bean for Hibernate set up
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.psql.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        return properties;
    }

    @Bean
    // Session Factory bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean =
                new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    @Autowired
    // bean responsible for coordinating transactions between one or more resources
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager =
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Override
    // specify path for static resources (css/images/pdf)
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
