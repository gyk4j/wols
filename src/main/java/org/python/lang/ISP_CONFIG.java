package org.python.lang;

public enum ISP_CONFIG {
	m1,
	singtel{

		@Override
		public String essaUrl() {
			return "https://singtel-wsg.singtel.com/essa_r12";
		}

		@Override
		public String createApiVersions(int i) {
			return new String[] {"2.6", "2.8"}[i];
		}

		@Override
		public String retrieveApiVersions(int i) {
			return new String[] {"2.0", "2.6"}[i];
		}
		
	},	
	starhub{
		
		@Override
		public String essaUrl() {
			return "https://api.wifi.starhub.net.sg/essa_r12";
		}
		
		@Override
		public String apiPassword() {
			return "5t4rHUB4p1";
		}

		@Override
		public String createApiVersions(int i) {
			return new String[] {"2.6", "2.8"}[i];
		}

		@Override
		public String retrieveApiVersions(int i) {
			return new String[] {"2.0", "2.6"}[i];
		}
		
	},
	simba,
	/*
	myrepublic{
		
		@Override
		public String essaUrl() {
			return "https://wireless-sg-app.myrepublic.net/essa_r12";
		}

		@Override
		public String createApiVersions(int i) {
			return new String[]{"2.3", "2.4"}[i];
		}

		@Override
		public String retrieveApiVersions(int i) {
			return new String[]{"1.6", "2.2"}[i];
		}
	}
	*/
	;
	
	public String essaUrl() {
		return "";
	}
	
	public String apiPassword() {
		return "";
	}
	
	public String createApiVersions(int i) {
		return "0.0";
	}
	
	public String retrieveApiVersions(int i) {
		return "0.0";
	}
}

