package dasturlash.z.spring_security_multi_user.dto;


import dasturlash.z.spring_security_multi_user.enums.ProfileRoleEnum;

public class AuthResponseDTO {
    private String name;
    private String surname;
    private String phone;
    private ProfileRoleEnum role;
   // private String jwtToken;

    private String accessToken;

    private String refreshToken;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProfileRoleEnum getRole() {
        return role;
    }

    public void setRole(ProfileRoleEnum role) {
        this.role = role;
    }

//    public String getJwtToken() {
//        return jwtToken;
//    }
//
//    public void setJwtToken(String jwtToken) {
//        this.jwtToken = jwtToken;
//    }
}