package net.altkey12.wols.net;

import net.altkey12.wols.util.platform.dummy.NetworkDiagnosticsDummy;
import net.altkey12.wols.util.platform.linux.NetworkDiagnosticsLinux;
import net.altkey12.wols.util.platform.windows.NetworkDiagnosticsWindows;

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
