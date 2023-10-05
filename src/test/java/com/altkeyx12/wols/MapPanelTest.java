package com.altkeyx12.wols;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.altkey12.wols.io.ResourceLoader;

public class MapPanelTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMapPanel() throws MalformedURLException {
		Object o = null;
		
		URL myURL = new URL("http://tile.openstreetmap.org/1/1/1.png");

		try {
			HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();

			myURLConnection.setRequestMethod("GET");

//			myURLConnection.addRequestProperty("Accept",
//					"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//			myURLConnection.addRequestProperty("Accept-Encoding", "gzip, deflate");
//			myURLConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.9");
//			myURLConnection.addRequestProperty("Cache-Control", "max-age=0");
//			myURLConnection.addRequestProperty("Connection", "keep-alive");
//			myURLConnection.addRequestProperty("Host", "tile.openstreetmap.org");
//			myURLConnection.addRequestProperty("If-None-Match", "e5ce7f673fffd09668a4ab99f0074862");
//			myURLConnection.addRequestProperty("Upgrade-Insecure-Requests", "1");
			myURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Iron Safari/537.36");

			myURLConnection.setUseCaches(false);
			myURLConnection.setDoInput(true);
			myURLConnection.setDoOutput(false);
			
			o = myURLConnection.getContent();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println(o);
		assertNotNull(o);
	}
	
	@Test
	public void testConvertPostCodeToLatLon() {
		List<String> lines = ResourceLoader.readAllLines("wsg-hotspot-list-published.txt");
		Pattern p = Pattern.compile("^[0-9]{5,6}$");
		
		Set<Integer> postal = new TreeSet<Integer>();
		int found = 0;
		for(String line: lines) {
			if(p.matcher(line).find()) {
//				System.out.println(line);
				found++;
				postal.add(Integer.parseInt(line));
			}
		};
		System.out.println(postal.size());
		
		try (FileWriter fw = new FileWriter(new File("resources/hotspots.csv"))){
			
			Iterator<Integer> iterator = postal.iterator();
			while(iterator.hasNext()) {
				int postalCode = iterator.next();
				
				URL url = new URL(
						String.format("https://developers.onemap.sg/commonapi/search?searchVal=%s&returnGeom=Y&getAddrDetails=N&pageNum=1", 
								postalCode));
				System.out.println(postalCode);
				BufferedInputStream in = new BufferedInputStream(url.openStream());
				StringBuilder sb = new StringBuilder();
				int b;
				while((b = in.read()) != -1) {
					sb.append((char) b);
				}
				
				JSONObject jo = new JSONObject(sb.toString());
				JSONArray ja = jo.getJSONArray("results");
				for(int r=0; r < ja.length(); r++) {
					JSONObject result = ja.getJSONObject(r);
					double latitude = result.getDouble("LATITUDE");
					double longitude = result.getDouble("LONGITUDE");
					fw.write(String.format("%06d,%f,%f\n", postalCode, latitude, longitude));
				}
				Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(5962, found);
	}

	@Test
	public void testRemoveDuplicate() {
		Set<String> filtered = new TreeSet<String>();
		try (FileReader fr = new FileReader(new File("resources/hotspots.csv"))){
			int read;
			StringBuilder sb = new StringBuilder();
			while((read = fr.read()) != -1){
				char c = (char) read;
				if(c == '\n') {
					String s = sb.toString();
					filtered.add(s);
					sb = new StringBuilder();
				}
				else {
					sb.append(c);
				}
			}
			if(sb.length() > 0) {
				String s = sb.toString();
				filtered.add(s);
			}
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		filtered.forEach(s -> System.out.println(s));
//		System.out.println(filtered.size());
	}
	
	public static class Coordinate extends Point2D.Double implements Comparable<Coordinate> {

		private static final long serialVersionUID = 1L;

		public Coordinate(double latitude, double longitude) {
			super(longitude, latitude);
		}

		@Override
		public int compareTo(Coordinate o) {
			if(this.x == o.x && this.y == o.y) {
				return 0;
			}
			else
				return -1;
		}
		
	}
	
	@Test
	public void testCsvToJson() {
		Set<Coordinate> hotspots = new TreeSet<>();
		
		ResourceLoader.readAllLines("hotspots.csv").forEach(l -> {
        	String[] tokens = l.split(",");
        	double latitude = Double.parseDouble(tokens[1]);
        	double longitude = Double.parseDouble(tokens[2]);
        	hotspots.add(new Coordinate(latitude,longitude));
        });
		
		try(FileWriter fw = new FileWriter("resources/hotspots.json")){
			fw.write("[\n");
			Iterator<Coordinate> i = hotspots.iterator();
			while(i.hasNext()) {
				Point2D.Double hotspot = i.next();
				fw.write("  {\n");
				fw.write(
						String.format("    \"latitude\" : %f,\n    \"longitude\" : %f\n", 
								hotspot.y, hotspot.x));
				fw.write("  },\n");
			}
			fw.write("]\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(hotspots.size() > 0);
	}
}
