package dasturlash.z.spring_security_multi_user.repository;

import dasturlash.z.spring_security_multi_user.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//package dasturlash.z.spring_security_multi_user.repository;
//
//import dasturlash.z.spring_security_multi_user.model.TaskEntity;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
////package dasturlash.z.spring_security_multi_user.repository;
////
////
//////import dasturlash.z.spring_security_multi_user.model.ProfileEntity;
//////import dasturlash.z.spring_security_multi_user.model.TaskEntity;
//////import jakarta.persistence.Entity;
//////import dasturlash.z.spring_security_multi_user.model.TaskEntity;
////import dasturlash.z.spring_security_multi_user.model.TaskEntity;
////import org.springframework.data.repository.CrudRepository;
////import org.springframework.stereotype.Repository;
////
////import java.util.List;
//////import org.springframework.stereotype.Repository;
////


@Repository
public interface TaskRepository extends CrudRepository<TaskEntity,String> {
    List<TaskEntity> findByProfileId(String profileId);//task da profileId o'zgaruvchisi bor.
    List<TaskEntity>findAll();
    //void deleteById(String id);

    Optional<TaskEntity> findById(String id);

//    @Query("select t from TaskEntity t")
//    List<TaskEntity>findAllTasks();


}
