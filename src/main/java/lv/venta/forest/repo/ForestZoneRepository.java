package lv.venta.forest.repo;

import lv.venta.forest.model.ForestZone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForestZoneRepository extends CrudRepository<ForestZone, Long> {}
