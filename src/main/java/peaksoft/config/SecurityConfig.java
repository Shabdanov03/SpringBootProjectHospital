package peaksoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Shabdanov Ilim
 **/
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetails(){
        User.UserBuilder userBuilder=User.withDefaultPasswordEncoder();

        UserDetails admin = userBuilder
                .username("Ilim")
                .password("ilim123")
                .roles("ADMIN")
                .build();

        UserDetails doctor = userBuilder
                .username("Nurik")
                .password("nurik123")
                .roles("DOCTOR")
                .build();

        UserDetails patient = userBuilder
                .username("Kurstan")
                .password("kurstan123")
                .roles("PATIENT")
                .build();

        return new InMemoryUserDetailsManager(admin,doctor,patient);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/hospitals").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/hospitals/new").hasRole("ADMIN")
                .requestMatchers("/hospitals/save").hasRole("ADMIN")
                .requestMatchers("/hospitals/{hospitalId}/delete").hasRole("ADMIN")
                .requestMatchers("/hospitals/{hospitalId}/edit").hasRole("ADMIN")
                .requestMatchers("/hospitals/{hospitalId}/update").hasRole("ADMIN")
                .requestMatchers("/hospitals/{hospitalId}").hasAnyRole("ADMIN","DOCTOR","PATIENT")

                .requestMatchers("/departments/{id}").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/departments/{hospitalId}/new").hasRole("ADMIN")
                .requestMatchers("/departments/{hospitalId}/save").hasRole("ADMIN")
                .requestMatchers("/departments/{hospitalId}/{departmentId}/delete").hasRole("ADMIN")
                .requestMatchers("/departments/{hospitalId}/{departmentId}/edit").hasRole("ADMIN")
                .requestMatchers("/departments/{hospitalId}/{departmentId}/update").hasRole("ADMIN")

                .requestMatchers("/doctors/{id}").hasAnyRole("ADMIN","PATIENT")
                .requestMatchers("/doctors/{hospitalId}/new").hasRole("ADMIN")
                .requestMatchers("/doctors/{hospitalId}/save").hasRole("ADMIN")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/delete").hasRole("ADMIN")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/edit").hasRole("ADMIN")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/update").hasRole("ADMIN")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/assignPage").hasRole("ADMIN")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/assign").hasRole("ADMIN")

                .requestMatchers("/patients/{id}").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/patients/{hospitalId}/new").hasAnyRole("ADMIN","DOCTOR")
                .requestMatchers("/patients/{hospitalId}/save").hasAnyRole("ADMIN","DOCTOR")
                .requestMatchers("/patients/{hospitalId}/{patientId}/delete").hasAnyRole("ADMIN","DOCTOR")
                .requestMatchers("/patients/{hospitalId}/{patientId}/edit").hasAnyRole("ADMIN","DOCTOR")
                .requestMatchers("/patients/{hospitalId}/{patientId}/update").hasAnyRole("ADMIN","DOCTOR")

                .requestMatchers("/appointments/{hospitalId}").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/appointments/{hospitalId}/new").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/appointments/{hospitalId}/save").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/appointments/{hospitalId}/{id}/edit").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/appointments/{hospitalId}/{id}/update").hasAnyRole("ADMIN","DOCTOR","PATIENT")
                .requestMatchers("/appointments/{hospitalId}/{appointmentId}/delete").hasAnyRole("ADMIN","DOCTOR","PATIENT")

                .and()
                .formLogin()
                .successForwardUrl("/hospitals")
                .permitAll();
        return http.build();
    }

}
