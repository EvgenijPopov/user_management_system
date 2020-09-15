package popov.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // support security configuration
public class UMSSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    /* override configure method and specify source of data (DB store userNames, passwords, roles)
    then specify by query in which table(non-default) this data stored */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, roles from users where username=?");
    }

    @Override
    // ignore webapp/resources directory for static resources usage
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    /* set up users authorization, custom login form, Spring Security user authentication,
    logout possibility and customize access denied page */
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/ums/edit*").hasRole("ADMIN")
                .antMatchers("/ums/new*").hasRole("ADMIN")
                .antMatchers("/ums/save*").hasRole("ADMIN")
                .antMatchers("/ums/update*").hasRole("ADMIN")
                .antMatchers("/ums/delete*").hasRole("ADMIN")
                .antMatchers("/ums/showDetails*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/ums/users*").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessRestrict");
    }

    @Bean
    // create bean for password encryption
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
