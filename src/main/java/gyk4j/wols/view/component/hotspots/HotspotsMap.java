package gyk4j.wols.view.component.hotspots;

import java.awt.geom.Point2D;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.MapPanel;
import gyk4j.wols.view.component.TransparentPanel;

public class HotspotsMap extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
//	protected final MapPanel map = MapPanel.createMapPanel(new Point(412938, 259954), 11);
	protected final MapPanel map;

	public HotspotsMap() {
		super();
		Point2D.Double[] hotspots = Controller.getInstance().getHotspots();
		map = new MapPanel(hotspots);
		add(map);
	}

	public MapPanel getMap() {
		return map;
	}

}
