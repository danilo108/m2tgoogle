package m2t.jobloader.service.controllers.web;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import m2t.jobloader.dao.model.Container;
import m2t.jobloader.dao.repositories.ContainerRepository;
import m2t.jobloader.dao.repositories.JobRepository;
import m2t.jobloader.service.controllers.SheetController;
import m2t.jobloader.service.controllers.WebSchedulerController;
import m2t.jobloader.service.controllers.model.BasicServiceResponse;
import m2t.jobloader.service.controllers.model.PrintReportResponse;
import m2t.jobloader.service.controllers.model.ResponseErrorDetail;
import m2t.jobloader.service.controllers.model.SheetServiceResponse;
import m2t.jobloader.service.controllers.model.WebAPIResponse;
import m2t.service.model.jobloader.ContainerDTO;

@RestController("/webapi")
@CrossOrigin(origins = "http://localhost:3000")
public class WebAPI {

	@Autowired
	ContainerRepository containerRepository;
	@Autowired
	JobRepository jobRepository;
	@Autowired
	SheetController sheetController;
	@Autowired
	WebSchedulerController webSchedulerController;
	
	@RequestMapping(path = "webapi/containers", method = RequestMethod.GET)
	WebAPIResponse<ContainerDTO> getcontainers(@RequestParam(name = "pageNumber", defaultValue = "0"	)int pageNumber, @RequestParam(name="pageSize", required = true)int pageSize ) {
		WebAPIResponse<ContainerDTO> response = new WebAPIResponse<ContainerDTO>("getcontainers");
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<Container> pageResponse = containerRepository.findAll(pageRequest);
		response.setTotalPages(pageResponse.getTotalPages());
		response.setPageSize(pageResponse.getSize());
		response.setCurrentPage(pageResponse.getNumber());
		List<ContainerDTO> entities = pageResponse.getContent().stream().sorted(Comparator.comparing(Container::getId).reversed()).map(dao -> {
			ContainerDTO dto = translateContainerDaoToDTO(dao);
			
			return dto;
		}).collect(Collectors.toList());
		response.setEntities(entities);
		return response;
	}
	private ContainerDTO translateContainerDaoToDTO(Container dao) {
		ContainerDTO dto = new ContainerDTO();
		dto.setContainerId(dao.getId());
		String containerNumber = dao.getContainerNumber();
		dto.setContainerNumber(containerNumber);
		dto.setReportSheetId(dao.getReportSheetId());
		dto.setReportSheetURL(dao.getReportFullURL());
		dto.setSpreadSheetId(dao.getSheetId());
		dto.setSpreadSheetURL(dao.getFullURL());
		dto.setNumberOfJobs(jobRepository.countByContainerNumber(containerNumber));
		dto.setNumberOfConfirmedDockets(jobRepository.countConfirmedJobs(containerNumber));
		dto.setNumberOfOriginalDockets(jobRepository.countOriginalJobs(containerNumber));
		dto.setTotalNumberOfBlinds(jobRepository.countTotalBlinds(containerNumber));
		dto.setTotalNumberOfPanels(jobRepository.countTotalPanels(containerNumber));
		dto.setTotalNumberOfBoxes(jobRepository.countTotalBoxes(containerNumber));
		dto.setTotalNumberOfFrames(jobRepository.countTotalFrames(containerNumber));
		return dto;
	}
	@RequestMapping(path="webapi/containers/{containerNumber}", method = RequestMethod.GET)
	WebAPIResponse<ContainerDTO> getContainer(@PathVariable(name = "containerNumber", required = true)String containerNumber){
		WebAPIResponse<ContainerDTO> response = new WebAPIResponse<ContainerDTO>("getContainer");
		Container dao = containerRepository.findByContainerNumber(containerNumber);
		if(null == dao) {
			response.setError(true);
			response.setErrorDescription("The container number " + containerNumber + " is not in the database");
			return response;
		}
		ContainerDTO dto = translateContainerDaoToDTO(dao);
		response.setEntity(dto);
		return response;
	}
	
	@RequestMapping(path = "webapi/containers/{containerNumber}/reset")
	public WebAPIResponse<ContainerDTO> resetContainer(@PathVariable(name = "containerNumber", required = true)String containerNumber){
		WebAPIResponse<ContainerDTO> response = new WebAPIResponse<ContainerDTO>("reset container " + containerNumber);
		Container dao = containerRepository.findByContainerNumber(containerNumber);
		if(dao == null) {
			response.setFound(0);
			response.setError(true);
			response.setErrorDescription("The container " + containerNumber + " does not exist");
			return response;
		}
		response.setFound(1);
		
		try {
			dao.getJobs().stream().forEach(job ->{
				job.setDeliverToCode(null);
				job.setDeliverTo(null);
				jobRepository.save(job);
			});
			dao.setFullURL(null);
			dao.setSheetId(null);
			dao.setReportFullURL(null);
			dao.setReportSheetId(null);
			containerRepository.save(dao);
		} catch (Exception e) {
			response.setError(true);
			String errorDescription = "Error while trying to saving a refreshed container with jobs";
			response.addWarning(new ResponseErrorDetail("ERROR", errorDescription,dao, e));
			response.setErrorDescription(errorDescription);
			return response;
		}
		
		BasicServiceResponse createSheetResponse = sheetController.createSheet(containerNumber);
		response.addOperationResponse(createSheetResponse);
		if(createSheetResponse.isError()) {
			response.setError(true);
			response.setErrorDescription("Error while trying to recreate the spreadsheet: " + createSheetResponse.getErrorDescription());
		}
		
		return response;
	}
	
	@RequestMapping(path = "webapi/containers/{containerNumber}/update")
	public SheetServiceResponse updateSheet(@PathVariable(name = "containerNumber") String containerNumber) {
		return sheetController.updateSheet(containerNumber);
	}
	 
	@RequestMapping(path = "webapi/containers/{containerNumber}/report")
	public PrintReportResponse generateReport(@PathVariable(name = "containerNumber") String containerNumber) {
		return webSchedulerController.printReport(containerNumber, false);
	}
	
	@RequestMapping(path = "webapi/containers/{containerNumber}/report/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable String containerNumber, HttpServletRequest request) throws Exception, GeneralSecurityException {
		return webSchedulerController.downloadFile(containerNumber, request);
	}
	
	
	
}
