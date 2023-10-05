package gyk4j.wols.beans.main;

public class OutputNetworkProfile {
	String identity;
	String password;
	
	public OutputNetworkProfile(String identity, String password) {
		super();
		this.identity = identity;
		this.password = password;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
