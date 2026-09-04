package lv.venta.forest.repo;

import lv.venta.forest.model.ForestManagementPractice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ForestManagementPracticeRepository extends JpaRepository<ForestManagementPractice, Long> {
    List<ForestManagementPractice> findByPracticeType(String practiceType);
    List<ForestManagementPractice> findByStatus(String status);
}
