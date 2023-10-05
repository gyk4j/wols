package gyk4j.wols.beans.speedtest;

public class SpeedTests {
	protected float downloadSpeedTest; // in Mbps

	public SpeedTests(float downloadSpeedTest) {
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
