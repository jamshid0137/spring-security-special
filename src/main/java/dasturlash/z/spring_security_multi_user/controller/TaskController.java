package dasturlash.z.spring_security_multi_user.controller;

import dasturlash.z.spring_security_multi_user.config.CustomUserDetails;
import dasturlash.z.spring_security_multi_user.dto.TaskDto;
//import dasturlash.z.spring_security_multi_user.service.TaskService;
import dasturlash.z.spring_security_multi_user.model.TaskEntity;
import dasturlash.z.spring_security_multi_user.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Source;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/task")
@EnableMethodSecurity(prePostEnabled = true)
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto dto) {




        //SecurityContextHolder bu authrntification user malumotini bu yerga saqlaydi shundan olyapmiz.
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();//kim task create qilganini bilish va malumotini olish uchun.
        //get username---murojat qilayotgan odamni nameni olish.


        String currentPrincipalName=authentication.getName();
        System.out.println(currentPrincipalName);

        //get user object
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();//principal bu customdetailsni return qiladi
        System.out.println(user);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        System.out.println(user.getId());

        //Collection<? extends> profileU=user.getAuthorities();






        TaskDto result = taskService.create(dto);
        return ResponseEntity.ok(result);
    }


    @GetMapping
    public ResponseEntity<List<TaskDto>> getAll() {
        //String profileId=SpringSecurityUtil.getProfileId();
        List<TaskDto> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskDto>> getMyTasks() {
        //String profileId=SpringSecurityUtil.getProfileId();
        List<TaskDto> result = taskService.getProfileTaskList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable String id) {
        TaskDto result = taskService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Boolean> update(@RequestBody TaskDto student,
                                          @PathVariable("id") String id) {
        Boolean result = taskService.update(student, id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") //ONLY faqat admin chaqira oladi qolganlar 403
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/admin")
    public ResponseEntity<Void> deleteAsAdmin(@PathVariable("id") String id) {
        taskService.deleteAsAdmin(id);
        return ResponseEntity.ok().build();
    }
//    @GetMapping("/namuna")
//    public ResponseEntity<List<TaskDto>> getAll1() {
//        //String profileId=SpringSecurityUtil.getProfileId();
//        //List<TaskDto> result = taskService.getAll();
//        List<TaskDto>l1=taskService.namuna();
//        return ResponseEntity.ok(l1);
//    }


}
