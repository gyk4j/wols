package net.altkey12.wols.beans.speedtest.linux;

public class OutputPing {
	Reply[] replies;
	PingStatistics pingStatistics;
	ApproximateRoundTripTimes approximateRoundTripTimes;
	
	/**
	 * 
	 * @param replies
	 * @param pingStatistics
	 * @param approximateRoundTripTimes
	 */
	public OutputPing(Reply[] replies, PingStatistics pingStatistics, ApproximateRoundTripTimes approximateRoundTripTimes) {
		super();
		this.replies = replies;
		this.pingStatistics = pingStatistics;
		this.approximateRoundTripTimes = approximateRoundTripTimes;
	}

	public Reply[] getReplies() {
		return replies;
	}

	public void setReplies(Reply[] replies) {
		this.replies = replies;
	}

	public PingStatistics getPingStatistics() {
		return pingStatistics;
	}

	public void setPingStatistics(PingStatistics pingStatistics) {
		this.pingStatistics = pingStatistics;
	}

	public ApproximateRoundTripTimes getApproximateRoundTripTimes() {
		return approximateRoundTripTimes;
	}

	public void setApproximateRoundTripTimes(ApproximateRoundTripTimes approximateRoundTripTimes) {
		this.approximateRoundTripTimes = approximateRoundTripTimes;
	}

	public static class Reply {
		int bytes;
		float time;
		int ttl;
		
		/**
		 * 
		 * @param bytes
		 * @param time
		 * @param ttl
		 */
		public Reply(int bytes, float time, int ttl) {
			super();
			this.bytes = bytes;
			this.time = time;
			this.ttl = ttl;
		}

		public int getBytes() {
			return bytes;
		}

		public void setBytes(int bytes) {
			this.bytes = bytes;
		}

		public float getTime() {
			return time;
		}

		public void setTime(float time) {
			this.time = time;
		}

		public int getTtl() {
			return ttl;
		}

		public void setTtl(int ttl) {
			this.ttl = ttl;
		}
	}

	public static class PingStatistics {
		int packetsSent;
		int packetsReceived;
		int packetsLost;
		
		/**
		 * 
		 * @param packetsSent
		 * @param packetsReceived
		 * @param packetsLost
		 */
		public PingStatistics(int packetsSent, int packetsReceived, int packetsLost) {
			super();
			this.packetsSent = packetsSent;
			this.packetsReceived = packetsReceived;
			this.packetsLost = packetsLost;
		}
		
		public PingStatistics(int packetsSent, int packetsReceived, float packetsLostPercent) {
			super();
			this.packetsSent = packetsSent;
			this.packetsReceived = packetsReceived;
			this.packetsLost = Math.round(packetsLostPercent * packetsSent);
		}

		public int getPacketsSent() {
			return packetsSent;
		}

		public void setPacketsSent(int packetsSent) {
			this.packetsSent = packetsSent;
		}

		public int getPacketsReceived() {
			return packetsReceived;
		}

		public void setPacketsReceived(int packetsReceived) {
			this.packetsReceived = packetsReceived;
		}

		public int getPacketsLost() {
			return packetsLost;
		}

		public void setPacketsLost(int packetsLost) {
			this.packetsLost = packetsLost;
		}
	}
	
	public static class ApproximateRoundTripTimes {
		float minimum;
		float maximum;
		float average;
		float mdev;
		
		/**
		 * 
		 * @param minimum
		 * @param maximum
		 * @param average
		 */
		public ApproximateRoundTripTimes(float minimum, float maximum, float average, float mdev) {
			super();
			this.minimum = minimum;
			this.maximum = maximum;
			this.average = average;
			this.mdev = mdev;
		}

		public float getMinimum() {
			return minimum;
		}

		public void setMinimum(float minimum) {
			this.minimum = minimum;
		}

		public float getMaximum() {
			return maximum;
		}

		public void setMaximum(float maximum) {
			this.maximum = maximum;
		}

		public float getAverage() {
			return average;
		}

		public void setAverage(float average) {
			this.average = average;
		}
		
		public float getMDev() {
			return mdev;
		}

		public void setMDev(float mdev) {
			this.mdev = mdev;
		}
	}	
}
