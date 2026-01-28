package dasturlash.z.spring_security_multi_user.model;

import dasturlash.z.spring_security_multi_user.enums.GeneralSatus;
import dasturlash.z.spring_security_multi_user.enums.ProfileRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") //global miqiyosda unikal degani
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="phone")
    private String phone;

    @Column(name="password")
    private String password;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private GeneralSatus status=GeneralSatus.ACTIVE;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private ProfileRoleEnum role;

    @Column(name="visible")
    private Boolean visible=Boolean.TRUE; //profile o'chirib yuborilmaganni bilish

    @Column(name="created_date")
    private LocalDateTime createdDate=LocalDateTime.now();


}
