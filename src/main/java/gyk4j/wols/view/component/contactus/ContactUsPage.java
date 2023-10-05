package gyk4j.wols.view.component.contactus;

import gyk4j.wols.beans.contactus.Provider;
import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.ScrollPage;

public class ContactUsPage extends ScrollPage {
	private static final long serialVersionUID = 1L;

	public ContactUsPage() {
		this("Contact Us");
	}
	
	public ContactUsPage(String title) {
		super(title);
		
		loadProviders();
	}
	
	private void loadProviders() {
		Provider[] providers = Controller.getInstance().getProviders();
		for(Provider p: providers) {
			addComponent(
					new ProviderPanel(
							p.getName(), 
							p.getContactNo(), 
							p.getEmail(), 
							p.getWebsite(), 
							p.getLogo()));
		}
	}

}
