package net.altkey12.wols.model;

import java.awt.Component;

public class Binding {
	protected Component component;
	protected Object model;
	
	public Binding(Component component, Object model) {
		super();
		this.component = component;
		this.model = model;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}	
}
