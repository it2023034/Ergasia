package gr.hua.dit.Ergasia.core.repository;

import gr.hua.dit.Ergasia.core.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Εύρεση αιτήσεων συγκεκριμένου πολιτη
    List<Application> findByCitizenId(String citizenId);
}
