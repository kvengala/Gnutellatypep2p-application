package com.peer_server;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class peer_peer_fncs extends UnicastRemoteObject implements rmi_interface{

	/**
	 * 
	 */
	//public static FileOutputStream output = new FileOutputStream("config.file"); 
	public static String user_dir = System.getProperty("user.dir");
	public static Properties prop1 = new Properties();
	private static HashMap<String,String> rthshmp= new HashMap<String,String>();
	private static HashMap<String,String> lthshmp= new HashMap<String,String>();
	private static HashMap<String,String> rthshmp_two= new HashMap<String,String>();
	private static HashMap<String,String> lthshmp_two= new HashMap<String,String>();
	
	private static final long serialVersionUID = 1L;
	public String run_dir() throws RemoteException 
	{
		// TODO Auto-generated method stub
		return user_dir;
	}

	 public static boolean server_good(String val, int num) throws Exception
	{
		try 
		{
			(new Socket(val, num)).close();
			return true;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return false;
		}
	}

	 protected peer_peer_fncs() throws RemoteException 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void left_traversal(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(property.getProperty("lftpnm").equals("undef"))
		{
		//System.out.println("in if -1");
		con = server_good(property.getProperty("IPAddress"), portnm-1);
		if(con == true)
		{	//System.out.println("in if -2");
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 1; 
			String fin = val[0]+"-"+Integer.toString(val1);
			FileOutputStream fileOut = new FileOutputStream(file);
			property.setProperty("lftpnm",fin);
			property.setProperty("lftprtnm",Integer.toString(portnm-1));
			property.store(fileOut, null);
			fileOut.close();
			//hshmp.put(fin, fin+"="+Integer.toString(portnm-1));
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm-1);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.left_traversal(fin, portnm-1);
		}
		}
		else
		{
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 1; 
		//	System.out.println("in else");
			String fin = val[0]+"-"+Integer.toString(val1);
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm-1);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.left_traversal(fin, portnm-1);
		}
	}
		catch(Exception e )
		{
			e.printStackTrace();
			
		}
		
	}
	public HashMap<String,String> left_peernm(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(!property.getProperty("lftpnm").equals("undef"))
		{
			lthshmp.put(property.getProperty("lftpnm"),property.getProperty("lftpnm")+"="+property.getProperty("lftprtnm"));
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 1; 
			//hshmp.put(fin, fin+"="+Integer.toString(portnm-1));
		//	Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),Integer.parseInt(property.getProperty("lftprtnm")));
		//	rmi_interface lookup = (rmi_interface)registry.lookup(property.getProperty("lftpnm"));
//			lthshmp= lookup.left_peernm(property.getProperty("lftpnm"),Integer.parseInt(property.getProperty("lftprtnm")));
		}
	
		}
		catch(Exception e )
		{
			e.printStackTrace();
			
		}
		return lthshmp;
	}
	public HashMap<String,String> left_peernm_two(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(!property.getProperty("lftpnmtwo").equals("undef"))
		{
			lthshmp_two.put(property.getProperty("lftpnmtwo"),property.getProperty("lftpnmtwo")+"="+property.getProperty("lftprtnmtwo"));
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 1; 
			//hshmp.put(fin, fin+"="+Integer.toString(portnm-1));
		//	Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),Integer.parseInt(property.getProperty("lftprtnmtwo")));
	//		rmi_interface lookup = (rmi_interface)registry.lookup(property.getProperty("lftpnm"));
	//		lthshmp_two= lookup.left_peernm(property.getProperty("lftpnmtwo"),Integer.parseInt(property.getProperty("lftprtnmtwo")));
		}
	
		}
		catch(Exception e )
		{
			e.printStackTrace();
			
		}
		return lthshmp_two;
	}
   
	public HashMap<String,String> right_peernm(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(!property.getProperty("rftpnm").equals("undef"))
		{
			rthshmp.put(property.getProperty("rftpnm"),property.getProperty("rftpnm")+"="+property.getProperty("rftprtnm"));
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 1; 
			//hshmp.put(fin, fin+"="+Integer.toString(portnm-1));
			//Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),Integer.parseInt(property.getProperty("lftprtnm")));
			//rmi_interface lookup = (rmi_interface)registry.lookup(property.getProperty("rftpnm"));
		//  rthshmp= lookup.left_peernm(property.getProperty("rftpnm"),Integer.parseInt(property.getProperty("rftprtnm")));
		}
	
		}
		catch(Exception e )
		{
			e.printStackTrace();
			
		}
		return rthshmp;
	}
	public HashMap<String,String> right_peernm_two(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(!property.getProperty("rftpnmtwo").equals("undef"))
		{
			rthshmp_two.put(property.getProperty("rftpnmtwo"),property.getProperty("rftpnmtwo")+"="+property.getProperty("rftprtnmtwo"));
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 1; 
			//hshmp.put(fin, fin+"="+Integer.toString(portnm-1));
			//Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),Integer.parseInt(property.getProperty("rftpnmtwo")));
			//rmi_interface lookup = (rmi_interface)registry.lookup(property.getProperty("rftpnmtwo"));
	//		rthshmp_two= lookup.left_peernm(property.getProperty("rftpnmtwo"),Integer.parseInt(property.getProperty("rftprtnmtwo")));
		}
		}
		catch(Exception e )
		{
			e.printStackTrace();
			
		}
		return lthshmp;
	}



	
	public void left_traversal_two(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(property.getProperty("lftpnmtwo").equals("undef"))
		{
		//System.out.println("in if -1");
		con = server_good(property.getProperty("IPAddress"), portnm-2);
		if(con == true)
		{	//System.out.println("in if -2");
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 2; 
			String fin = val[0]+"-"+Integer.toString(val1);
			FileOutputStream fileOut = new FileOutputStream(file);
			property.setProperty("lftpnmtwo",fin);
			property.setProperty("lftprtnmtwo",Integer.toString(portnm-2));
			property.store(fileOut, null);
			fileOut.close();
			//hshmp.put(fin, fin+"="+Integer.toString(portnm-1));
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm-2);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.left_traversal_two(fin, portnm-2);
			
			
		}
		}
		else
		{
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) - 2; 
		//	System.out.println("in else");
			String fin = val[0]+"-"+Integer.toString(val1);
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm-2);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.left_traversal_two(fin, portnm-2);
		}
	
		}
		catch(Exception e )
		{
			e.printStackTrace();
			
		}
		
	}
	public void  right_traversal(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(property.getProperty("rftpnm").equals("undef"))
		{
		con = server_good(property.getProperty("IPAddress"), portnm+1);
		if(con == true)
		{	
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) + 1; 
			String fin = val[0]+"-"+Integer.toString(val1);
			FileOutputStream fileOut = new FileOutputStream(user_dir+"\\config.file");
			property.setProperty("rftpnm",fin);
			property.setProperty("rftprtnm",Integer.toString(portnm+1));
			property.store(fileOut, null);
			fileOut.close();
			//lthshmp.put(fin,fin+"="+Integer.toString(portnm+1));
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm+1);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.right_traversal(fin, portnm+1);
	
		}
		}else
		{
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) + 1; 
			String fin = val[0]+"-"+Integer.toString(val1);
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm+1);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.right_traversal(fin, portnm+1);
		
		}
		
		}
		catch(Exception e )
		{
			e.printStackTrace();
	
		}
		
	}

