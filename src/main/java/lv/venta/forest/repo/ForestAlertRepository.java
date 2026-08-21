package lv.venta.forest.repo;

import lv.venta.forest.model.ForestAlert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForestAlertRepository extends CrudRepository<ForestAlert, Long> {}
