package net.altkey12.wols.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);
	public static final String NEWS_PLACEHOLDER_THUMBNAIL = "news_placeholder_icon.png";
	static final int BUFFER = 4096;
	
	public static ImageIcon getImageIcon(String name) {
		ImageIcon imageIcon = null;
		URL f = ResourceLoader.getFileFromResource(name);
		imageIcon = new ImageIcon(f);

		return imageIcon;
	}
	
	public static Image getImage(String name, String placeholder) {
		BufferedImage  image = null;
		try {
			URL f = ResourceLoader.getFileFromResource(name);
			image = ImageIO.read(f);
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			URL f = ResourceLoader.getFileFromResource(placeholder);
			try {
				image = ImageIO.read(f);
			} catch (IOException e1) {
				LOGGER.error(e.getMessage());
			}
			
		}
		return image;
	}
	
	public static List<String> readAllLines(String name) {
		List<String> lines = new ArrayList<>();
		InputStream is = ResourceLoader.getFileFromResourceAsStream(name);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String line;
		try {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public static String readAsString(String name) {
		return readAllLines(name).stream().collect(Collectors.joining());
	}
	
	public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

	public static URL getFileFromResource(String fileName) {

        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            return resource;
        }

    }
}
