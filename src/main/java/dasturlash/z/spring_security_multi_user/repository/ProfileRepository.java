package dasturlash.z.spring_security_multi_user.repository;

import dasturlash.z.spring_security_multi_user.model.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity,String> {
    Optional<ProfileEntity>findByPhoneAndVisibleTrue(String phone);//visible sistemadan chiqarilmaganini tekshirish.
}
