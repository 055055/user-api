package com.api.common.config;

import com.api.common.filter.CustomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProcess jwtTokenProcess;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.
			httpBasic().disable()  //rest api만을 할것이기 때문에 기본설정 해제
			.csrf().disable() //csrf 보안토큰 NO
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //토큰기반인증이므로 세션 사용NO
			.and()
//                .headers().addHeaderWriter(
//                    new XFrameOptionsHeaderWriter(
//                            new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))
//                    )
//                )
			.headers().frameOptions().sameOrigin()
			.and()
			.authorizeRequests() //요청에한 사용권한 체크
			.antMatchers(HttpMethod.POST, "/v1/user/login").permitAll()
			.antMatchers(HttpMethod.POST, "/v1/user").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new CustomFilter(jwtTokenProcess),
				UsernamePasswordAuthenticationFilter.class);
		//JWTAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다.
	}

	// authenticationManager를 Bean 등록합니다.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
