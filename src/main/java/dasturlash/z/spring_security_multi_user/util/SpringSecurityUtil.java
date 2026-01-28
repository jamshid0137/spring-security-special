package dasturlash.z.spring_security_multi_user.util;

import dasturlash.z.spring_security_multi_user.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpringSecurityUtil {
    public static String getCurrentProfileId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        return user.getId();
    }
    public static Collection<GrantedAuthority>getProfileGrantedAuthority(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        return (Collection<GrantedAuthority>) user.getAuthorities();
    }

    public static List<String>getProfileRoleList(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority>roles=user.getAuthorities();
        List<String> roleList = new ArrayList<>();
        for (GrantedAuthority authority : roles) {
            String role = authority.getAuthority();
            if (role.startsWith("ROLE_")) {
                roleList.add(role.substring(5)); // "ROLE_" prefiksini olib tashlash
            }
        }
        return roleList;
    }


}
