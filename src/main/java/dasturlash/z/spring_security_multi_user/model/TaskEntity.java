package dasturlash.z.spring_security_multi_user.model;//package dasturlash.z.spring_security_multi_user.model;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Entity
//@Table(name="task")
//public class TaskEntity {
//    @Id
//    @GeneratedValue(generator = "system-uuid") //global miqiyosda unikal degani
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    //@GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String id;
//
//    @Column(name = "title",columnDefinition = "text")//ko'p malumot yoziladi shuning uchun.
//    private String title;
//
//    @Column(name = "content",columnDefinition = "text")
//    private String content;
//
//    @Column(name = "create_data")
//    private LocalDateTime createDate=LocalDateTime.now();
//
//    @Column(name = "profile_id")
//    private String profileId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "profile_id",updatable = false,insertable = false)
//    private ProfileEntity profile;
//}
//
////profile_id bu taskni yaratayotgan odam.


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="task")
public class TaskEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") //global miqiyosda unikal degani
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;
    @Column(name="title")
    private String title;
    @Column(name="content",columnDefinition = "text")
    private String content;
    @Column(name="created_date",columnDefinition = "text")
    private LocalDateTime createdDate;

    @Column(name = "profile_id")
    private String profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",updatable = false,insertable = false)
    private ProfileEntity profileEntity;

//ko'p tasklar bir profilega tegishli.
//    public TaskEntity() {
//    }
//
//    public TaskEntity(String id, String title, String content, LocalDateTime createdDate) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.createdDate = createdDate;
//    }
}