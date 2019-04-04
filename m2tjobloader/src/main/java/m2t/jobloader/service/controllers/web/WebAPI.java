package m2t.jobloader.service.controllers.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import m2t.jobloader.dao.model.Container;
import m2t.jobloader.dao.repositories.ContainerRepository;
import m2t.jobloader.dao.repositories.JobRepository;
import m2t.jobloader.service.controllers.model.WebAPIResponse;
import m2t.service.model.jobloader.ContainerDTO;

@RestController("/webapi")
@CrossOrigin(origins = "http://localhost:3000")
public class WebAPI {

	@Autowired
	ContainerRepository containerRepository;
	@Autowired
	JobRepository jobRepository;

	@RequestMapping(path = "webapi/containers", method = RequestMethod.GET)
	
	WebAPIResponse<ContainerDTO> getContainers(@RequestParam(name = "pageNumber", defaultValue = "0"	)int pageNumber, @RequestParam(name="pageSize", required = true)int pageSize ) {
		WebAPIResponse<ContainerDTO> response = new WebAPIResponse<ContainerDTO>("getContainers");
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<Container> pageResponse = containerRepository.findAll(pageRequest);
		response.setTotalPages(pageResponse.getTotalPages());
		response.setPageSize(pageResponse.getSize());
		response.setCurrentPage(pageResponse.getNumber());
		List<ContainerDTO> entities = pageResponse.getContent().stream().sorted(Comparator.comparing(Container::getId).reversed()).map(dao -> {
			ContainerDTO dto = new ContainerDTO();
			dto.setContainerId(dao.getId());
			String containerNumber = dao.getContainerNumber();
			dto.setContainerNumber(containerNumber);
			dto.setReportSheetId(dao.getReportSheetId());
			dto.setReportSheetURL(dao.getReportFullURL());
			dto.setSpreadSheetId(dao.getSheetId());
			dto.setSpreadSheetURL(dao.getFullURL());
			dto.setNumberOfJobs(jobRepository.countByContainerNumber(containerNumber));
			dto.setNumberOfConfirmedDockets(jobRepository.countConfirmedDockets(containerNumber));
			dto.setNumberOfOriginalDockets(jobRepository.countOriginalDockets(containerNumber));
			dto.setTotalNumberOfBlinds(jobRepository.countTotalBlinds(containerNumber));
			dto.setTotalNumberOfPanels(jobRepository.countTotalPanels(containerNumber));
			dto.setTotalNumberOfBoxes(jobRepository.countTotalBoxes(containerNumber));
			dto.setTotalNumberOfFrames(jobRepository.countTotalFrames(containerNumber));
			
			return dto;
		}).collect(Collectors.toList());
		response.setEntities(entities);
		return response;
	}
}
