package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Slf4j
@EnableWebSecurity
/* WebSecurityConfigurerAdapter deprecated !!
 * SecurityConfiguration로 대체
 */
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {}
public class SecurityConfiguration {

    /* WebSecurity ignoring Security filter chain 적용되는 문제
     * web.ignoring().antMatchers(...)는 파라미터로 전달하는 패턴에 대해 security filter chain을 생략하도록 하는데, 
     * custom filter를 빈으로 등록하게 되면 해당 필터가 security filter chain에 포함되는 게 아니라 
     * default filter chain에 포함되게 되기 때문에 해당 패턴에 접근하게 됐을 때 그대로 필터를 적용하게 되는 것이다.
     * >> addFilterAfter 인자에 직접 생성 67라인
     * @Autowired
     * private JwtAuthenticationFilter jwtAuthenticationFilter;
     */

    /* antMatchers 적용안됨!!
     * SecurityConfiguration로 대체 후 public WebSecurityCustomizer webSecurityCustomizer() 빈등록해야함
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring()
                    .antMatchers(
                            HttpMethod.POST,"/", "/auth/*"
                            );
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http.cors() // WebMvcConfig에서 이미 설정
                .and()
                .csrf().disable() // csrf는 현재 사용 X
                .httpBasic().disable() // token을 사용하므로 basix 사용 X
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반이 아님
                .and()
                .authorizeHttpRequests() // /와 /auth/** 경로는 인증 안해도됨
                .antMatchers(HttpMethod.POST,"/", "/auth/*").permitAll()
                .anyRequest() // /와 /auth/** 이외의 경로는 모두 인증
                .authenticated();
        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다
        http.addFilterAfter(
                new JwtAuthenticationFilter(),
                CorsFilter.class
        );

        return http.build();
    }

}
