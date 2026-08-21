package lv.venta.forest.repo;

import lv.venta.forest.model.ForestSensor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForestSensorRepository extends CrudRepository<ForestSensor, Long> {}
