package dasturlash.z.spring_security_multi_user.service;

import dasturlash.z.spring_security_multi_user.config.CustomUserDetails;
import dasturlash.z.spring_security_multi_user.dto.*;
import dasturlash.z.spring_security_multi_user.enums.GeneralSatus;
import dasturlash.z.spring_security_multi_user.exception.AppBadRequestException;
import dasturlash.z.spring_security_multi_user.model.ProfileEntity;
import dasturlash.z.spring_security_multi_user.repository.ProfileRepository;
import dasturlash.z.spring_security_multi_user.util.JwtUtil;
import dasturlash.z.spring_security_multi_user.util.MD5Util;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;//security configdagidan oldi.

    @Autowired
    private AuthenticationManager authenticationManager;

    public ProfileDto registration(ProfileDto dto){
        Optional<ProfileEntity>optional=profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if(optional.isPresent()){ //phone bazada bo'lsa null qaytaradi.
            return null;
        }

        ProfileEntity entity=new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone()); //buni id unique qilmoqchimiz.
        //entity.setPassword(dto.getPassword());
        //entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        //BCryptPasswordEncoder bc=new BCryptPasswordEncoder();//har registration chaqirilganda yangidan bean yaratyapti.
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        entity.setRole(dto.getRole());
        profileRepository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }


//    public AuthResponseDTO authorization(AuthRequestDTO auth) {//login parolni olish uchun tizimda bor yo'qligini tekshirish uchun
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getPhone(), auth.getPassword()));
//
//            if (authentication.isAuthenticated()) {
//                CustomUserDetails profile = (CustomUserDetails) authentication.getPrincipal();
//                AuthResponseDTO response = new AuthResponseDTO();
//                response.setName(profile.getName());
//                response.setSurname(profile.getSurname());
//                response.setPhone(profile.getPhone());
//                response.setRole(profile.getRole());
//                response.setJwtToken(JwtUtil.encode(profile.getPhone(), profile.getRole().name()));
//                return response;
//            }
//        } catch (BadCredentialsException e) {
//            throw new UsernameNotFoundException("Phone or password wrong");
//        }
//        throw new UsernameNotFoundException("Phone or password wrong");
//    }

    public AuthResponseDTO authorization(AuthRequestDTO auth) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getPhone(), auth.getPassword()));//dbdan qidirib rollari bn qaytaradi

            if (authentication.isAuthenticated()) { //autorizatsiyadan o'tdimi degani.
                CustomUserDetails profile = (CustomUserDetails) authentication.getPrincipal();
                AuthResponseDTO response = new AuthResponseDTO();
                response.setName(profile.getName());
                response.setSurname(profile.getSurname());
                response.setPhone(profile.getUsername());
                response.setRole(profile.getRole());
                //response.setJWTToken(JwtUtil.encode(profile.getUsername(), profile.getRole().name()));
                response.setAccessToken(JwtUtil.encode(profile.getUsername(), profile.getRole().name()));
                response.setRefreshToken(JwtUtil.generateRefreshToken(profile.getUsername(), profile.getRole().name()));
                return response;
            }
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Phone or password wrong");
        }
        throw new UsernameNotFoundException("Phone or password wrong");
    }



//        Optional<ProfileEntity> optional=profileRepository.findByPhoneAndVisibleTrue(auth.getPhone());
//        if(optional.isEmpty()){
//            throw new AppBadRequestException("Phone or password wrong 1");
//        }
//        ProfileEntity profile= optional.get();
//        if(!bCryptPasswordEncoder.matches(auth.getPassword(),profile.getPassword())){//equalsi bu bazadagi parol va kelgan parolni solishtiradi bycrpt orqali
//            throw new AppBadRequestException("Phone or password wrong 2");
//        }
//
//        if(!profile.getStatus().equals(GeneralSatus.ACTIVE)){
//            throw new AppBadRequestException("Phone or password wrong 3");
//        }
//
//        ProfileDto response=new ProfileDto();
//        response.setRole(profile.getRole());
//        response.setSurname(profile.getSurname());
//        response.setName(profile.getName());
//        response.setPhone(profile.getPhone());
//        response.setJwt(JwtUtil.encode(profile.getPhone(),profile.getRole().name()));
//        return response;
    //}


    public TokenDTO getNewAccessToken(TokenDTO dto) {
        try {
            //if (JwtUtil.isValid(dto.getRefreshToken())) {//decoded tekshiradi exp mi yo'qligini
                JwtDTO jwtDTO = JwtUtil.decode(dto.getRefreshToken());

                Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(jwtDTO.getUsername());

                if (optional.isPresent()) {
                    ProfileEntity profile = optional.get();

                    if(profile.getStatus().equals(GeneralSatus.NOT_ACTIVE)){
                        throw new AppBadRequestException("Invalid token");
                    }

                    TokenDTO response = new TokenDTO();
                    response.setAccessToken(JwtUtil.encode(profile.getPhone(), profile.getRole().name()));
                    //response.setRefreshToken(JwtUtil.generateRefreshToken(profile.getPhone(), profile.getRole().name()));//doimiy ochiq bo'lsa turaveradi
                    return response;
                }

        } catch (JwtException e) {

        }
        throw new AppBadRequestException("Invalid token");
    }


}




