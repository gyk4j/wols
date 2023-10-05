package net.altkey12.wols;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationContext {
	public static final String APPDATA_DIR = System.getProperty("user.home") + File.separator + ".wols";
	
	protected static Path appDataDir;
	
	public static Path getAppDataDir() {
		if(appDataDir == null) {
			appDataDir = Paths.get(APPDATA_DIR);
			if(!Files.exists(appDataDir) || 
					!Files.isDirectory(appDataDir) || 
					!Files.isReadable(appDataDir)|| 
					!Files.isWritable(appDataDir)) {
				try {
					Path created = Files.createDirectories(appDataDir);
					
					if(!created.equals(appDataDir)) {
						System.err.println("Failed to create " + appDataDir.toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return appDataDir;
	}
	
	public static Path getCacheDir() {
		return getAppDataDir();
	}
	
	public static Path getAppDir() {
		return getAppDataDir();
	}
}
