package gyk4j.wols.beans.main.linux;

import java.util.UUID;

public class OutputNmcliConnectionAdd {
	String id;
	UUID uuid;
	
	/**
	 * 
	 * @param id
	 * @param uuid
	 */
	public OutputNmcliConnectionAdd(String id, UUID uuid) {
		super();
		this.id = id;
		this.uuid = uuid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
