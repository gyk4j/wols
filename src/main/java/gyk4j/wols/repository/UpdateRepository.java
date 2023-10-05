package gyk4j.wols.repository;

import gyk4j.wols.beans.updatecontent.Update;

public class UpdateRepository {
	public Update get() {
//		String json = Update.download();
//
//		Update newUpdate;
//		
//		if(json == null) { // Download failed?
//			newUpdate = Update.getInstance();
//		}
//		else { // Try to parse
//			newUpdate = Update.parse(json);
//		}

//		return newUpdate;
		return Update.getInstance();
	}
}
