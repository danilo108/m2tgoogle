package m2t.service.model.jobloader;

import java.util.ArrayList;
import java.util.List;

public class ContainerDTO {
	
	String containerNumber;
	List<DocketDTO> dockets;
	String originalFileName;
	String spreadSheetId;
	String spreadSheetURL;
	String reportSheetId;
	String reportSheetURL;
	int numberOfJobs;
	int numberOfConfirmedDockets;
	int numberOfOriginalDockets;
	int totalNumberOfBoxes;
	int totalNumberOfFrames;
	int totalNumberOfPanels;
	int totalNumberOfBlinds;
	long containerId;
	
	
	
	public ContainerDTO() {
		dockets = new ArrayList<DocketDTO>();
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public List<DocketDTO> getDockets() {
		return dockets;
	}
	public void setDockets(List<DocketDTO> dockets) {
		this.dockets = dockets;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public int getNumberOfConfirmedDockets() {
		return numberOfConfirmedDockets;
	}
	public void setNumberOfConfirmedDockets(int numberOfConfirmedDockets) {
		this.numberOfConfirmedDockets = numberOfConfirmedDockets;
	}
	public int getNumberOfOriginalDockets() {
		return numberOfOriginalDockets;
	}
	public void setNumberOfOriginalDockets(int numberOfOriginalDockets) {
		this.numberOfOriginalDockets = numberOfOriginalDockets;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getSpreadSheetId() {
		return spreadSheetId;
	}
	public void setSpreadSheetId(String spreadSheetId) {
		this.spreadSheetId = spreadSheetId;
	}
	public String getSpreadSheetURL() {
		return spreadSheetURL;
	}
	public void setSpreadSheetURL(String spreadSheetURL) {
		this.spreadSheetURL = spreadSheetURL;
	}
	public String getReportSheetId() {
		return reportSheetId;
	}
	public void setReportSheetId(String reportSheetId) {
		this.reportSheetId = reportSheetId;
	}
	public String getReportSheetURL() {
		return reportSheetURL;
	}
	public void setReportSheetURL(String reportSheetURL) {
		this.reportSheetURL = reportSheetURL;
	}
	public int getNumberOfJobs() {
		return numberOfJobs;
	}
	public void setNumberOfJobs(int numberOfJobs) {
		this.numberOfJobs = numberOfJobs;
	}
	
	public int getTotalNumberOfBoxes() {
		return totalNumberOfBoxes;
	}
	public void setTotalNumberOfBoxes(int totalNumberOfBoxes) {
		this.totalNumberOfBoxes = totalNumberOfBoxes;
	}
	public int getTotalNumberOfFrames() {
		return totalNumberOfFrames;
	}
	public void setTotalNumberOfFrames(int totalNumberOfFrames) {
		this.totalNumberOfFrames = totalNumberOfFrames;
	}
	public int getTotalNumberOfPanels() {
		return totalNumberOfPanels;
	}
	public void setTotalNumberOfPanels(int totalNumberOfPanels) {
		this.totalNumberOfPanels = totalNumberOfPanels;
	}
	public int getTotalNumberOfBlinds() {
		return totalNumberOfBlinds;
	}
	public void setTotalNumberOfBlinds(int totalNumberOfBlinds) {
		this.totalNumberOfBlinds = totalNumberOfBlinds;
	}
	public long getContainerId() {
		return containerId;
	}
	public void setContainerId(long containerId) {
		this.containerId = containerId;
	}

	
	
}
