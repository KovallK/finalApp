package pl.sda.ownApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/restricted").authenticated()
                .anyRequest().permitAll() // przepuszcza kazde żądanie
                .and()
                .csrf().disable() // wylaczamy csrf , bledy zwiazane z 403
                .headers().frameOptions().disable() // zeby h2 dzialalo w przegladarce , springsecurity zeby nie blokowalo
                .and()
                .formLogin() // uzywamy formularza do logowania
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username") // podajemy parametr z pliku html z name username
                .passwordParameter("passwordParam") // podajemy parametr z pliku html
                .failureUrl("/login?error=true") // przy zlym zalogowaniu chcemy wrocic na ta sama strone, i dajemy ? i doklejamy parametr zebysmy wiedzieli ze cos poszlo nie tak przy logowaniu
                .defaultSuccessUrl("/"); // po poprawnym zalogowaniu chcemy zeby wyladowal na nasza glowna strone
    }
    @Override // dzieki tej metodzie bedziemy budowac mangera springsecurity , jak dostac sie do danego uzytkownika
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT u.username, u.password_hash, 1 FROM user u WHERE u.username = ?") // piszemy selecta zeby uzyskac informacje o uzytkowniku na podstawie username , 1 jest to flaga aktywnosci ustawiamy 1 ze zawsze aktywni
                .authoritiesByUsernameQuery("SELECT u.username, r.role_name " +  // select na zwrocenie wszystkich rol jakie posiada uzytkownik
                        "FROM user u " +
                        "JOIN role_users ur ON u.id = ur.users_id " +
                        "JOIN role r ON ur.roles_id = r.id " +
                        "WHERE u.username = ?")
                .passwordEncoder(passwordEncoder); //określony tutaj jest typ hashowania w tym przypadku BcyptPasswordEnc w klasie secBeansConfig
    }
}

