package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.DepartmentService;
import gr.hua.dit.Ergasia.core.model.Employee;
import gr.hua.dit.Ergasia.core.model.Request;
import gr.hua.dit.Ergasia.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByDepartmentService(DepartmentService departmentService);

    boolean existsByTitleAndEmployee(String title, Employee employee);

    List<Request> findByCitizen(User citizen);
}

