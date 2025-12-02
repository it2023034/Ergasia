package gr.hua.dit.Ergasia.repository;

import gr.hua.dit.Ergasia.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository for managing {@link Employee} entity.
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByUsername(String username);
    Optional<Employee> findByUsername(String username);
}

