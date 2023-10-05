package org.python.net;

import org.python.lang.Dict;
import org.python.lang.error.ValueError;
import org.python.modules.Json;

public class Response {
	int statusCode;
	String body;
	
	public Response(int statusCode, String body) {
		this.statusCode = statusCode;
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public Dict json() throws ValueError {
		return Dict.from(Json.loads(body));
	}
	
}
