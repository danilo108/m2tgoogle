package m2t.jobloader.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import m2t.jobloader.dao.model.Container;

public interface ContainerRepository extends PagingAndSortingRepository<Container	, Long> {
	public Container findByContainerNumber(String containerNumber);
	public Container findByOrOriginalFileName(String originalFileName);
}
