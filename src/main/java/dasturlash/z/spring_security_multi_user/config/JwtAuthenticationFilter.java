package dasturlash.z.spring_security_multi_user.config;

import dasturlash.z.spring_security_multi_user.dto.JwtDTO;
import dasturlash.z.spring_security_multi_user.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {//permitall urllar uchun
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SecurityConfig.AUTH_WHITELIST)
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, /* tizimga kirishi login qilishi uchun tokenni tekshiradi */
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header= request.getHeader("Authorization");//task.httpda Authorization bilan berilgan bearer bn berilgan qiymatni beradi
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continue the filter chain //keyingi filterga o't degani yangi versiyalarda

            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //eski versiyalarda keyingi chainlarga o'tkazmaydi.
            return;
        }

        try {
            final String token = header.substring(7).trim();//bearer dan keyinini kesib oldi.
            JwtDTO jwtDTO = JwtUtil.decode(token);//jwtdan userni olish
            String phone = jwtDTO.getUsername();//#####################################################################################################################
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            /*
            UsernamePasswordAuthenticationToken — bu Spring Securityning asosiy sinflaridan biri bo‘lib, foydalanuvchining autentifikatsiya (tizimga kirish) holatini ifodalaydi.

            userDetails – token orqali aniqlangan foydalanuvchi (telefon raqami bo‘yicha yuklangan).

            null – bu joyda credential (masalan, parol) berilmayapti, chunki foydalanuvchi JWT token orqali tasdiqlangan.

            userDetails.getAuthorities() – foydalanuvchining rollari (masalan, ROLE_ADMIN, ROLE_USER).
             */
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response); // Continue the filter chain //keyingi filterga o't degani yangi versiyalarda

            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //eski versiyalarda keyingi chainlarga o'tkazmaydi.
            return;

        }catch (JwtException | UsernameNotFoundException e){
            filterChain.doFilter(request, response); // Continue the filter chain //keyingi filterga o't degani yangi versiyalarda

            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //eski versiyalarda keyingi chainlarga o'tkazmaydi.
            return;

        }
    }
}
