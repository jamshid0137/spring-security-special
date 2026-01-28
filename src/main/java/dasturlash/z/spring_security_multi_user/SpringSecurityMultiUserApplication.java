package dasturlash.z.spring_security_multi_user;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityMultiUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringSecurityMultiUserApplication.class, args);
	}

}




/*
Authorization: Basic Base64(username,password)
spring security userni usernomi bn topishi customuserdetailsservicega murojat qiladi.
customdetailsservice bazadan profileentityni olib kelib user detailsga beradi.
custonuserservice esa customuserdetail qilib beradi securityga
security esa custom userserviceni metodlariga murojat qilib user malumotlarini oladi.

HttpBasic - bu authentication qilish usullaridan bittasi. ( authentication -
 berilgan login va parolli user bor yoki yo'qligini aniqlash.)

Ya'ni murojat qilayotgan foydalanuvchining identifikatsiya qilishda HttpBasic-dan foydalansak bo'ladi.
 Bunda foydalanuvchi o'zining login va parollarini base64 algoritmidan foydalanib ularni shifirlaydi va
  HTTP Request-ning Header qismida berib yuboradi.


bazaga qo'shayotganda ROLE_ ni qo'shib berish kk bazaga bo'lmasa taqqoslay olmidi va xato beradi.

 */
