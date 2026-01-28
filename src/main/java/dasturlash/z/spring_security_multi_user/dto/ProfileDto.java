package dasturlash.z.spring_security_multi_user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.z.spring_security_multi_user.enums.GeneralSatus;
import dasturlash.z.spring_security_multi_user.enums.ProfileRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) //qaysinidir qiymati null bo'lsa jsonga qo'shmaydi.
public class ProfileDto {

    private String id;

    private String name;

    private String surname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfileRoleEnum getRole() {
        return role;
    }

    public void setRole(ProfileRoleEnum role) {
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    private String phone;

    private String password;

    private ProfileRoleEnum role;
    private String jwt;
}
