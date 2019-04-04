package m2t.jobloader.service.controllers.model;

import java.util.ArrayList;
import java.util.List;

public class WebAPIResponse<EntityType> extends BasicServiceResponse{

	protected List<EntityType> entities;
	protected EntityType entity;
	protected int totalPages;
	protected int currentPage;
	protected int pageSize;
	
	public WebAPIResponse(String operationName) {
		super(operationName);
		entities = new ArrayList<>();
	}

	public List<EntityType> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityType> entities) {
		this.entities = entities;
	}

	public EntityType getEntity() {
		return entity;
	}

	public void setEntity(EntityType entity) {
		this.entity = entity;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
}
