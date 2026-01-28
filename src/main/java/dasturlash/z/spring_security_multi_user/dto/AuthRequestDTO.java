package dasturlash.z.spring_security_multi_user.dto;


public class AuthRequestDTO {//profile servicedagi login parolni olish uchun
    private String phone;
    private String password;

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
}