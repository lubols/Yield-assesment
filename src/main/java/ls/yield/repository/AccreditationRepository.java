package ls.yield.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ls.yield.model.Accreditation;

@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, Long>{

	Accreditation findAccrByAccrId(String id);
	
	@Query(value = "SELECT * FROM taccreditation where user_id = ?1", nativeQuery = true)
	List<Accreditation> findAccrByUserId(String id);
	
	@Query(value = "SELECT * FROM taccreditation where user_id = ?1 and accr_status = 'PENDING'", nativeQuery = true)
	List<Accreditation> findPendingAccrByUserId(String id);
	
	@Query(value = "SELECT * FROM taccreditation where accr_status = 'CONFIRMED' and current_timestamp() >= timestamp + interval '30' DAY", nativeQuery = true)
	List<Accreditation> findConfirmedAccrByUserId();
}
