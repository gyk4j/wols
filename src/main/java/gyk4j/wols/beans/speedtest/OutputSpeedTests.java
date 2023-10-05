package gyk4j.wols.beans.speedtest;

public class OutputSpeedTests {
	protected float downloadSpeedTest; // in Mbps

	public OutputSpeedTests(float downloadSpeedTest) {
		super();
		this.downloadSpeedTest = downloadSpeedTest;
	}

	public float getDownloadSpeedTest() {
		return downloadSpeedTest;
	}

	public void setDownloadSpeedTest(float downloadSpeedTest) {
		this.downloadSpeedTest = downloadSpeedTest;
	}
}
