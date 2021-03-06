package m2t.jobloader.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import m2t.jobloader.dao.model.Job;

public interface JobRepository extends CrudRepository<Job	, Long> {
	
	List<Job> findByContainerOrderByTotalBoxesDesc(String container);
	Job findByJobCode(String jobCode);
	
	List<Job> findByContainerAndDeliverToCodeIsNull(String container);
	
	
	@Query(value = "select count(*) from job j where j.container like  %:containerNumber%", nativeQuery = true)
	int countByContainerNumber(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select count(delivery_to_code) from job where container like %:containerNumber% and delivery_to_code is not null", nativeQuery = true)
	int countConfirmedJobs(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select count(  original_client) from job where container like %:containerNumber%", nativeQuery = true)
	int countOriginalJobs(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select sum(tot_boxes) from job where container like %:containerNumber%", nativeQuery = true)
	int countTotalBoxes(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select sum(tot_frames) from job where container like %:containerNumber%", nativeQuery = true)
	int countTotalFrames(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select sum(tot_hardware) from job where container like %:containerNumber%", nativeQuery = true)
	int countTotalHardware(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select sum(tot_panels) from job where container like %:containerNumber%", nativeQuery = true)
	int countTotalPanels(@Param("containerNumber") String containerNumber);
	
	@Query(value = "select sum(tot_blinds) from job where container like %:containerNumber%", nativeQuery = true)
	int countTotalBlinds(@Param("containerNumber") String containerNumber);
	
	
}
