package edu.illinois.mdpnp.mock.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jcouchdb.db.Database;

import edu.illinois.mdpnp.mock.models.CDBChanges;
import edu.illinois.mdpnp.mock.models.DeviceData;
import edu.illinois.mdpnp.mock.models.DeviceData.ConditionBuilder;

public class Controller {
    final String serverIPname = "localhost"; // server IP name
	private List<Integer> devicePorts = new ArrayList<Integer>();
	private Map<Integer, DeviceData> devices = new HashMap<Integer, DeviceData>();
	private ObjectMapper mapper = new ObjectMapper();

	public void sendMessage(Socket sock, String message) {
    	PrintWriter pw = null; // socket output to server
    	BufferedReader br = null; // socket input from server
    	try {
    		pw = new PrintWriter(sock.getOutputStream(), true); // create reader and writer
    		br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    		pw.println(message); // send msg to the server
    		
    		String readings = br.readLine(); // get data from the server
    		
    		Map<String, Object> reading = mapper.readValue(readings, Map.class);
    		String[] deviceAttributes = devices.get(sock.getPort()).getAttributes();
    		for (String key : deviceAttributes) {
    			System.out.println("VALUE: " +key + ": " + reading.get(key));
    			ConditionBuilder cb = devices.get(sock.getPort()).getConditions();
    			cb.setSource(reading.get(key).hashCode());
    			System.out.println("CONDITION: " + cb.getCondition().evaluate());
    		}

    		pw.close(); 
    		br.close();
    	} catch (Throwable e) {
    		System.out.println("Error: " + e.getMessage());
    		e.printStackTrace();
    	}
    }
    
    private void listenForNewDevices() {
    	new Thread(new Runnable() {
    		public void run() {
		    	try {
		    		//Database db = new Database("localhost", "mdpnp");
		    		//ViewAndDocumentsResult<Object,BaseDocument> docs = db.query("_changes?feed=continuous&since=34", Object.class, BaseDocument.class, null, null, null);
		    		
		    		//String data = URLEncoder.encode("feed", "UTF-8") + "=" + URLEncoder.encode("continuous", "UTF-8");
		    		//data += URLEncoder.encode("since", "UTF-8") + "=" + URLEncoder.encode("34", "UTF-8");
		    		
		    		Database db = new Database("localhost", "mdpnp");
		    		
		    		URL url = new URL("http://localhost:5984/mdpnp/_changes?feed=continuous"); //&since=34
		    		URLConnection conn = url.openConnection();
		    		conn.setDoOutput(true);
		    		
		    		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    		String line;
		    		while((line = rd.readLine())!= null) {
		    			System.out.println(line);
		    			
		    			// hack to get around {"last_seq": #} message which I don't care for right now.
		    			CDBChanges changes; 
		    			try {
		    				changes = mapper.readValue(line, CDBChanges.class);
		    			} catch (JsonMappingException e) {
		    				continue;
		    			}
		    			
		    			if (!changes.isDeleted()) {
		    				DeviceData deviceData = db.getDocument(DeviceData.class, changes.getId());
		    				devicePorts.add(deviceData.getPort());
		    				devices.put(deviceData.getPort(), deviceData);
		    			}
		    			//System.out.println(line);
		    		}
		    	} catch (Exception e) {
		    		System.out.println("Error " + e.getMessage());
		    		e.printStackTrace();
		    	}
    		}
    	}).start();
    }
    
    public void start() {
    	Socket sock;
    	try {
	    	while (true) {
	    		for(int port : devicePorts) {
	    			sock = new Socket(serverIPname, port);
	    			sendMessage(sock, "ping");
	    		}
	    		Thread.sleep(5000);
	    	}
    	} catch (Exception e) {
    		System.out.println("Error " + e.getMessage());
    		e.printStackTrace();
    	}
    }
    public static void main(String args[]) {
    	Controller controller = new Controller();
    	controller.listenForNewDevices();
    	controller.start();
	}
}