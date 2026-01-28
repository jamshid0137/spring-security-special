package dasturlash.z.spring_security_multi_user.config;

import dasturlash.z.spring_security_multi_user.enums.GeneralSatus;
import dasturlash.z.spring_security_multi_user.enums.ProfileRoleEnum;
import dasturlash.z.spring_security_multi_user.model.ProfileEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private String phone;
    private String password;
    private ProfileRoleEnum role;
    private GeneralSatus status;


    private String surname;
    private String name;


    private String id;
    //private ProfileEntity profileEntity;//va getter va CustomUserDetails contructorda berib qo'yaveramiz farqi yo'q.getusername va hzo.

    public CustomUserDetails(ProfileEntity profile){
        this.phone=profile.getPhone();
        this.password=profile.getPassword();
        this.role=profile.getRole();
        this.status=profile.getStatus();

        this.name= profile.getName();
        this.surname= profile.getSurname();

        this.id=profile.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority>roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.name()));//bir nechta role bo'lsa listga berib yuborsak bo'ladi shunday.


        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralSatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {//biz visibleni repositorydan tru qilganini olganimiz uchun shartmas.
        return true;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ProfileRoleEnum getRole() {
        return role;
    }
}
//23 bitdi.
//24 bitdi.
//25 bitdi.
//26 bitdi.
//27 bitdi
//28 bitdi
//36 bitdi.
//38 xato
//44 48 bo'ldi.
