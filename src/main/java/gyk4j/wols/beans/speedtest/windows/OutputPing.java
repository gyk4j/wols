package gyk4j.wols.beans.speedtest.windows;

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
		int time;
		int ttl;
		
		/**
		 * 
		 * @param bytes
		 * @param time
		 * @param ttl
		 */
		public Reply(int bytes, int time, int ttl) {
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

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
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
		int minimum;
		int maximum;
		int average;
		
		/**
		 * 
		 * @param minimum
		 * @param maximum
		 * @param average
		 */
		public ApproximateRoundTripTimes(int minimum, int maximum, int average) {
			super();
			this.minimum = minimum;
			this.maximum = maximum;
			this.average = average;
		}

		public int getMinimum() {
			return minimum;
		}

		public void setMinimum(int minimum) {
			this.minimum = minimum;
		}

		public int getMaximum() {
			return maximum;
		}

		public void setMaximum(int maximum) {
			this.maximum = maximum;
		}

		public int getAverage() {
			return average;
		}

		public void setAverage(int average) {
			this.average = average;
		}
	}
}
