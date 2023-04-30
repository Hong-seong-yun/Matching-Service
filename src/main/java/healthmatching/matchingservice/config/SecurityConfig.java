package healthmatching.matchingservice.config;

import healthmatching.matchingservice.service.MemberDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity        //spring security 를 적용한다는 Annotation
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberDetailsServiceImpl memberDetailsService;

    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 규칙 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable() //로그인 창(사이트 간 요청 위조(Cross-Site Request Forgery) 공격 방지 기능 키기)
                .authorizeRequests()
                .antMatchers( "/login", "/signup","/home", "/access_denied", "/resources/**").permitAll()
                // USER, ADMIN 접근 허용(보류) - /userAccess라는 페이지에 해당 CUSTOMER와 TRAINER만 들어올수있다는 뜻.
                .antMatchers("/userAccess").hasRole("CUSTOMER")
                .antMatchers("/userAccess").hasRole("TRAINER")
                .and()
                .formLogin()
                .loginPage("/login")
                //구현한 로그인 페이지(form에서도 경로를 /login_proc으로 정의해야한다)
                .loginProcessingUrl("/login_proc")
                //로그인 성공시 제공할 페이지
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                // 로그아웃을 실행할 주소
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home")
                // 로그아웃시 쿠키 삭제
                .deleteCookies("JSESSIONID", "remember-me")
                // 로그아웃 이후 세션 전체 삭제 여부
                .invalidateHttpSession(true)
                .and();
    }

    //  Bean으로 등록해줘야 한다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    // 시큐리티가 대신 로그인해주는데 password를 가로채는데
    // 해당 password가 뭘로 해쉬화해서 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가능
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailsService).passwordEncoder(encryptPassword());
    }
}
