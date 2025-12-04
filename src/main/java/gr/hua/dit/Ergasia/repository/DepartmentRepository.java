package gr.hua.dit.adminbt.adminbt.repository;

import gr.hua.dit.adminbt.adminbt.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
