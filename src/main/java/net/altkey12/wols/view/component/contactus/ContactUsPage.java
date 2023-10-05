package net.altkey12.wols.view.component.contactus;

import net.altkey12.wols.beans.contactus.Provider;
import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.view.component.ScrollPage;

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
