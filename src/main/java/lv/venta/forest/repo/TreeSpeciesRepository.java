package lv.venta.forest.repo;

import lv.venta.forest.model.TreeSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreeSpeciesRepository extends JpaRepository<TreeSpecies, Long> {
    List<TreeSpecies> findByIsDominantTrue();
    List<TreeSpecies> findByType(String type);
    List<TreeSpecies> findBySoilType(String soilType);
}
