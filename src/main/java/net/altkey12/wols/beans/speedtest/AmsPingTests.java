package net.altkey12.wols.beans.speedtest;

public class AmsPingTests {
	protected int m1 = -1;
	protected int singtel = -1;
	protected int starhub = -1;
	protected int simba = -1;
	
	/**
	 * 
	 * @param m1
	 * @param singtel
	 * @param starhub
	 * @param simba
	 */
	public AmsPingTests(int m1, int singtel, int starhub, int simba) {
		super();
		this.m1 = m1;
		this.singtel = singtel;
		this.starhub = starhub;
		this.simba = simba;
	}

	public int getM1() {
		return m1;
	}

	public void setM1(int m1) {
		this.m1 = m1;
	}

	public int getSingtel() {
		return singtel;
	}

	public void setSingtel(int singtel) {
		this.singtel = singtel;
	}

	public int getStarhub() {
		return starhub;
	}

	public void setStarhub(int starhub) {
		this.starhub = starhub;
	}

	public int getSimba() {
		return simba;
	}

	public void setSimba(int simba) {
		this.simba = simba;
	}
}
