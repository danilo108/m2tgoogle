package m2t.jobloader.service.controllers.model;

import java.util.ArrayList;
import java.util.List;

public class WebAPIResponse<EntityType> extends BasicServiceResponse{

	protected List<EntityType> entities;
	protected EntityType entity;
	
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

	
}
