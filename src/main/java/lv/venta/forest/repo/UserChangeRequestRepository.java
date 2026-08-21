package lv.venta.forest.repo;

import lv.venta.forest.model.UserChangeRequest;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserChangeRequestRepository extends CrudRepository<UserChangeRequest,Long> {
    List<UserChangeRequest> findByStatusOrderByRequestedAtDesc(String status);
    List<UserChangeRequest> findByUserIdOrderByRequestedAtDesc(Long userId);
    boolean existsByUserIdAndStatus(Long userId, String status);
}
