package gyk4j.wols.beans.speedtest;

public class GeneralPingTests {
	protected int pingTest; // in ms

	public GeneralPingTests(int pingTest) {
		super();
		this.pingTest = pingTest;
	}

	public int getPingTest() {
		return pingTest;
	}

	public void setPingTest(int pingTest) {
		this.pingTest = pingTest;
	}
}
