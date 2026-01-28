package dasturlash.z.spring_security_multi_user.config;


import dasturlash.z.spring_security_multi_user.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public  class SecurityConfig {
    //private CustomUserDetailsService customUserDetailsService;//inject qilinganda ota klasga boradi userla.
    @Autowired//tip nomi ioc contenerdan topiberadi beanni.
    private UserDetailsService userDetailsService;//customuserdetailsdan implement olgani uchun ota sinfni obyektini bergan maqul
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String[] AUTH_WHITELIST = {
            "/profile/registration",
            "/profile/authorization",
            "/profile/refresh-token"
    };

    @Bean
    public AuthenticationProvider authenticationProvider(/*PasswordEncoder passwordEncoder*/BCryptPasswordEncoder bCryptPasswordEncoder){//loginparol bor yo'qligini tekshirish

        final DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);//berib yubordik userlarni topib ish bajaradi.
        //authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//paswordlar kodlanilmagan degani.{noop} oddiy tekshiradi.
        //authenticationProvider.setPasswordEncoder(passwordEncoder);
        //authenticationProvider.setPasswordEncoder(passwordEncoder());//authenticationProvider() bo'lganda.
        //authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{  //autorization foydalanuvchi huquqlarini tekshirish.
        //tushunganiskiy
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers(AUTH_WHITELIST).permitAll()
                    //.requestMatchers(AUTH_WHITELIST  /*=="/profile/authorization"*/ ).permitAll()
                    .requestMatchers(HttpMethod.GET,"/task/*","/task").permitAll()
                    .requestMatchers(HttpMethod.DELETE,"/task/*/admin").hasRole("ADMIN")//deleteAsAdmin degani uchun.
                    .requestMatchers(HttpMethod.GET,"/task").permitAll()
                    //.requestMatchers("/api/rooms").permitAll()///api/rooms ga hammaga ochiq lakin api/rooms/1 va hzolarga login so'raydi.
                    //request matchers bu murojat qilinayotgan requestlar manashu urlga mos kelsa  permitAll yani ruhsat ber degani.hammaga
                    //.requestMatchers(HttpMethod.GET,"/api/rooms/*").permitAll()//bunda faqat bitta qiymatga ruhsat bor degani rooms/1/2 ga emas.
                    //.requestMatchers("/api/rooms/*").permitAll()//bu delete add va get zaproslarni barchasida ishlayveradi.
                    //.requestMatchers(HttpMethod.GET,"/api/rooms/**").permitAll() //bu /api/rooms/dwedw/fewfew/fsw uchun ham hamma get zaproslarga ochiq.
                    //.requestMatchers(HttpMethod.GET,"/api/rooms/*").permitAll()  //   /api/rooms/ ,/api/rooms/32,/api/rooms/{1}
                    //.requestMatchers(HttpMethod.GET,"/api/rooms/name","/api/rooms/rating").permitAll() //bunda anashu ikki urlga ochiq degani.

                    //403 bu odamni bu urlni ishlatishga ruhsati yo'q degani.
                    //.requestMatchers("/api/rooms/*").hasRole("ADMIN")
                    //hasRole bu shu rolega mumkin degani.
                    //.requestMatchers("/api/rooms/*").hasAnyRole("USER")//HASANYROLENI HASROLEDAN farqi unda bir nechta rolega ruhsat berolimiz.

                    //.requestMatchers(HttpMethod.GET,"/api/rooms/*").permitAll()  //endi faqat get uchun ochiq qolganlari uchun tizimga kirish kerak deb qo'ydik.

                    .anyRequest()
                    .authenticated();//kirib kelayotgan barcha requestlarni autifikation qil deyapmiz.//bu autificationProvider yuqoridagi metodni chaqiradi.
        }).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);//spring securitydan oldin tekshiryapmiz.
        // UsernamePasswordAuthenticationFilterdan oldin
        //jwtAuthenticationFilter ni ishga tushur! yani meni filterimni oldin ishlat va tokenni tekshir degani


        //http.httpBasic(Customizer.withDefaults());//yozilgan configuratasiyadan foydalanish basicdan
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.cors(AbstractHttpConfigurer::disable);//boshqa domendan kirish mn.
//        http.cors(Customizer.withDefaults());//cors default holatda yoqilgan
//        http.csrf(Customizer.withDefaults());//csrf default holatda yoqilgan

        http.csrf(AbstractHttpConfigurer::disable); // csrf o'chirilgan

        http.cors(Customizer.withDefaults());
        http.cors(httpSecurityCorsConfigurer -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(Arrays.asList("*"));//hohlagan domenlarga ruhsat degani
            configuration.setAllowedMethods(Arrays.asList("*"));//get post va hzo vaproslar
            configuration.setAllowedHeaders(Arrays.asList("*"));//headerga misol authorization

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);//hohlagan patternlarga qo'lla mos tushgan har hil apiga turlicha
            httpSecurityCorsConfigurer.configurationSource(source);
        });



        return http.build();

    }


    /*
    rawpasword shifrlanmagan pasword
    encodedpasword dbdan kelgan pasword
     */
    /*
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {//userni shifrlanmagan paswordini taqdim qiladi
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5Util= MD5Util.getMd5(rawPassword.toString());
                return md5Util.equals(encodedPassword);
            }
        };
    }

     */

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();//// Bu CustomUserDetailsService ni va passwordEncoder ni biladi
    }

}
