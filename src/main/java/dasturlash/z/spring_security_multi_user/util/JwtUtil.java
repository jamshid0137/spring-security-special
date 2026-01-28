package dasturlash.z.spring_security_multi_user.util;


import dasturlash.z.spring_security_multi_user.dto.JwtDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
        private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    //private static final int tokenLiveTime = 1000;

    private static final long refreshTokenLiveTime = 1000L * 3600 * 24 * 30;//30 day
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(String username, String role) { //jwt yasaydi
        Map<String, Object> extraClaims = new HashMap<>();
        //extraClaims.put("username", username);//"username"bu  usernameni kaliti
        extraClaims.put("role", role);//"role", roleni kaliti bu
        //istalgancha qiymat berib shifrlasak bo'laveradi.

        return Jwts
                .builder()
                .claims(extraClaims) //username role yashirinishi kk bo'lgan malumotlar
                .subject(username)  //muhim rol o'ynaydigan qiymat
                .issuedAt(new Date(System.currentTimeMillis()))  //jwt yasalgan vaqtni berish
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))  //yaroqlilik muddati jwtni//yaratilayotgan vaqt +1kun
                .signWith(getSignInKey())
                .compact();
    }


    public static JwtDTO decode(String token) {//token ichidagi username hokazo hokazolarni ajratib olib return qilamiz.
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username =  claims.getSubject();
        ///String username = (String) claims.get("username");
        String role = (String) claims.get("role");
        return new JwtDTO(username, role);
    }



    private static SecretKey getSignInKey() {//secret keyni shifrlash yani raqamli imzolash tekshirish uchun
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);//dekodlash jarayoni secretkey dekodlanadi fef32r232f4rrrf shunaqaroq bb qoladi.
        return Keys.hmacShaKeyFor(keyBytes);//HMAC-SHA (HS256) algoritmiga mos keladigan kalit yaratadi.
    }

    public static String generateRefreshToken(String username, String role) { //jwt yasaydi
        Map<String, Object> extraClaims = new HashMap<>();
        //extraClaims.put("username", username);//"username"bu  usernameni kaliti
        extraClaims.put("role", role);//"role", roleni kaliti bu
        //istalgancha qiymat berib shifrlasak bo'laveradi.

        return Jwts
                .builder()
                .claims(extraClaims) //username role yashirinishi kk bo'lgan malumotlar
                .subject(username)  //muhim rol o'ynaydigan qiymat
                .issuedAt(new Date(System.currentTimeMillis()))  //jwt yasalgan vaqtni berish
                .expiration(new Date(System.currentTimeMillis() + refreshTokenLiveTime))  //yaroqlilik muddati jwtni//yaratilayotgan vaqt +1kun
                .signWith(getSignInKey())
                .compact();
    }


    public static boolean isValid(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                //.getBody()
                .getExpiration().after(new Date());//tokenni exp vaqti hozirdan keyinmi degani ?
    }


}


/*
jwt muddati tugasa yana qaytadan login parol qilib kirishi kerak bo'ladi.

jwt agar secret key mos kelmasa decodlamaydi.
ichidagi malumotlarni jwtdan ko'rish mumkin lekin secretkeyni bilmasa qiymatni o'zgartira olmaydiyani
kerakli malumotlarni jwt ga bermaymiz masalan id,username,keraksiy malumotlar beramiz.
 */
