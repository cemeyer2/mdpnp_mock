package edu.illinois.mdpnp.mock.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import org.jcouchdb.db.Database;

public class CouchDBBroadcaster implements Runnable
{
	private static final String GROUP_ADDRESS = "230.0.0.1";
	private static final int PORT = 54321;
	
	private boolean should_broadcast;
	private DatagramSocket socket;
	private String broadcast_string;
	
	
	public CouchDBBroadcaster()
	{
		should_broadcast = true;
        try {
			socket = new DatagramSocket(PORT-1);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void calculateBroadcastString() throws SocketException
	{
		System.out.println("Broadcaster: recalculating broadcast string");
		String address = "";
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        {
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) 
            {
            	try
            	{
            		String ip = inetAddress.getHostAddress();
            		
	            	if(new HasCouchDB(ip, 1000).hasCouchDB())
	            	{
	            		address += ip+";;";
	            		System.out.println(ip+" has CouchDB");
	            	}
	            	else
	            	{
	            		System.out.println(ip+" does not have CouchDB");
	            	}
            	}
            	catch(Exception e)
            	{
            		//ignore if cant connect to couchdb on this address
            	}
            }
        }
        broadcast_string = address;
        System.out.println("Broadcaster: recalculated broadcast string to "+broadcast_string);
	}
	
	public void run()
	{
		int i = 0;
		while(should_broadcast)
		{
			try
			{
				if(i % 25 == 0)
				{
					calculateBroadcastString();
				}
				broadcast();
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.err.println("Error broadcasting:");
				e.printStackTrace();
			}
			i = i + 1;
		}
		socket.close();
	}
	
	private void broadcast() throws IOException
	{
		byte[] buffer = broadcast_string.getBytes();
        InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
        socket.send(packet);
//        System.out.println("Broadcaster: broadcasted "+broadcast_string);
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Thread th = new Thread(new CouchDBBroadcaster());
		th.start();
		th.join();
	}
}