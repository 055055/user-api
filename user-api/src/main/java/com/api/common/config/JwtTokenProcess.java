package com.api.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProcess {

	private String secretkey = "055055";

	//토크유효시간 30분
	//private long tokenValidTime = 30*60*1000L;

	private long tokenValidTime = 5 * 60 * 1000L;

	private final UserDetailsService userDetailsService;

	// 객체 초기화, secretKey를 Base64로 인코딩
	@PostConstruct
	protected void init() {
		secretkey = Base64.getEncoder().encodeToString(secretkey.getBytes());
	}

	//JWT 토큰 생성
	public String createToken(String userPk, String role) {
		Claims claims = Jwts.claims().setSubject(userPk); //JWT payload에 저장되는 정보단위
		claims.put("role", role); //정보는 key/value 쌍으로 저장
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims) //정보저장
			.setIssuedAt(now) //토큰발행시간정보
			.setExpiration(new Date(now.getTime() + tokenValidTime)) //set Expire Time
			.signWith(SignatureAlgorithm.HS256, secretkey) //사용할 알고리즘과  signature에 들어갈 secret값 세팅
			.compact();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserInfo(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities());
	}

	// 토큰에서 회원 정보 추출
	public String getUserInfo(String token) {
		return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값 조회
	public String getToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}

	// 토큰의 유효성 및 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}

