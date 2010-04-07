package edu.illinois.mdpnp.mock.server;

import java.util.HashMap;
import java.util.Map;

public class PCA extends Device {
	public PCA(final String id, final int port, final String[] attributes) {
		this.id = id;
		this.port = port;
		this.attributes = attributes;
	}
	
	public String getReadings() {
		return "{\"dose\": 1}";
	}
	
	public Map<String, Integer> getConditions() {
		Map<String, Integer> conditions = new HashMap<String, Integer>();
		conditions.put("lessThan", 10);
		return conditions;
	}
	
	public static void main(String[] args) {
		PCA pca = new PCA("pca", 2345, new String[]{"dose"});
		pca.startDevice();
	}
}
