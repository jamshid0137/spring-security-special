package dasturlash.z.spring_security_multi_user.service;//package dasturlash.z.spring_security_multi_user.service;//package dasturlash.z.spring_security_multi_user.service;
//
////import dasturlash.z.spring_security_multi_user.model.TaskEntity;
////import dasturlash.z.spring_security_multi_user.repository.TaskRepository;
//import dasturlash.z.spring_security_multi_user.dto.TaskDto;
//import dasturlash.z.spring_security_multi_user.model.ProfileEntity;
//import dasturlash.z.spring_security_multi_user.model.TaskEntity;
//import dasturlash.z.spring_security_multi_user.repository.ProfileRepository;
//import dasturlash.z.spring_security_multi_user.repository.TaskRepository;
//import dasturlash.z.spring_security_multi_user.util.SpringSecurityUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class TaskService {
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private ProfileRepository profileRepository;
//
//    //private List<TaskDto>taskList;
//    public TaskDto create(TaskDto dto) {
//        String profileId= SpringSecurityUtil.getCurrentProfileId();
//        //ProfileEntity profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
//        TaskEntity taskEntity=new TaskEntity();
//        taskEntity.setTitle(dto.getTitle());
//        taskEntity.setContent(dto.getContent());
//        taskEntity.setCreateDate(LocalDateTime.now());
//        //taskEntity.setProfile(profile);
//        taskEntity.setProfileId(profileId);
//
////        System.out.println("Task DTO title: " + dto.getTitle());
////        System.out.println("Task DTO content: " + dto.getContent());
//        String u=taskEntity.getId();
//        taskRepository.save(taskEntity);
//
//        dto.setId(taskEntity.getId());
//
////        dto.setId(UUID.randomUUID().toString());
////        dto.setCreatedDate(LocalDateTime.now());
//
//        return dto;
//    }
//
//    public List<TaskDto> getAll() {
//        return null;
//    }
//
//    public TaskDto getById(String id) {
//
//        return null;
//    }
//
//    public Boolean update(TaskDto dto, String id) {
//
//        //.setTitle(dto.getTitle());
//       // .setContent(dto.getContent());
//        return true;
//    }
//
//    public Boolean delete(String id) {
//        return true;
//    }
//}

//import dasturlash.z.dto.TaskDTO;
import dasturlash.z.spring_security_multi_user.dto.TaskDto;
import dasturlash.z.spring_security_multi_user.enums.ProfileRoleEnum;
import dasturlash.z.spring_security_multi_user.exception.AppBadRequestException;
import dasturlash.z.spring_security_multi_user.exception.ItemNotFoundException;
import dasturlash.z.spring_security_multi_user.model.TaskEntity;
import dasturlash.z.spring_security_multi_user.repository.TaskRepository;
import dasturlash.z.spring_security_multi_user.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    //private List<TaskDto> taskList;

//    public TaskService() {
//        taskList = new LinkedList<>();
//
//        TaskDto task1 = new TaskDto();
//        task1.setId(UUID.randomUUID().toString());
//        task1.setTitle("Bozor");
//        task1.setContent("Bozorga borib meva-chevalar olib kelish kerak.");
//        task1.setCreatedDate(LocalDateTime.now());
//        taskList.add(task1);
//
//        TaskDto task2 = new TaskDto();
//        task2.setId(UUID.randomUUID().toString());
//        task2.setTitle("Spring Security");
//        task2.setContent("Dasturlash.uz ga kirib Spring Securityni o'rganishim kerak.");
//        task2.setCreatedDate(LocalDateTime.now());
//        taskList.add(task2);
//    }

    public TaskDto create(TaskDto dto) {
        String profileId=SpringSecurityUtil.getCurrentProfileId();
        TaskEntity e=new TaskEntity();
        e.setTitle(dto.getTitle());
        e.setContent(dto.getContent());
        e.setCreatedDate(LocalDateTime.now());
        e.setProfileId(profileId);
        taskRepository.save(e);
        dto.setId(e.getId());
        return dto;
    }

    public List<TaskDto> getAll() {
        //String profileId= SpringSecurityUtil.getCurrentProfileId();
        List<TaskEntity>l=taskRepository.findAll();
        List<TaskDto>l2=new LinkedList<>();
        for(TaskEntity t:l){
            l2.add(toDto(t));
        }
        return l2;
    }

    public List<TaskDto> getProfileTaskList() {
        String profileId= SpringSecurityUtil.getCurrentProfileId();
        List<TaskEntity>list=taskRepository.findByProfileId(profileId);
        List<TaskDto> list1=new ArrayList<>();
        for(TaskEntity t:list){
            list1.add(toDto(t));
        }
        return list1;
    }
    public TaskEntity get(String id) {
        Optional<TaskEntity> entity= taskRepository.findById(id);
               if(entity.isPresent())return entity.get();
               else{
                   throw new ItemNotFoundException("task not found");
               }
    }


//    public TaskEntity get(String id){
//        return taskRepository.findById(id).orElseThrow(()->
//             new ItemNotFoundException("task not found");
//        );
//    }

    public TaskDto getById(String id) {
        TaskEntity entity=taskRepository.findById(id).get();

        return toDto(entity);
    }

    public Boolean update(TaskDto dto, String id) {//buniyam adminga ruhr=sat berish kerak topshiriq
        //TaskDto exists = getById(id);

        TaskEntity entity=get(id);
        String profileId=SpringSecurityUtil.getCurrentProfileId();
        if(!entity.getProfileId().equals(profileId)){
            List<String>roles=SpringSecurityUtil.getProfileRoleList();
            if(!roles.contains("ADMIN")){
            throw new AppBadRequestException("it does not belong to the current profile ! ");}
        }
        if (entity == null) {
            return false;
        }

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        taskRepository.save(entity);
        return true;
    }

    public Boolean delete(String id) {
        TaskEntity entity=get(id);
        String profileId=SpringSecurityUtil.getCurrentProfileId();
        if(!entity.getProfileId().equals(profileId)){
            List<String>roles=SpringSecurityUtil.getProfileRoleList();
            System.out.println("Current User Roles: " + roles);
            if(!roles.contains(/*ProfileRoleEnum.ROLE_ADMIN.name()*/"ADMIN")){
                throw new AppBadRequestException("it does not belong to the current profile ! ");}
        }


        taskRepository.delete(entity);
        return true;
    }

    public Boolean deleteAsAdmin(String id) {
        taskRepository.deleteById(id);
        return true;
    }

//    public List<TaskDto> namuna(){
//        List<TaskEntity>l1= (taskRepository.findAllTasks());
//        List<TaskDto> list1=new ArrayList<>();
//        for(TaskEntity t:l1){
//            list1.add(toDto(t));
//        }
//        return list1;
//    }

    private TaskDto toDto(TaskEntity t){



            TaskDto td=new TaskDto();
            td.setId(t.getId());
            td.setContent(t.getContent());
            td.setCreatedDate(t.getCreatedDate());
            td.setTitle(t.getTitle());



        return td;
    }

}

