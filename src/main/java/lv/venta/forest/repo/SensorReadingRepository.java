package lv.venta.forest.repo;

import lv.venta.forest.model.SensorReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorReadingRepository extends CrudRepository<SensorReading, Long> {}
