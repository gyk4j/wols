package gyk4j.wols.net;

import gyk4j.wols.util.platform.dummy.NetworkDiagnosticsDummy;
import gyk4j.wols.util.platform.linux.NetworkDiagnosticsLinux;
import gyk4j.wols.util.platform.windows.NetworkDiagnosticsWindows;

public class NetworkDiagnosticsFactory {
	public static NetworkDiagnostics getInstance() {
		String os = System.getProperty("os.name");
		
		os = (os == null)? "": os.toLowerCase();
		
		if (os.contains("linux")) {
			return new NetworkDiagnosticsLinux();
		} 
		else if (os.contains("windows")) {
			return new NetworkDiagnosticsWindows();
		}
		else {
			return new NetworkDiagnosticsDummy();
		}
	}
}
