package gyk4j.wols.beans.contactus;

public class Provider {
	
	String name;
	String contactNo;
	String email;
	String website;
	String logo;
	
	public Provider(String name, String contactNo, String email, String website, String logo) {
		super();
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		this.website = website;
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}
