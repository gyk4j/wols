package gyk4j.wols.beans.updatecontent;

import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.ApplicationContext;
import gyk4j.wols.beans.contactus.Provider;
import gyk4j.wols.io.ResourceLoader;

public class Update implements Comparable<Update> {
	public static final Logger LOGGER = LoggerFactory.getLogger(Update.class);
	
	private static final String JSON_KEY_FAQS = "faqs";
	
	private static final String JSON_KEY_DATE = "date";
	private static final String JSON_KEY_VERSION = "version";
	
	private static final String JSON_KEY_NEWS = "news";
	private static final String JSON_NEWS_IMAGE = "image";
	private static final String JSON_NEWS_DATE = "date";
	private static final String JSON_NEWS_TITLE = "title";
	private static final String JSON_NEWS_CONTENT = "content";

	private static final String JSON_KEY_HOTSPOTS = "hotspots";
	private static final String JSON_HOTSPOTS_LATITUDE = "latitude";
	private static final String JSON_HOTSPOTS_LONGITUDE = "longitude";
	
	private static final String JSON_KEY_CONTACTUS = "contact_us";

	private static final String UPDATE_JSON_DIR = ApplicationContext.APPDATA_DIR;
	private static final String UPDATE_JSON = "update.json";
	private static final String UPDATE_JSON_FILE = UPDATE_JSON_DIR + File.separator + UPDATE_JSON;
	private static final String UPDATE_URL = "http://localhost/update.json";

	protected static Update instance = null;
	
	protected String version;
	protected LocalDate date;
	protected Point2D.Double[] hotspots;
	protected News[] news;
	protected FaqsSection[] faqsSections;
	protected Provider[] contactUs;
	
	public static Update getInstance() {
		if(instance != null) {
			LOGGER.info("Returning from memory.");
			return instance;
		}
		
		// Read from cached copy on disk.
		String json = null;
		Path updatePath = Paths.get(UPDATE_JSON_FILE);
		if (Files.exists(updatePath) && Files.isRegularFile(updatePath) && Files.isReadable(updatePath)) {
			LOGGER.info("Loading from cache.");
			try {
				json = Files.readAllLines(updatePath).stream().collect(Collectors.joining());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Download latest copy from Internet.
		if (json == null) {
			LOGGER.info("Downloading from Internet");
			json = download();
		}

		// Read from default bundled update.json for outdated data.
		if (json == null) {
			LOGGER.info("Reading from resource");
			json = ResourceLoader.readAsString(UPDATE_JSON);
		}
			
		instance = parse(json);
		return instance;		
	}
	
	public static Update parse(String json) {
		JSONObject jo = new JSONObject(json);
		
		Update u = new Update();
		u.loadMetadata(jo);
		u.loadHotspots(jo.getJSONArray(JSON_KEY_HOTSPOTS));
		u.loadNews(jo.getJSONArray(JSON_KEY_NEWS));
		u.loadFaqs(jo.getJSONArray(JSON_KEY_FAQS));
		u.loadContactUs(jo.getJSONArray(JSON_KEY_CONTACTUS));
		
		return u;
	}
	
	public static String download() {
		String json = null;
		
		try {
			URL url = new URL(UPDATE_URL);
			InputStream is = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			int buffer;
			
			StringBuilder sb = new StringBuilder();
			while((buffer = bis.read()) != -1) {
				sb.append((char) buffer);
			}
			
			
			Path jsonDir = Paths.get(UPDATE_JSON_DIR);
			if(!Files.exists(jsonDir) || !Files.isDirectory(jsonDir)) {
				Files.createDirectories(jsonDir);
			}
			
			FileWriter writer = new FileWriter(UPDATE_JSON_FILE);
			json = sb.toString();
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
		}
		
		return json;
	}
	
	protected Update() {
		super();
	}
	
	private void loadMetadata(JSONObject jo) {
		date = LocalDate.parse(jo.getString(JSON_KEY_DATE));
		version = jo.getString(JSON_KEY_VERSION);
	}
	
	private void loadHotspots(JSONArray jas) {
		hotspots = new Point2D.Double[jas.length()];
        
		for(int i = 0; i < jas.length(); i++) {
			JSONObject hjs = jas.getJSONObject(i);
			
			hotspots[i] = new Point2D.Double();
			hotspots[i].x = hjs.getDouble(JSON_HOTSPOTS_LONGITUDE);
			hotspots[i].y = hjs.getDouble(JSON_HOTSPOTS_LATITUDE);
		}
	}
	
	private void loadNews(JSONArray jas) {
		news = new News[jas.length()];
		
		for(int i = 0; i < jas.length(); i++) {
			JSONObject njs = jas.getJSONObject(i);
			
			String image = njs.getString(JSON_NEWS_IMAGE);
			LocalDate date = LocalDate.parse(njs.getString(JSON_NEWS_DATE));
			String title = njs.getString(JSON_NEWS_TITLE);
			String content = njs.getString(JSON_NEWS_CONTENT);
			
			news[i] = new News(image, date, title, content);
		}
	}
	
	private void loadFaqs(JSONArray jas) {		
		faqsSections = new FaqsSection[jas.length()];
		
		for(int s = 0; s < jas.length(); s++) {
			JSONObject sjs = jas.getJSONObject(s);
			String name = sjs.getString("section");
			JSONArray qja = sjs.getJSONArray("qs");
			
			QnA[] qs = new QnA[qja.length()];
			for(int q = 0; q < qja.length(); q++) {
				JSONObject qnajo = qja.getJSONObject(q);
				String question = qnajo.getString("q");
				String answer = qnajo.getString("a");
				
				QnA qna = new QnA(question, answer);
				qs[q] = qna;
			}
			
			FaqsSection section = new FaqsSection(name, qs);
			faqsSections[s] = section;
		}
	}
	
	private void loadContactUs(JSONArray jas) {
		contactUs = new Provider[jas.length()];
		
		for(int p = 0; p < jas.length(); p++) {
			JSONObject pjs = jas.getJSONObject(p);
			
			String name = pjs.getString("name");
			String contactNo = pjs.getString("contact_no");
			String email = pjs.getString("email");
			String website = pjs.getString("website");
			String logo = pjs.getString("logo");
			
			contactUs[p] = new Provider(name, contactNo, email, website, logo);
		}
	}
	
	public String getVersion() {
		return version;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public News[] getNews() {
		return news;
	}
	
	public FaqsSection[] getFaqsSections() {
		return faqsSections;
	}

	public Point2D.Double[] getHotspots() {
		return hotspots;
	}
	
	public Provider[] getContactUs() {
		return contactUs;
	}

	@Override
	public boolean equals(Object obj) {
		boolean same = false;
		
		if(obj instanceof Update) {
			Update update = (Update) obj;
			same = version.equals(update.version) && date.equals(update.date);
		}
		
		return same;
	}

	@Override
	public int compareTo(Update o) {
		return version.compareTo(o.version);
	}
}
