package us.anarchia.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* This class is untested.  It was created when moving code to GWT, so that these java.net.* classes would
    not be included in client-side code.
 */
public class StorageID {
    static {
		try {
			InetAddress addr = InetAddress.getLocalHost(); // Get IP Address
			byte[] ipAddr = addr.getAddress(); // Get hostname
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			hostname = "ERROR_in_hostname";
			System.out.println("ERROR "+e);
		}

	}

    private transient long iNextID = 1;

    private String id;
	public String getID() {
		return id;
	}
	public void setID(String value) {
		id = value;
	}

	public String getStorageID(){
		//Todo: connect to the database, and use the local conf's prefix or the ip address.
		long now = System.currentTimeMillis();
		long i;
        String hostname = getHostName();
		synchronized(this){
			i = ++iNextID;
			id = hostname+':'+now+':'+iNextID;
		}
		return id;

	}
	private transient static String hostname;


	public String getHostName(){
		return hostname;
	}


}
