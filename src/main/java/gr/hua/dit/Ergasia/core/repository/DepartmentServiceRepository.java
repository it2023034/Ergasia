package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.DepartmentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentServiceRepository extends JpaRepository<DepartmentService, Long> {
    Optional<DepartmentService> findByServiceName(String serviceName);
}


