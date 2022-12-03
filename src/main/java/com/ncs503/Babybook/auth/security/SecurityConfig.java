package com.ncs503.Babybook.auth.security;

import com.ncs503.Babybook.auth.utility.RoleEnum;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    private final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
                                             UserDetailsService userDetailsCustomService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsCustomService)
                .passwordEncoder(bCryptPasswordEncoder).and().build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth

                        // Auth

                        // TODO borrar endpoint de admin
                        .antMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/su/register", "/auth/guestRegister/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/auth/testing").hasAuthority(RoleEnum.USER.getFullRoleName())



                        //Users
                        .antMatchers(HttpMethod.GET, "/user/getById/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE, "/user/delete/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.PATCH, "/user/update/**").hasAuthority(RoleEnum.USER.getFullRoleName())

                        //ADMIN
                        .antMatchers(HttpMethod.GET, "/user/all").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.GET, "/user/getPagination/**").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE, "/user/su/delete/**").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.PATCH, "/user/su/update/**").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.GET, "/guest/all").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.GET, "/guest/getPagination/**").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE, "/guest/su/delete/**").hasAuthority(RoleEnum.ADMIN.getFullRoleName())
                        .antMatchers(HttpMethod.PATCH, "/guest/su/update/**").hasAuthority((RoleEnum.ADMIN.getFullRoleName()))

                        //Subject
                        .antMatchers(HttpMethod.GET,"/subjects/getByUser/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.GET,"/subjects/getByName/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.PUT,"/subjects/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.POST,"/subjects").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE,"/subjects/**").hasAuthority(RoleEnum.USER.getFullRoleName())

                        //Events
//                        .antMatchers(HttpMethod.POST, "/events/create").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.PUT, "/events/update/").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE, "/events/delete/").hasAuthority(RoleEnum.USER.getFullRoleName())
//                        .antMatchers(HttpMethod.GET, "/events/findById/").hasAuthority(RoleEnum.USER.getFullRoleName())
//                        .antMatchers(HttpMethod.GET, "/events/findByIdSubject/").hasAuthority(RoleEnum.USER.getFullRoleName())
//                        .antMatchers(HttpMethod.GET, "/events/findAllByDate/").hasAuthority(RoleEnum.USER.getFullRoleName())
//                        .antMatchers(HttpMethod.GET, "/events/findAllByTags/").hasAuthority(RoleEnum.USER.getFullRoleName())

                        //MedicalData
                        .antMatchers(HttpMethod.GET,"/medicals/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.PUT,"/medicals/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.POST,"/medicals").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE,"/medicals/**").hasAuthority(RoleEnum.USER.getFullRoleName())


                        //Guests
                        .antMatchers(HttpMethod.GET, "/guest/getByUser/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.GET, "/guest/getPaginationByUser/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.GET, "/guest/getById/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.POST, "/guest/new/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.DELETE, "/guest/delete/**").hasAuthority(RoleEnum.USER.getFullRoleName())
                        .antMatchers(HttpMethod.PATCH, "/guest/update/**").hasAuthority(RoleEnum.USER.getFullRoleName())

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .exceptionHandling((ex) -> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "DELETE", "POST", "PATCH", "HEAD", "TRACE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
        return jwtDecoder;
    }


    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**",
                "/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs", "/v3/api-docs", "/api/docs", "/api/docs/**",
                "/api/docs/swagger-ui", "/swagger-ui.html", "/**/swagger-ui/**", "/swagger-ui");
    }

}
