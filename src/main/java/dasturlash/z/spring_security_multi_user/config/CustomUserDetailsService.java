package dasturlash.z.spring_security_multi_user.config;

import dasturlash.z.spring_security_multi_user.model.ProfileEntity;
import dasturlash.z.spring_security_multi_user.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity>optional=profileRepository.findByPhoneAndVisibleTrue(username);
        if(optional.isEmpty()){//user bo'lmasa
            throw new UsernameNotFoundException(username);
        }
        ProfileEntity profile= optional.get();//userni oldik.
        //profileni endi userdetails ko'rinishida berib yuboramiz.
        CustomUserDetails userDetails=new CustomUserDetails(profile);

        return userDetails;
    }
}
//CustomUserDetailsService userrepositorydan malumotlarini topib olamiz
//CustomUserDetails user malumotlarini berib yuboramiz.
