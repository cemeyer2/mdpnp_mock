package edu.illinois.mdpnp.mock.discovery;

import org.jcouchdb.db.Database;

public class HasCouchDB implements Runnable
{	
	private String ip;
	private int timeout;
	private boolean hasCouchDB;
	
	public HasCouchDB(String ip, int timeout)
	{
		this.ip = ip;
		this.timeout = timeout;
		this.hasCouchDB = false;
	}
	
	public boolean hasCouchDB()
	{
		try
		{
			Thread th = new Thread(this);
			th.start();
			Thread.sleep(timeout);
			if(th.isAlive())
			{
				th.interrupt();
			}
		}
		catch(Exception e)
		{
			
		}
		return hasCouchDB;
	}
	
	public void run()
	{
		try
    	{
        	Database db = new Database(ip, "mdpnp");
			db.getStatus();
			hasCouchDB = true;
    	}
    	catch(Exception e)
    	{
    		hasCouchDB = false;
    		//ignore if cant connect to couchdb on this address
    	}
	}
}
