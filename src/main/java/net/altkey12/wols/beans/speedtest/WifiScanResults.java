package net.altkey12.wols.beans.speedtest;

public class WifiScanResults {
	protected WifiScan[] results;

	/**
	 * 
	 * @param results
	 */
	public WifiScanResults(WifiScan[] results) {
		super();
		this.results = results;
	}

	public WifiScan[] getResults() {
		return results;
	}

	public void setResults(WifiScan[] results) {
		this.results = results;
	}
	
}
