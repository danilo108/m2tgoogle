package m2t.jobloader.service.controllers.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import m2t.jobloader.dao.model.Container;
import m2t.jobloader.dao.repositories.ContainerRepository;
import m2t.jobloader.dao.repositories.JobRepository;
import m2t.jobloader.service.controllers.model.WebAPIResponse;
import m2t.service.model.jobloader.ContainerDTO;

@RestController("/webapi")
public class WebAPI {

	@Autowired
	ContainerRepository containerRepository;
	@Autowired
	JobRepository jobRepository;

	@RequestMapping(path = "webapi/containers", method = RequestMethod.GET)
	WebAPIResponse<ContainerDTO> getContainers() {
		WebAPIResponse<ContainerDTO> response = new WebAPIResponse<ContainerDTO>("getContainers");
		List<Container> allDao = new ArrayList<>();
		containerRepository.findAll().forEach(c -> allDao.add(c));
		List<ContainerDTO> entities = allDao.stream().sorted(Comparator.comparing(Container::getId).reversed()).map(dao -> {
			ContainerDTO dto = new ContainerDTO();
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
