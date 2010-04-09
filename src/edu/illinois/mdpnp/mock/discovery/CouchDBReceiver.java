package edu.illinois.mdpnp.mock.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class CouchDBReceiver 
{
	private static final String GROUP_ADDRESS = "230.0.0.1";
	private static final int PORT = 54321;

	public CouchDBReceiver()
	{

	}

	public String receive()
	{
		try
		{
			MulticastSocket socket = new MulticastSocket(PORT);
			InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
			socket.joinGroup(group);

			DatagramPacket packet;

			byte[] buf = new byte[256];
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);

			String received = new String(packet.getData());
			System.out.println("Receiver: received " + received);

			socket.leaveGroup(group);
			socket.close();
			return received;
		}
		catch(IOException e)
		{
			System.err.println("Error receiving");
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		new CouchDBReceiver().receive();
	}
}
