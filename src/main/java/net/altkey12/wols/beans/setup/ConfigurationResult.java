package net.altkey12.wols.beans.setup;

import java.util.UUID;

public class ConfigurationResult {
	String connectionId;
	UUID connectionUuid;

	public boolean isOK() {
		return getConnectionId() != null &&
				getConnectionId().length() > 0 &&
				getConnectionUuid() != null &&
				!getConnectionUuid().equals(new UUID(0,0));
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public UUID getConnectionUuid() {
		return connectionUuid;
	}

	public void setConnectionUuid(UUID connectionUuid) {
		this.connectionUuid = connectionUuid;
	}
}
