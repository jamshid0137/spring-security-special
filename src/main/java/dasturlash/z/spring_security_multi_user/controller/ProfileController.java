package dasturlash.z.spring_security_multi_user.controller;


import dasturlash.z.spring_security_multi_user.dto.AuthRequestDTO;
import dasturlash.z.spring_security_multi_user.dto.AuthResponseDTO;
import dasturlash.z.spring_security_multi_user.dto.ProfileDto;
import dasturlash.z.spring_security_multi_user.dto.TokenDTO;
import dasturlash.z.spring_security_multi_user.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/registration")
    public ResponseEntity<ProfileDto>create(@RequestBody ProfileDto profileDto){
        ProfileDto result=profileService.registration(profileDto);
        String l=" fs";
        StringBuilder sb=new StringBuilder();
        //sb.
        //sb.reverse().toString();
        System.out.println();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/authorization")
    public ResponseEntity<AuthResponseDTO>authorization(@RequestBody AuthRequestDTO authRequestDTO){
        AuthResponseDTO result=profileService.authorization(authRequestDTO);
        //String s="fsf";
        //s.strip();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO>refreshToken(@RequestBody TokenDTO authRequestDTO){
        TokenDTO result=profileService.getNewAccessToken(authRequestDTO);
        return ResponseEntity.ok(result);
    }
}
/*
1010101
7 likka qanday o'tkazaman ?
1*7^^x
 */