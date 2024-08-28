package ru.gb.spring.my_timesheet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // зачем нужна
@EnableMethodSecurity(securedEnabled = true) // аннотация, чтобы заработала проверка кода на наличие аннотаций @Secured
public class SecurityConfiguration {

    @Bean // этот бин переопределяет префикс, который используется в методе hasrole() ниже
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // здесь устанавливаем префикс для метода, например "MY_ROLE_PREFIX_".
        // Если "", как здесь, то hasAuthority("admin") тоже самое, что hasRole("admin")
    }

    @Bean
    SecurityFilterChain noSecurity(HttpSecurity http) throws Exception { // оключить запрос логин-пароля
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(it -> it.anyRequest().permitAll())
                .build();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(requests -> requests
////                                .requestMatchers("/home/projects/**").hasAuthority(RoleName.ADMIN.getName())
////                                .requestMatchers("/home/projects/**").hasRole("admin") // если этот способ, тогда в Authority,
////                                если не определено в бине GrantedAuthorityDefaults выше, ищет ROLEadmin
//                                .requestMatchers("/home/projects/**", "/home/employees/**").hasRole(RoleName.ADMIN.getName())
//                                .requestMatchers("/home/timesheets/**").hasRole(RoleName.USER.getName())
//                                .requestMatchers("/timesheets/**", "/projects/**", "/employees/**").hasRole(RoleName.REST.getName())
//                                .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults())
//                .build();
//    }

    @Bean // вместо класса MyCustomPasswordEncoder
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
