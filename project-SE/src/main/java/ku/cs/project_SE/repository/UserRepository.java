package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
//Tee
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailOrPhoneNumber(String email,String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String username);
}