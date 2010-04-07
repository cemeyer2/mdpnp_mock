package edu.illinois.mdpnp.mock.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.jcouchdb.db.Database;

/**
 * Example Server program using TCP.
 */
public class Device {
	protected String id;
	protected int port; // server port number
	protected String[] attributes;
	  
	// send data to couchdb
	protected void connect() {
		Database db = new Database("localhost", "mdpnp");
		  
		Map<String,Object> doc = new HashMap<String,Object>();
		doc.put("_id", id);
		doc.put("port", port);
		doc.put("attributes", attributes);
		doc.put("conditions", getConditions());
		db.createDocument(doc);
	}
	
	public String getReadings() {
		return "{}";
	}
	
	public Map<String, Integer> getConditions() {
		return new HashMap<String, Integer>();
	}

	protected void startDevice() {
		ServerSocket sock = null;
		PrintWriter pw= null; // socket output stream
		Socket clientSocket = null; // socket created by accept
		BufferedReader br; // socket input stream
		try {
			sock = new ServerSocket(port); // create socket and bind to port
			connect();
			while (true) {
				System.out.println(id + " waiting for controller to connect");
				clientSocket = sock.accept(); // wait for client to connect
				System.out.println("controller has connected");
				pw= new PrintWriter(clientSocket.getOutputStream(),true);
				br= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String msg = br.readLine(); // read msg from client
				System.out.println("Message from the controller: " + msg);
				pw.println(getReadings()); // send msg to client
			}
			//pw.close(); // close everything
			//br.close();
			//clientSocket.close();
			//sock.close();
		} catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}
}