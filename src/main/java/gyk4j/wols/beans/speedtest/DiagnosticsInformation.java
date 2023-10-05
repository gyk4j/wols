package gyk4j.wols.beans.speedtest;

public class DiagnosticsInformation {
	protected String diagnosticsMode;
	protected String connectivityMode;
	
	public DiagnosticsInformation(String diagnosticsMode, String connectivityMode) {
		super();
		this.diagnosticsMode = diagnosticsMode;
		this.connectivityMode = connectivityMode;
	}

	public String getDiagnosticsMode() {
		return diagnosticsMode;
	}

	public void setDiagnosticsMode(String diagnosticsMode) {
		this.diagnosticsMode = diagnosticsMode;
	}

	public String getConnectivityMode() {
		return connectivityMode;
	}

	public void setConnectivityMode(String connectivityMode) {
		this.connectivityMode = connectivityMode;
	}
}