public void  right_traversal_two(String peernm , int portnm) throws RemoteException
	{
		try
		{
		boolean con;
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		if(property.getProperty("rftpnmtwo").equals("undef"))
		{
		con = server_good(property.getProperty("IPAddress"), portnm+2);
		if(con == true)
		{	
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) + 2; 
			String fin = val[0]+"-"+Integer.toString(val1);
			FileOutputStream fileOut = new FileOutputStream(user_dir+"\\config.file");
			property.setProperty("rftpnmtwo",fin);
			property.setProperty("rftprtnmtwo",Integer.toString(portnm+2));
			property.store(fileOut, null);
			fileOut.close();
			//lthshmp.put(fin,fin+"="+Integer.toString(portnm+1));
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm+2);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.right_traversal_two(fin, portnm+2);
	
		}
		}else
		{
			String [] val = property.getProperty("PeerName").split("-");
			int val1 =  Integer.parseInt(val[1]) + 2; 
			String fin = val[0]+"-"+Integer.toString(val1);
			Registry registry = LocateRegistry.getRegistry(property.getProperty("IPAddress"),portnm+2);
			rmi_interface lookup = (rmi_interface)registry.lookup(fin);
			lookup.right_traversal_two(fin, portnm+2);
		
		}
		
		}
		catch(Exception e )
		{
			e.printStackTrace();
	
		}
		
	}



 public String ret_conts() throws RemoteException
	{   String dirs;
		try{
		File file = new File(user_dir+"\\config.file");
		FileInputStream finpt = new FileInputStream(file);
		Properties property= new Properties();
		property.load(finpt);
		finpt.close();
		dirs = property.getProperty("DownloadDirectory")+"="+ property.getProperty("FileListDIr");
		return dirs;
	}catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	
	}
	public void comment() throws RemoteException
	{
		System.out.println("the file has been modified in the source kindly update");
	}
	public void comment1() throws RemoteException
	{
		System.out.println("VALID: STATUS - The files are in sync as per the main servver");
	}

	public boolean search_file(String filename,String pathname ) throws RemoteException
	{
		boolean chk  = new File(pathname+"//"+filename).exists();
		return chk;
	}
	
	
}
