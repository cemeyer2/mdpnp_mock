package edu.illinois.mdpnp.mock.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SPO2 extends Device {
	public SPO2(final String id, final int port, final String[] attributes) {
		this.id = id;
		this.port = port;
		this.attributes = attributes;
	}
	
	public String getReadings() {
		Random random = new Random();
		return "{\"oxygenSat\": "+ (int) (((100 - 80 + 1) * random.nextDouble()) + 80) + "}";
	}
	
	public Map<String, Integer> getConditions() {
		Map<String, Integer> conditions = new HashMap<String, Integer>();
		conditions.put("lessThan", 100);
		conditions.put("greaterThan", 85);
		return conditions;
	}
	
	public static void main(String[] args) {
		SPO2 spo2 = new SPO2("spo2", 1234, new String[]{"oxygenSat"});
		spo2.startDevice();
	}
}
