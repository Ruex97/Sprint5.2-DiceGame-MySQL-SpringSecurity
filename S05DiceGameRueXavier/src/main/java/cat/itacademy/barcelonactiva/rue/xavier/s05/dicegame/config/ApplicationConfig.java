package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.config;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final PlayerRepository repository;

    /**
     * This bean is responsible for loading user-specific data based on the username provided by the user during login
     * @return an implementation of the UserDetailsService interface
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * This is the data access object which is responsible to fetch the
     * user details and also encode password
     * @return Authentication provider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * The PasswordEncoder is used to encode the user's password before comparing it with the stored password.
     * @return returns an instance of BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager is responsible for performing authentication operations
     * @param config
     * @return AuthenticationManager from Authentication config
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
