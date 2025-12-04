package gr.hua.dit.Ergasia.repository;

import gr.hua.dit.Ergasia.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Εύρεση αιτήσεων συγκεκριμένου πολιτη
    List<Application> findByCitizenId(String citizenId);
}
