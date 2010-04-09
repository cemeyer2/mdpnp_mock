package edu.illinois.mdpnp.mock.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class CouchDBBroadcaster implements Runnable
{
	private static final String GROUP_ADDRESS = "230.0.0.1";
	private static final int PORT = 54321;
	
	boolean should_broadcast;
	DatagramSocket socket;
	
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
	
	public void run()
	{
		while(should_broadcast)
		{
			try
			{
				broadcast();
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				System.err.println("Error broadcasting:");
				e.printStackTrace();
			}
		}
		socket.close();
	}
	
	private void broadcast() throws IOException
	{
		String address = "";
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        {
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) 
            {
            	address += inetAddress.toString()+";;";
            }
        }
		
		byte[] buffer = address.getBytes();

        InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
        socket.send(packet);
//        System.out.println("Broadcaster: broadcasted "+address);
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Thread th = new Thread(new CouchDBBroadcaster());
		th.start();
		th.join();
	}
}