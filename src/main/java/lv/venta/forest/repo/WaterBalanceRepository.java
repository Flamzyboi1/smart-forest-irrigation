package lv.venta.forest.repo;

import lv.venta.forest.model.WaterBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WaterBalanceRepository extends JpaRepository<WaterBalance, Long> {
    List<WaterBalance> findByForestZoneId(Long forestZoneId);
}
