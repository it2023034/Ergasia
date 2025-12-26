package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByAfm(String afm);
    boolean existsByIdCardNumber(String idCardNumber);
}
