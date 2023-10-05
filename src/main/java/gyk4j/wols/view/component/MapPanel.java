package gyk4j.wols.view.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.DefaultWaypointRenderer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import gyk4j.wols.ApplicationContext;
import gyk4j.wols.io.ResourceLoader;

public class MapPanel extends JXMapViewer {
	
	public static final int ZOOM = 8;
	private static final long serialVersionUID = 1L;
	protected static final int THREAD_POOL_SIZE = 8;
	protected static final GeoPosition SINGAPORE = new GeoPosition(1.343319, 103.830717);

	public MapPanel(Point2D.Double[] hotspots) {
		super();

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);

		// Setup local file cache
		File cacheDir = new File(ApplicationContext.APPDATA_DIR);
		tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(THREAD_POOL_SIZE);

        // Set the focus
        setZoom(ZOOM);
        setAddressLocation(SINGAPORE);
        
        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(this);
        addMouseListener(mia);
        addMouseMotionListener(mia);

        addMouseListener(new CenterMapListener(this));

        addMouseWheelListener(new ZoomMouseWheelListenerCursor(this));

        addKeyListener(new PanKeyListener(this));

        // Add a selection painter
        SelectionAdapter sa = new SelectionAdapter(this);
        SelectionPainter sp = new SelectionPainter(sa);
        addMouseListener(sa);
        addMouseMotionListener(sa);
        setOverlayPainter(sp);
        /*
        addPropertyChangeListener("zoom", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
//                updateWindowTitle(frame, this);
            	System.out.println(evt.getPropertyName() + ":" + evt.getNewValue());
            }
        });

        addPropertyChangeListener("center", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
//                updateWindowTitle(frame, this);
            	System.out.println(evt.getPropertyName() + ":" + evt.getNewValue());
            }
        });
        */
        
		// Create waypoints from the geo-positions
//        Arrays.asList(
//				new DefaultWaypoint(new GeoPosition(1.3703, 103.8861)),
//				new DefaultWaypoint(new GeoPosition(1.3652, 103.8870)),
//				new DefaultWaypoint(new GeoPosition(1.3726, 103.8937))
//		)
        
        
		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
		Set<Waypoint> waypoints = new HashSet<Waypoint>();
		
		for(int i=0; i < hotspots.length; i++) {
			double latitude = hotspots[i].y;
			double longitude = hotspots[i].x;
			GeoPosition gp = new GeoPosition(latitude, longitude);
			Waypoint wp = new DefaultWaypoint(gp);
			waypoints.add(wp);
		}
		
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new CustomWaypointRenderer());

		setOverlayPainter(waypointPainter);
        
	}
	
	/**
	 * Creates a selection rectangle based on mouse input
	 * Also triggers repaint events in the viewer
	 * @author Martin Steiger
	 */
	public class SelectionAdapter extends MouseAdapter 
	{
	    private boolean dragging;
	    private JXMapViewer viewer;

	    private Point2D startPos = new Point2D.Double();
	    private Point2D endPos = new Point2D.Double();

	    /**
	     * @param viewer the jxmapviewer
	     */
	    public SelectionAdapter(JXMapViewer viewer)
	    {
	        this.viewer = viewer;
	    }

	    @Override
	    public void mousePressed(MouseEvent e)
	    {
	        if (e.getButton() != MouseEvent.BUTTON3)
	            return;

	        startPos.setLocation(e.getX(), e.getY());
	        endPos.setLocation(e.getX(), e.getY());

	        dragging = true;
	    }

	    @Override
	    public void mouseDragged(MouseEvent e)
	    {
	        if (!dragging)
	            return;

	        endPos.setLocation(e.getX(), e.getY());

	        viewer.repaint();
	    }

	    @Override
	    public void mouseReleased(MouseEvent e)
	    {
	        if (!dragging)
	            return;

	        if (e.getButton() != MouseEvent.BUTTON3)
	            return;

	        viewer.repaint();

	        dragging = false;
	    }

	    /**
	     * @return the selection rectangle
	     */
	    public Rectangle getRectangle()
	    {
	        if (dragging)
	        {
	            int x1 = (int) Math.min(startPos.getX(), endPos.getX());
	            int y1 = (int) Math.min(startPos.getY(), endPos.getY());
	            int x2 = (int) Math.max(startPos.getX(), endPos.getX());
	            int y2 = (int) Math.max(startPos.getY(), endPos.getY());

	            return new Rectangle(x1, y1, x2-x1, y2-y1);
	        }

	        return null;
	    }

	}
	
	/**
	 * Paints a selection rectangle
	 * @author Martin Steiger
	 */
	public class SelectionPainter implements Painter<Object>
	{
	    private Color fillColor = new Color(128, 192, 255, 128);
	    private Color frameColor = new Color(0, 0, 255, 128);

	    private SelectionAdapter adapter;

	    /**
	     * @param adapter the selection adapter
	     */
	    public SelectionPainter(SelectionAdapter adapter)
	    {
	        this.adapter = adapter;
	    }

	    @Override
	    public void paint(Graphics2D g, Object t, int width, int height)
	    {
	        Rectangle rc = adapter.getRectangle();

	        if (rc != null)
	        {
	            g.setColor(frameColor);
	            g.draw(rc);
	            g.setColor(fillColor);
	            g.fill(rc);
	        }
	    }
	}
	
	public static class CustomWaypointRenderer extends DefaultWaypointRenderer {
		private static final Log log = LogFactory.getLog(CustomWaypointRenderer.class);
		
		private BufferedImage hotspot = null;

		public CustomWaypointRenderer() {
			try {
				hotspot = ImageIO.read(ResourceLoader.getFileFromResource("hotspot.png"));
			} catch (Exception ex) {
				log.warn("couldn't read hotspot.png", ex);
			}
		}
		
		@Override
	    public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint w)
	    {
	        if (hotspot == null)
	            return;

	        Point2D point = map.getTileFactory().geoToPixel(w.getPosition(), map.getZoom());
	        
	        int x = (int)point.getX() -hotspot.getWidth() / 2;
	        int y = (int)point.getY() -hotspot.getHeight();
	        
	        g.drawImage(hotspot, x, y, null);
	    }
	}
}
